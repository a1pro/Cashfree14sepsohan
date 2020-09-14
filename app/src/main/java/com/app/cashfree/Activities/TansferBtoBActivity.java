package com.app.cashfree.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.app.cashfree.R;

public class TansferBtoBActivity extends AppCompatActivity implements View.OnClickListener {
    private Button bt_transfer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tansfer_bto_b);
        initUI();
    }

    private void initUI() {
        bt_transfer=findViewById(R.id.bt_transfer);
        bt_transfer.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_transfer:
                Intent intent=new Intent(TansferBtoBActivity.this,BankTransferCompleteActivity.class);
                startActivity(intent);
                break;
        }
    }
}