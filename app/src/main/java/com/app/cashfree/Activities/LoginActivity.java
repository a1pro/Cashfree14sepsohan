package com.app.cashfree.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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

public class LoginActivity extends AppCompatActivity {
    private EditText et_email, et_pass;
    private Button bt_sigin;
    private ProgressBar progressbar;
    private Retrofit retrofit;
    private TextView tv_signup, tvForgotPass;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        sharedPreferences = getSharedPreferences(Constants.pref, MODE_PRIVATE);
        init();
        bt_sigin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validation()) {
                    Login(et_email.getText().toString(), et_pass.getText().toString());
                }
            }
        });
    }

    private void init() {
        tvForgotPass = findViewById(R.id.tvForgotPass);
        bt_sigin = findViewById(R.id.bt_sigin);
        tv_signup = findViewById(R.id.tv_signup);

        tvForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(i);
            }
        });

        tv_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(i);
            }
        });
        et_email = findViewById(R.id.et_email);
        progressbar = findViewById(R.id.progressbar);
        et_pass = findViewById(R.id.et_pass);
    }


    private boolean validation() {
        if (et_email.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please Enter Email", Toast.LENGTH_SHORT).show();
            return false;
        } else if (et_pass.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    private void Login(String email, String password) {
        try {
            final Dialog dialog = CommonUtils.showProgress(LoginActivity.this);
            retrofit = RetrofitClient.retroInit();
            final ApiInterface apiListener = retrofit.create(ApiInterface.class);
            Call<SignModel> call = apiListener.Login(email, password, "123", "xyz");
            call.enqueue(new Callback<SignModel>() {
                @Override
                public void onResponse(Call<SignModel> call, Response<SignModel> response) {
                    if (response.isSuccessful()) {
                        SignModel data = response.body();
                        if (data != null) {
                            if (data.getCode().equalsIgnoreCase("201")) {
                                dialog.dismiss();
                                String user_id = data.getData().get(0).getId();
                                SaveWalletid(data.getData().get(0).getWalletId());
                                saveuserid(data.getData().get(0).getId());
                                SaveBenename(data.getData().get(0).getBeneficiaryName());
                                if (data.getCardInfo().size() > 0) {
                                    Savecard(data.getCardInfo().get(0).getCardNo());
                                    SaveCardHolder(data.getCardInfo().get(0).getCardHolderName());
                                } else {
                                    Savecard(null);
                                    SaveCardHolder(null);
                                }
                                SaveBene(data.getData().get(0).getBeneficiaryId());
                                PreferenceHandler.writeString(LoginActivity.this, "first_name", data.getData().get(0).getFirstName());
                                PreferenceHandler.writeString(LoginActivity.this, "walletId", data.getData().get(0).getWalletId());
                                PreferenceHandler.writeString(LoginActivity.this, "myscanner", data.getData().get(0).getQrCode());
                                //     PreferenceHandler.writeString(LoginActivity.this,"accountholdername",data.getCardInfo().get(0).getCardHolderName());
                                PreferenceHandler.writeString(LoginActivity.this, "email", data.getData().get(0).getEmail());
                                PreferenceHandler.writeString(LoginActivity.this, PreferenceHandler.USER_ID, user_id);
                                Toast.makeText(LoginActivity.this, "" + data.getMessage(), Toast.LENGTH_SHORT).show();

                                if (data.getData().get(0).getPinNumber() != null) {
                                    Savepin(data.getData().get(0).getPinNumber());
                                    Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                                    startActivity(i);
                                    finish();
                                } else {
                                    GotoGeneratePin();
                                }


                            } else {
                                dialog.dismiss();
                                Toast.makeText(LoginActivity.this, "" + data.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<SignModel> call, Throwable t) {
                    dialog.dismiss();
                    Toast.makeText(LoginActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void GotoGeneratePin() {
        Intent intent = new Intent(LoginActivity.this, GeneratePinActivity.class);
        startActivity(intent);
    }

    private void SaveWalletid(String walletid) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("walletid", walletid);
        editor.apply();
    }

    private void Savecard(String card) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("card", card);
        editor.apply();
    }

    private void SaveCardHolder(String card) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("cardholder", card);
        editor.apply();

    }

    private void saveuserid(String userid) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("userid", userid);
        editor.apply();

    }

    private void SaveBene(String bene) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("benficairyid", bene);
        editor.apply();
    }

    private void SaveBenename(String bene) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("benficairyname", bene);
        editor.apply();
    }

    private void Savepin(String pin) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("pinnumber", pin);
        editor.apply();
    }
}