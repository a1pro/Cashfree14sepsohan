package com.app.cashfree.Activities;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.app.cashfree.utils.CommonUtils;
import com.app.cashfree.Interface.ApiInterface;
import com.app.cashfree.Model.AddCartModel;
import com.app.cashfree.Model.CardInfo;
import com.app.cashfree.R;
import com.app.cashfree.Retrofit.RetrofitClient;
import com.app.cashfree.utils.Constants;
import com.app.cashfree.utils.PreferenceHandler;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AddCardActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText tv_card_number, tv_validity, tv_member_name, tv_cvv, tv_bank_name;
    private Button bt_continue;
    private ImageView iv_back;
    private SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    List<CardInfo> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);
        sharedPreferences = getSharedPreferences(Constants.pref, MODE_PRIVATE);
        initUI();
    }

    private void initUI() {
        //tv_bank_name = findViewById(R.id.tv_bank_name);
        iv_back = findViewById(R.id.iv_back);
        //tv_card_number = findViewById(R.id.tv_card_number);
        bt_continue = findViewById(R.id.bt_continue);
   //     tv_validity = findViewById(R.id.tv_validity);
       // tv_member_name = findViewById(R.id.tv_member_name);
    //    tv_cvv = findViewById(R.id.tv_cvv);
        bt_continue.setOnClickListener(this);
        iv_back.setOnClickListener(this);
    }

    private void AddCard() {
        //     PreferenceHandler.writeString(getApplicationContext(),"card",tv_card_number.getText().toString());
        final Dialog dialog = CommonUtils.showProgress(AddCardActivity.this);
        Retrofit retrofit = RetrofitClient.retroInit();
        final ApiInterface apiListener = retrofit.create(ApiInterface.class);
        Call<AddCartModel> call = apiListener.saveCard(PreferenceHandler.readString(AddCardActivity.this,
                PreferenceHandler.USER_ID, ""), tv_member_name.getText().toString(), tv_card_number.getText().toString(),
                tv_cvv.getText().toString(), tv_validity.getText().toString(), "", "");
        call.enqueue(new Callback<AddCartModel>() {
            @Override
            public void onResponse(Call<AddCartModel> call, Response<AddCartModel> response) {
                if (response.isSuccessful()) {
                    list.clear();
                    AddCartModel data = response.body();
                    if (data != null && data.getCode().equalsIgnoreCase("201")) {
                        list.addAll(data.getCardInfo());
                        dialog.dismiss();
                        Savecard(list.get(0).getCardNo());
                        SaveCardHolder(list.get(0).getCardHolderName());
                        PreferenceHandler.writeString(AddCardActivity.this,"accountholdername",tv_member_name.getText().toString());
                        cleardata();
                        Toast.makeText(AddCardActivity.this, ""+data.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        dialog.dismiss();
                        Toast.makeText(AddCardActivity.this, "" + data.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<AddCartModel> call, Throwable t) {
                dialog.dismiss();
            }
        });

    }

    private void cleardata() {
        tv_validity.getText().clear();
        tv_cvv.getText().clear();
        tv_bank_name.getText().clear();
        tv_member_name.getText().clear();
        tv_card_number.getText().clear();
    }

//    private void Savecard(List<CardInfo> list) {
//        editor = sharedPreferences.edit();
//        editor.putString("card", list.get(0).getCardNo());
//        editor.apply();
//
//    }

    private void Savecard(String card) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("card", card);
        editor.apply();
    }

    private void SaveCardHolder(String card) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(AddCardActivity.this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("cardholder", card);
        editor.apply();

    }




    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_continue:
                Intent i=new Intent(AddCardActivity.this,CardLinkedActivity.class);
                startActivity(i);
               /* if (tv_card_number.getText().toString().length() < 16) {
                    Toast.makeText(this, "Invalid card details", Toast.LENGTH_SHORT).show();
                } else {
                    if (Validation()) {
                        AddCard();
                    }
                }*/

                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }

    private boolean Validation() {
        if (tv_validity.getText().toString().length() < 3) {
            Toast.makeText(this, "Please Enter Card Validity", Toast.LENGTH_SHORT).show();
            return false;
        } else if (tv_cvv.getText().toString().length() < 3) {
            Toast.makeText(this, "Please Enter Valid Cvv Number", Toast.LENGTH_SHORT).show();
            return false;
        } else if (tv_member_name.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please Enter Card Holder Name", Toast.LENGTH_SHORT).show();
            return false;
        } else if (tv_bank_name.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please Enter Bank Name", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
