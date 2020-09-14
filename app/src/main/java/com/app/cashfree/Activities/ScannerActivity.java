package com.app.cashfree.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Camera;
import android.media.Image;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.cashfree.Interface.ApiInterface;
import com.app.cashfree.Model.GenricModel;
import com.app.cashfree.Model.SignatureModel;
import com.app.cashfree.R;
import com.app.cashfree.Retrofit.RetrofitClient;
import com.app.cashfree.utils.PreferenceHandler;
import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ScannerActivity extends AppCompatActivity implements View.OnClickListener {
    private CodeScanner mCodeScanner;
    private CodeScannerView scannerView;
    private Button btn_transfer;
    private EditText et_amount;
    private TextView txt_wallet_id;
    private String receiver_wallet_id;
    private ImageView iv_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
        initUI();
        getCameraInstance();
        setScanner();
//        Log.e("userid",getuserid()+" "+getWalletid());

    }

    private void initUI() {
        iv_back=findViewById(R.id.iv_back1);
        txt_wallet_id = findViewById(R.id.txt_wallet_id);
        scannerView = findViewById(R.id.scanner_view);
        btn_transfer = findViewById(R.id.btn_transfer);
        btn_transfer.setOnClickListener(this);
        et_amount = findViewById(R.id.et_amount);
        iv_back.setOnClickListener(this);
    }

    private void setScanner() {
        mCodeScanner = new CodeScanner(this, scannerView);
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("result_scan", String.valueOf(result));
                        if (!result.getText().equalsIgnoreCase("")) {
                            receiver_wallet_id=String.valueOf(result);
                            et_amount.setVisibility(View.VISIBLE);
                            btn_transfer.setVisibility(View.VISIBLE);
                            txt_wallet_id.setVisibility(View.VISIBLE);
                            txt_wallet_id.setText("Wallet ID: " + result);
                            // hitScannerAPI();
                        } else {
                            // llInvalid.setVisibility(View.VISIBLE);
                            //  llValid.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });
        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCodeScanner.startPreview();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
    }

    @Override
    protected void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }

    public static Camera getCameraInstance() {
        Camera c = null;
        try {
            c = Camera.open(); // attempt to get a Camera instance
        } catch (Exception e) {
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_transfer:
                Intent intent=new Intent(ScannerActivity.this,CheckPinActivity.class);
                intent.putExtra("type","five");
                startActivityForResult(intent,5);
            //    hitTransferApi();
                break;
            case R.id.iv_back1:
                finish();
                break;

        }
    }

    private void hitTransferApi() {
        Retrofit retrofit = RetrofitClient.retroInit();
        final ApiInterface apiListener = retrofit.create(ApiInterface.class);
        Call<GenricModel> call = apiListener.tranfer_money(getuserid(), getWalletid(),receiver_wallet_id,et_amount.getText().toString(),"1","");
        call.enqueue(new Callback<GenricModel>() {
            @Override
            public void onResponse(Call<GenricModel> call, Response<GenricModel> response) {
                GenricModel data = response.body();
                assert data != null;
                if (data.getCode().equalsIgnoreCase("201")) {
                    Toast.makeText(ScannerActivity.this, ""+data.getMessage(), Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(ScannerActivity.this, ""+data.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GenricModel> call, Throwable t) {
                Toast.makeText(ScannerActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private String getuserid () {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ScannerActivity.this);
        return preferences.getString("userid", null);
    }


    private String getWalletid () {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ScannerActivity.this);
        return preferences.getString("walletid", null);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==5){
            if (data!=null){
                if (data.getStringExtra("result")!=null){
                     hitTransferApi();
                }
            }
        }
    }
}
