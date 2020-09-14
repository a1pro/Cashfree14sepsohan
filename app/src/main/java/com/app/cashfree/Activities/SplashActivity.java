package com.app.cashfree.Activities;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.format.Formatter;
import android.util.Log;
import android.widget.Toast;

import com.app.cashfree.Interface.ApiInterface;
import com.app.cashfree.Model.AutorizationModel;
import com.app.cashfree.Model.SignatureModel;
import com.app.cashfree.R;
import com.app.cashfree.Retrofit.RetrofitClient;
import com.app.cashfree.utils.PreferenceHandler;
import com.app.cashfree.utils.Utils;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.crypto.Cipher;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SplashActivity extends AppCompatActivity {
    private Retrofit retrofit;
    String userId;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        userId= PreferenceHandler.readString(SplashActivity.this,PreferenceHandler.USER_ID,"");
        Utils.getMACAddress("wlan0");
        Utils.getMACAddress("eth0");
        Utils.getIPAddress(true); // IPv4
        Utils.getIPAddress(false); // IPv6

        WifiManager wm = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        String ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());

        Log.e("ip_address",ip);
        Log.e("utils_ip",Utils.getIPAddress(true));
        Log.e("utils_ipv6",Utils.getIPAddress(false));

        //String clientIdWithEpochTimeStamp = "CF25608CMCUZXRXDFTEMAA"+"."+Instant.now().getEpochSecond();
        //generateEncryptedSignature(clientIdWithEpochTimeStamp);
        checkPermission();
    }

   /* @RequiresApi(api = Build.VERSION_CODES.O)
    private static String generateEncryptedSignature(String clientIdWithEpochTimestamp) {
        String encrytedSignature = "";
        try {
            String filename="accountId_24890_public_key.pem";
            Path pathToFile = Paths.get(filename);
            System.out.println(pathToFile.toAbsolutePath());
            byte[] keyBytes = Files
                    .readAllBytes(new File("/Users/EWEB-12/Downloads/accountId_24890_public_key.pem").toPath()); // Absolute Path to be replaced
            String publicKeyContent = new String(keyBytes);
            System.out.println(publicKeyContent);
            publicKeyContent = publicKeyContent.replaceAll("[\\t\\n\\r]", "")
                    .replace("-----BEGIN PUBLIC KEY-----", "").replace("-----END PUBLIC KEY-----", "");
            KeyFactory kf = KeyFactory.getInstance("RSA");
            System.out.println(publicKeyContent);
            X509EncodedKeySpec keySpecX509 = new X509EncodedKeySpec(
                    Base64.getDecoder().decode(publicKeyContent));
            RSAPublicKey pubKey = (RSAPublicKey) kf.generatePublic(keySpecX509);
            final Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-1AndMGF1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);
            encrytedSignature = Base64.getEncoder().encodeToString(cipher.doFinal(clientIdWithEpochTimestamp.getBytes()));
            System.out.println(encrytedSignature);
            Log.e("signature",encrytedSignature);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encrytedSignature;
    }
*/

    private void hitGetSignatureApi() {
        try {
            retrofit = RetrofitClient.retroInit();
            final ApiInterface apiListener = retrofit.create(ApiInterface.class);
            Call<SignatureModel> call = apiListener.signature();
            call.enqueue(new Callback<SignatureModel>() {
                @Override
                public void onResponse(Call<SignatureModel> call, Response<SignatureModel> response) {
                    SignatureModel data = response.body();
                    if (data != null) {
                        if (data.getCode().equalsIgnoreCase("201")) {
                            String sig_val = data.getData();
                            Autorize(sig_val);
                        } else {
                            Toast.makeText(SplashActivity.this, "" + data.getStatus(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<SignatureModel> call, Throwable t) {
                    Toast.makeText(SplashActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void checkPermission() {
                    Dexter.withActivity(SplashActivity.this)
                            .withPermissions(
                                    Manifest.permission.READ_EXTERNAL_STORAGE,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    Manifest.permission.CAMERA)
                            .withListener(new MultiplePermissionsListener() {
                                @Override
                                public void onPermissionsChecked(MultiplePermissionsReport report) {
                                    // check if all permissions are granted
                                    if (report.areAllPermissionsGranted()) {
                                        hitGetSignatureApi();
                                    }
                                    if (report.isAnyPermissionPermanentlyDenied()) {

                                    }
                                }

                                @Override
                                public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                                    token.continuePermissionRequest();
                                }
                            })
                            .onSameThread()
                            .check();
            }



    private void Autorize(String sig_val) {
        retrofit = RetrofitClient.retroInitHeaderAuthorisation(sig_val);
        final ApiInterface apiListener = retrofit.create(ApiInterface.class);
        Call<AutorizationModel> call = apiListener.AutorizationApi();
        call.enqueue(new Callback<AutorizationModel>() {
            @Override
            public void onResponse(Call<AutorizationModel> call, Response<AutorizationModel> response) {
                if (response.isSuccessful()) {
                    AutorizationModel data = response.body();
                    if (data != null) {
                        if (data.getSubCode().equalsIgnoreCase("200")) {
                            Log.e("token", data.getData().getToken());
                           // token=data.getData().getToken();
                            SaveToken(data.getData().getToken());
                            RetrofitClient retrofitClient=new RetrofitClient(SplashActivity.this, data.getData().getToken());
                            retrofitClient.storeToken(data.getData().getToken());
                            splash();
               //       Toast.makeText(SplashActivity.this, "" + data.getMessage(), Toast.LENGTH_SHORT).show();
                        } else {
                            splash();
                          Toast.makeText(SplashActivity.this, "" + data.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<AutorizationModel> call, Throwable t) {
            //    Toast.makeText(SplashActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void splash() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (userId.equalsIgnoreCase("")) {
                    Intent intent = new Intent(SplashActivity.this, WelcomeSliderActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 2000);
    }

    private String GetPin() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(SplashActivity.this);
        return preferences.getString("pinnumber", null);
    }


    private void SaveToken(String token) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Token", token);
        editor.apply();
    }

}