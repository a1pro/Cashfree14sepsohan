package com.app.cashfree.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.cashfree.R;
import com.app.cashfree.utils.PreferenceHandler;

public class MyProfileActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView iv_back;
    private TextView tv_my_wallet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        initUI();
    }

    private void initUI() {
        iv_back=findViewById(R.id.iv_back);
        tv_my_wallet=findViewById(R.id.tv_my_wallet);
        iv_back.setOnClickListener(this);
        tv_my_wallet.setText("My Wallet Number: "+PreferenceHandler.readString(MyProfileActivity.this, "walletId", ""));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
