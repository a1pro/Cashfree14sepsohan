package com.app.cashfree.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.cashfree.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

public class PhoneVerificationActivity extends AppCompatActivity implements View.OnClickListener {
    private String mobile;
    private ImageView iv_back, iv_done;
    private String TAG = "Cashfree Verification code";
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    private EditText otp;
    private FirebaseAuth mAuth;
    private PhoneAuthCredential credential1;
    private Button bt_send;
    private String otp1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_verification);
        Intent intent = getIntent();
        mobile = intent.getStringExtra("mobile");
        Log.e("mobile", "+91 " + mobile);
        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();

        //  mAuth.getCurrentUser();
        bt_send = findViewById(R.id.bt_send);
        iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        otp = findViewById(R.id.otp);
        bt_send.setOnClickListener(this);


        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                 otp1 = credential.getSmsCode();
                if (otp1 != null) {
                    otp.setText(otp1);
                }
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.
                // Log.d(TAG, "onVerificationCompleted:" + credential);
                Toast.makeText(PhoneVerificationActivity.this, "Verification complete", Toast.LENGTH_SHORT).show();
                credential1 = PhoneAuthProvider.getCredential(mVerificationId, otp1);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                //   Log.w(TAG, "onVerificationFailed", e);
                Toast.makeText(PhoneVerificationActivity.this, "Verification fail" + e.getMessage(), Toast.LENGTH_SHORT).show();

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    Toast.makeText(PhoneVerificationActivity.this, "Firebase ex", Toast.LENGTH_SHORT).show();
                    // ...
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    // ...
                }

                // Show a message and update the UI
                // ...
            }


            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d(TAG, "onCodeSent:" + verificationId);
                Toast.makeText(PhoneVerificationActivity.this, "Code sent", Toast.LENGTH_SHORT).show();
                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;

                // ...
            }
        };

        if (mobile != null) {
            sendVerificationCode();
        } else {
            Toast.makeText(this, "null", Toast.LENGTH_SHORT).show();
        }

    }


    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential1) {
        mAuth.signInWithCredential(credential1)
                .addOnCompleteListener(PhoneVerificationActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            //verification successful we will start the profile activity
                            if (mobile != null) {
                                if (otp.getText().toString().equalsIgnoreCase(otp1)){
                                    Intent intent = new Intent();
                                    intent.putExtra("otp", otp1);
                                    setResult(1, intent);
                                    finish();
                                }else {
                                    Toast.makeText(PhoneVerificationActivity.this, "You Entered Wrong Otp", Toast.LENGTH_SHORT).show();
                                }

                            }


                        } else {

                            //verification unsuccessful.. display an error message

                            String message = "Somthing is wrong, we will fix it soon...";

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                message = "Invalid code entered...";
                                Log.e("exception", task.getException().getMessage());
                                Toast.makeText(PhoneVerificationActivity.this, ""+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

    }

    private void sendVerificationCode() {
        PhoneAuthProvider phoneAuthProvider = PhoneAuthProvider.getInstance(mAuth);
        phoneAuthProvider.verifyPhoneNumber(
                "+91" + mobile,      // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                PhoneVerificationActivity.this,             // Activity (for callback binding)
                mCallbacks);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_send:
                if (otp.getText().toString().isEmpty()) {
                    Toast.makeText(this, "Please enter otp", Toast.LENGTH_SHORT).show();
                } else {
                    signInWithPhoneAuthCredential(credential1);
                }
                break;

            case R.id.iv_back:
                finish();
                break;
        }
    }
}