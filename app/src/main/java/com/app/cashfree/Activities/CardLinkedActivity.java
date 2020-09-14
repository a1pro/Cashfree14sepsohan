package com.app.cashfree.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.app.cashfree.R;

public class CardLinkedActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView iv_tick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_linked);
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
                Intent i=new Intent(CardLinkedActivity.this,HomeActivity.class);
                startActivity(i);
                finish();
                break;
        }
    }
}