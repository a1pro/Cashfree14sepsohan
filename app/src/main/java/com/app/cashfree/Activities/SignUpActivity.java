package com.app.cashfree.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.cashfree.Model.GenricModel;
import com.app.cashfree.utils.CommonUtils;
import com.app.cashfree.Interface.ApiInterface;
import com.app.cashfree.Model.SignModel;
import com.app.cashfree.R;
import com.app.cashfree.Retrofit.RetrofitClient;
import com.app.cashfree.utils.Constants;
import com.app.cashfree.utils.PreferenceHandler;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SignUpActivity extends AppCompatActivity {
    private Button bt_sigin;
    private EditText et_name,et_email,et_phone;
    private Retrofit retrofit;
    TextView tv_signup;
    private SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharedPreferences = getSharedPreferences(Constants.pref, MODE_PRIVATE);
        initUI();
    }

    private void initUI() {
        tv_signup=findViewById(R.id.tv_signup);
        tv_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(SignUpActivity.this,LoginActivity.class);
                startActivity(i);
                finish();
            }
        });
        et_phone=findViewById(R.id.et_phone);
        et_name=findViewById(R.id.et_name);
        et_email=findViewById(R.id.et_email);
        bt_sigin=findViewById(R.id.bt_sigin);
        bt_sigin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendtOtp();
           //     SignUp();
            }
        });
    }


    private void sendtOtp() {
        if (et_phone.getText().toString().isEmpty() || et_phone.getText().toString().length() < 10) {
            et_phone.setError("Enter a valid mobile");
            et_phone.requestFocus();
            return;
        }
        Intent intent = new Intent(SignUpActivity.this, PhoneVerificationActivity.class);
        intent.putExtra("mobile", et_phone.getText().toString().trim());
        startActivityForResult(intent,1);
    }
    private void SignUp() {
        final Dialog dialog = CommonUtils.showProgress(SignUpActivity.this);
        retrofit = RetrofitClient.retroInit();
        final ApiInterface apiListener = retrofit.create(ApiInterface.class);
        Call<SignModel> call = apiListener.signUp(et_name.getText().toString(),"","",
                et_phone.getText().toString(),et_email.getText().toString());
        call.enqueue(new Callback<SignModel>() {
            @Override
            public void onResponse(Call<SignModel> call, Response<SignModel> response) {
                if (response.isSuccessful()) {
                    dialog.dismiss();
                    SignModel data = response.body();
                    if (data != null) {
                        if (data.getCode().equalsIgnoreCase("201")) {
                            dialog.dismiss();
                            String user_id=data.getData().get(0).getId();
                            PreferenceHandler.writeString(SignUpActivity.this,"first_name",data.getData().get(0).getFirstName());
                            PreferenceHandler.writeString(SignUpActivity.this,"walletId",data.getData().get(0).getWalletId());
                            PreferenceHandler.writeString(SignUpActivity.this,"myscanner",data.getData().get(0).getQrCode());
                            PreferenceHandler.writeString(SignUpActivity.this,"accountholdername",data.getData().get(0).getFirstName()+" "+data.getData().get(0).getLastName());
                            PreferenceHandler.writeString(SignUpActivity.this,"email",data.getData().get(0).getEmail());
                            PreferenceHandler.writeString(SignUpActivity.this,PreferenceHandler.USER_ID,user_id);
                            Toast.makeText(SignUpActivity.this, "" + data.getMessage(), Toast.LENGTH_SHORT).show();

                            UserVerifyApi(data.getData().get(0).getId());

                        } else {
                            dialog.dismiss();
                            Toast.makeText(SignUpActivity.this, "" + data.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<SignModel> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(SignUpActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void UserVerifyApi(String userid) {
        retrofit = RetrofitClient.retroInit();
        final ApiInterface apiListener = retrofit.create(ApiInterface.class);
        Call<GenricModel> call = apiListener.userVarify(userid);
        call.enqueue(new Callback<GenricModel>() {
            @Override
            public void onResponse(Call<GenricModel> call, Response<GenricModel> response) {
                if (response.isSuccessful()) {

                    GenricModel data = response.body();
                    if (data != null) {
                        if (data.getCode().equalsIgnoreCase("201")) {

                            Toast.makeText(SignUpActivity.this, ""+data.getMessage(), Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(SignUpActivity.this, LoginActivity.class);
                            startActivity(i);
                            finish();
                        } else {

                            Toast.makeText(SignUpActivity.this, "" + data.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<GenricModel> call, Throwable t) {
                Toast.makeText(SignUpActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });



    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1){
            if (data!=null){
                String otp=data.getStringExtra("otp");
                if (otp!=null){
                    SignUp();
                }

            }
        }
    }
}
