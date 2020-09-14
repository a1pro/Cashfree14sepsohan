package com.app.cashfree.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.app.cashfree.R;

public class TransferActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView iv_btob,iv_wtob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);
        initUI();
    }

    private void initUI() {
        iv_btob=findViewById(R.id.iv_btob);
        iv_wtob=findViewById(R.id.iv_wtob);
        iv_wtob.setOnClickListener(this);
        iv_btob.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_btob:
                Intent i=new Intent(TransferActivity.this,TansferBtoBActivity.class);
                startActivity(i);
                break;
            case R.id.iv_wtob:
                Intent i1=new Intent(TransferActivity.this,TansferBtoBActivity.class);
                startActivity(i1);
                break;
        }
    }
}