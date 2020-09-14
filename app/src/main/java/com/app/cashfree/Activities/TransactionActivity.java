package com.app.cashfree.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.app.cashfree.utils.CommonUtils;
import com.app.cashfree.Interface.ApiInterface;
import com.app.cashfree.Model.TransModel;
import com.app.cashfree.R;
import com.app.cashfree.RVTransactionHistory;
import com.app.cashfree.Retrofit.RetrofitClient;
import com.app.cashfree.utils.PreferenceHandler;

import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class TransactionActivity extends AppCompatActivity implements View.OnClickListener {
    RVTransactionHistory rvTransactionHistoryadaptet;
    RecyclerView rvTrans;
    private ImageView iv_back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        rvTrans=findViewById(R.id.rvTrans);
        iv_back=findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        hitTransactipn();
    }

    private void hitTransactipn() {
        Log.e("wallletId",PreferenceHandler.readString(TransactionActivity.this, "walletId", ""));
        final Dialog dialog = CommonUtils.showProgress(TransactionActivity.this);
        Retrofit retrofit = RetrofitClient.retroInit();
        final ApiInterface apiListener = retrofit.create(ApiInterface.class);
        Call<TransModel> call = apiListener.transaction_history(PreferenceHandler.readString(TransactionActivity.this,
               "walletId", ""));
        call.enqueue(new Callback<TransModel>() {
            @Override
            public void onResponse(Call<TransModel> call, Response<TransModel> response) {
                if (response.isSuccessful()) {
                    TransModel data = response.body();
                    if (data.getCode().equalsIgnoreCase("201")) {
                        Collections.reverse(data.getData());
                        dialog.dismiss();
                        if (data.getData().size()>0){
                            rvTransactionHistoryadaptet=new RVTransactionHistory(TransactionActivity.this,data);
                            rvTrans.setLayoutManager(new LinearLayoutManager(TransactionActivity.this));
                            rvTrans.setAdapter(rvTransactionHistoryadaptet);
                        }else {
                   //         Toast.makeText(TransactionActivity.this, "0 Size", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                 //       Toast.makeText(TransactionActivity.this, ""+data.getStatus(), Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                }
            }

            @Override
            public void onFailure(Call<TransModel> call, Throwable t) {
                dialog.dismiss();
                //Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
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
