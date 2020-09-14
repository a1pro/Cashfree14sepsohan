package com.app.cashfree.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.app.cashfree.Interface.ApiInterface;
import com.app.cashfree.Model.GenricModel;
import com.app.cashfree.R;
import com.app.cashfree.Retrofit.RetrofitClient;
import com.app.cashfree.utils.PreferenceHandler;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class ChangePasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_old_pass, et_pass, et_con_pass;
    private ProgressBar progressbar;
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password_activitt);
        initUI();
    }

    private void initUI() {
        et_old_pass = findViewById(R.id.et_old_pass);
        et_pass = findViewById(R.id.et_pass);
        et_con_pass = findViewById(R.id.et_con_pass);
        Button bt_submit = findViewById(R.id.bt_submit);
        ImageView iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        bt_submit.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_submit:
                hitChangePassword();
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }

    private void hitChangePassword() {
        progressbar.setVisibility(View.VISIBLE);
        retrofit = RetrofitClient.retroInit();
        final ApiInterface apiListener = retrofit.create(ApiInterface.class);
        Call<GenricModel> call = apiListener.forgot(PreferenceHandler.readString(ChangePasswordActivity.this, "email", ""));
        call.enqueue(new Callback<GenricModel>() {
            @Override
            public void onResponse(Call<GenricModel> call, Response<GenricModel> response) {
                GenricModel data = response.body();
                assert data != null;
                if (data.getCode().equalsIgnoreCase("201")) {
                    Toast.makeText(ChangePasswordActivity.this, "" + data.getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ChangePasswordActivity.this, "" + data.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GenricModel> call, Throwable t) {
                Toast.makeText(ChangePasswordActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}