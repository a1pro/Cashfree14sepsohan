package com.app.cashfree.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.app.cashfree.utils.CommonUtils;
import com.app.cashfree.Interface.ApiInterface;
import com.app.cashfree.Model.GenricModel;
import com.app.cashfree.R;
import com.app.cashfree.Retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RecoverActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText et_pass;
    private EditText et_con_pass,et_otp,et_email;
    private String otp;
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recover);
        initUI();
    }

    private void initUI() {
        et_otp=findViewById(R.id.et_otp);
        otp = getIntent().getStringExtra("OTP");
        et_email  = findViewById(R.id.et_email);
        et_pass = findViewById(R.id.et_pass);
        ImageView iv_back = findViewById(R.id.iv_back);
        et_con_pass = findViewById(R.id.et_con_pass1);
        //et_otp.setSelection(et_otp.getText().length());
        Button bt_submit = findViewById(R.id.bt_submit);
        bt_submit.setOnClickListener(this);
        et_otp.setText(otp);
    }

    private void hitApi() {
        final Dialog dialog = CommonUtils.showProgress(RecoverActivity.this);
        retrofit = RetrofitClient.retroInit();
        final ApiInterface apiListener = retrofit.create(ApiInterface.class);
        Call<GenricModel> call = apiListener.recover_password(et_email.getText().toString(),otp,et_pass.getText().toString().trim(),
                et_con_pass.getText().toString().trim());
        call.enqueue(new Callback<GenricModel>() {
            @Override
            public void onResponse(Call<GenricModel> call, Response<GenricModel> response) {
                GenricModel data = response.body();
                assert data != null;
                if (data.getCode().equalsIgnoreCase("201")) {
                    Toast.makeText(RecoverActivity.this, ""+data.getMessage(), Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    CommonUtils.showLongToast(RecoverActivity.this, data.getMessage());
                    finish();
                } else {
                    Toast.makeText(RecoverActivity.this, ""+data.getMessage(), Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                   CommonUtils.showLongToast(RecoverActivity.this, data.getMessage());
                }
            }

            @Override
            public void onFailure(Call<GenricModel> call, Throwable t) {
                Toast.makeText(RecoverActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                CommonUtils.showSmallToast(RecoverActivity.this, t.getMessage());
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_submit:
                hitApi();
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }
}