package com.app.cashfree.Activities;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.app.cashfree.utils.CommonUtils;
import com.app.cashfree.Interface.ApiInterface;
import com.app.cashfree.Model.GenricModel;
import com.app.cashfree.R;
import com.app.cashfree.Retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_email;
    private Intent intent;
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        initUI();
    }

    private void initUI() {
        Button bt_submit = findViewById(R.id.bt_submit);
        et_email = findViewById(R.id.et_email);
        ImageView iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        bt_submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_submit:
                hitApi();
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }

    private void hitApi() {
        final Dialog dialog = CommonUtils.showProgress(ForgotPasswordActivity.this);
        retrofit = RetrofitClient.retroInit();
        final ApiInterface apiListener = retrofit.create(ApiInterface.class);
        Call<GenricModel> call = apiListener.forgot(et_email.getText().toString());
        call.enqueue(new Callback<GenricModel>() {
            @Override
            public void onResponse(Call<GenricModel> call, Response<GenricModel> response) {
                GenricModel data = response.body();
                assert data != null;
                if (data.getCode().equalsIgnoreCase("201")) {
                    dialog.dismiss();
                    intent = new Intent(ForgotPasswordActivity.this, RecoverActivity.class);
                    intent.putExtra("OTP", data.getOtp());
                    startActivity(intent);
                    finish();
                } else {
                    dialog.dismiss();
                    CommonUtils.showLongToast(ForgotPasswordActivity.this, data.getMessage());
                }
            }

            @Override
            public void onFailure(Call<GenricModel> call, Throwable t) {
                dialog.dismiss();
                CommonUtils.showSmallToast(ForgotPasswordActivity.this, t.getMessage());
            }
        });
    }
}