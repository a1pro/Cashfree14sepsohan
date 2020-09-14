package com.app.cashfree.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.app.cashfree.R;

public class ChooseBankActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tv_debit_credit,tv_bank_account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_bank);
        initUI();
    }

    private void initUI() {
        tv_bank_account=findViewById(R.id.tv_bank_account);
        tv_debit_credit=findViewById(R.id.tv_debit_credit);
        tv_bank_account.setOnClickListener(this);
        tv_debit_credit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_bank_account:
            case R.id.tv_debit_credit:
                Intent i=new Intent(ChooseBankActivity.this,AddCardActivity.class);
                startActivity(i);
                break;
        }
    }
}