package com.app.cashfree.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.app.cashfree.R;

public class BankTransferCompleteActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView iv_tick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_transfer_complete);
        initUI();
    }

    private void initUI() {
        iv_tick=findViewById(R.id.iv_tick);
        iv_tick.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_tick:
                Intent intent=new Intent(BankTransferCompleteActivity.this,HomeActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}