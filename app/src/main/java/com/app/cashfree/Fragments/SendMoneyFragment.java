package com.app.cashfree.Fragments;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.app.cashfree.Activities.CheckPinActivity;
import com.app.cashfree.Activities.ScannerActivity;
import com.app.cashfree.utils.CommonUtils;
import com.app.cashfree.Interface.ApiInterface;
import com.app.cashfree.Model.AutorizationModel;
import com.app.cashfree.Model.GenricModel;
import com.app.cashfree.Model.SignatureModel;
import com.app.cashfree.Model.TranferModel;
import com.app.cashfree.R;
import com.app.cashfree.Retrofit.RetrofitClient;
import com.app.cashfree.utils.PreferenceHandler;
import com.google.gson.JsonObject;
import java.util.Collections;
import java.util.Random;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class SendMoneyFragment extends Fragment implements View.OnClickListener {
    private EditText et_benid,et_amount,et_name,et_amount1,et_wallet_number,et_benid1,et_amount2,et_name1;
    private Button bt_name,bt_wallet,bt_name1;
    private Retrofit retrofit;
    String ben_id;
    TextView bt_scan;
    private String[] card_list = {"441"};
    private Spinner spinner;
    private String card_list_single;
    String beneid,beneName;
    private String token;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_send_money, container, false);
        init(view);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        beneid = preferences.getString("benficairyid", null);
        beneName= preferences.getString("benficairyname", null);
        et_benid1.setText(beneid);
        et_benid.setText(beneid);
        et_name.setText(beneName);
        et_name1.setText(beneName);
        setSpin(view);
        String user_id = PreferenceHandler.readString(getContext(), PreferenceHandler.USER_ID, "");
        Log.e("userid", user_id);

        return view;
    }

    private void setSpin(View view) {
        card_list[0] = PreferenceHandler.readString(getContext(), "card", "");
        spinner = view.findViewById(R.id.spinner_card_list);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                card_list_single = card_list[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter card_list_adap = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, Collections.singletonList(card_list[0]));
        card_list_adap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(card_list_adap);
    }

    private void hitGetSignatureApi() {
        retrofit = RetrofitClient.retroInit();
        final ApiInterface apiListener = retrofit.create(ApiInterface.class);
        Call<SignatureModel> call = apiListener.signature();
        call.enqueue(new Callback<SignatureModel>() {
            @Override
            public void onResponse(Call<SignatureModel> call, Response<SignatureModel> response) {
                SignatureModel data = response.body();
                assert data != null;
                if (data.getCode().equalsIgnoreCase("201")) {
                    String sig_val = data.getData();
                    Autorize(sig_val);
                } else {
                    Toast.makeText(getContext(), ""+data.getStatus(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SignatureModel> call, Throwable t) {
                Toast.makeText(getContext(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void Autorize(String sig_val) {
        retrofit = RetrofitClient.retroInitHeaderAuthorisation(sig_val);
        final ApiInterface apiListener = retrofit.create(ApiInterface.class);
        Call<AutorizationModel> call = apiListener.AutorizationApi();
        call.enqueue(new Callback<AutorizationModel>() {
            @Override
            public void onResponse(Call<AutorizationModel> call, Response<AutorizationModel> response) {
                if (response.isSuccessful()) {
                    final AutorizationModel data = response.body();
                    if (data != null) {
                        if (data.getSubCode().equalsIgnoreCase("200")) {
                            SaveToken(data.getData().getToken());
                            if (validation()) {
                                token=data.getData().getToken();
                                Intent intent=new Intent(getContext(),CheckPinActivity.class);
                                intent.putExtra("type","three");
                                startActivityForResult(intent,3);


                            //    TransferMoney(data.getData().getToken());
                            }
                        } else {
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<AutorizationModel> call, Throwable t) {
            }
        });
    }


    private void init(View view) {
        et_benid1 = view.findViewById(R.id.et_benid1);
        et_amount2 = view.findViewById(R.id.et_amount2);
        et_name1 = view.findViewById(R.id.et_name1);
        bt_name1 = view.findViewById(R.id.bt_name1);
        bt_name1.setOnClickListener(this);
        et_wallet_number = view.findViewById(R.id.et_wallet_number);
        et_amount1 = view.findViewById(R.id.et_amount1);
        bt_wallet = view.findViewById(R.id.bt_wallet);
        bt_scan = view.findViewById(R.id.bt_scan);
        bt_name = view.findViewById(R.id.bt_name);
        et_benid = view.findViewById(R.id.et_benid);
        et_amount = view.findViewById(R.id.et_amount);
        et_name = view.findViewById(R.id.et_name);
        ben_id = PreferenceHandler.readString(getContext(), "ben", "");

        bt_scan.setOnClickListener(this);
        bt_wallet.setOnClickListener(this);
        bt_name.setOnClickListener(this);


    }


    private boolean validation() {
        if (et_benid.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "Please Enter Benficiary Id", Toast.LENGTH_SHORT).show();
            return false;
        } else if (et_amount.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "Please Enter Amount", Toast.LENGTH_SHORT).show();
            return false;
        } else if (et_name.getText().toString().isEmpty()) {
            Toast.makeText(getContext(), "Please Enter Name", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private String getuserid() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        return preferences.getString("userid", null);
    }


    private String getWalletid() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        return preferences.getString("walletid", null);
    }

    private String getbeneficary() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        return preferences.getString("benficairyid", null);
    }

//    private String GetBeneficarybName() {
//        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
//        return preferences.getString("benficairyname", null);
//    }


    private void TransferMoney(String token) {
        final int random = new Random().nextInt(6188) + 2880; // [0, 60] + 20 => [20, 80]
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("beneId", et_benid.getText().toString());
        jsonObject.addProperty("amount", et_amount.getText().toString());
        int s = random;
        Log.e("random_no", String.valueOf(random));
        jsonObject.addProperty("transferId", s);
        final Dialog dialog = CommonUtils.showProgress(getActivity());
        retrofit = RetrofitClient.retroInitHeader(token);
        final ApiInterface apiListener = retrofit.create(ApiInterface.class);
        Call<TranferModel> call = apiListener.TranferBalance(jsonObject);
        call.enqueue(new Callback<TranferModel>() {
            @Override
            public void onResponse(Call<TranferModel> call, Response<TranferModel> response) {
                if (response.isSuccessful()) {
                    TranferModel data = response.body();
                    if (data != null) {
                        if (data.getSubCode().equalsIgnoreCase("200")) {
                            dialog.dismiss();
                            et_amount.getText().clear();
                         //   et_name.getText().clear();
                            Toast.makeText(getContext(), "" + data.getMessage(), Toast.LENGTH_SHORT).show();
                        } else {
                            dialog.dismiss();
                            Toast.makeText(getContext(), "" + data.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<TranferModel> call, Throwable t) {
                Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_scan:
                Intent i = new Intent(getContext(), ScannerActivity.class);
                startActivity(i);
                break;
            case R.id.bt_wallet:
                if (et_wallet_number.getText().toString().equalsIgnoreCase(getWalletid())){
                    Toast.makeText(getContext(), "Please Enter Valid Wallet Number", Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent=new Intent(getContext(), CheckPinActivity.class);
                    intent.putExtra("type","one");
                    startActivityForResult(intent,1);

//                    hitTransferApi("1");
                }
                
                break;
            case R.id.bt_name:
                if (getbeneficary() != null) {

                    hitGetSignatureApi();
                } else {
                    Toast.makeText(getContext(), "Please Add Beneficiary Account", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.bt_name1:
                if (getbeneficary() != null) {
                    if (validaionsss()){
                        Intent intent=new Intent(getContext(), CheckPinActivity.class);
                        intent.putExtra("type","two");
                        startActivityForResult(intent,2);
//                        hitTransferApi("2");
                    }

                } else {
                    Toast.makeText(getContext(), "Please Add Beneficiary Account", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void SaveToken(String token) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Token", token);
        editor.apply();
    }

    private boolean validaionsss(){
        if (et_amount2.getText().toString().isEmpty()){
            Toast.makeText(getContext(), "Please Enter Amount", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (et_name1.getText().toString().isEmpty()){
            Toast.makeText(getContext(), "Please Enter Name", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void hitTransferApi(String key) {
        String user_id, wallet_id, wallet_number, amount;
        if (key.equalsIgnoreCase("1")) {
            user_id = getuserid();
            wallet_id = getWalletid();
            wallet_number = et_wallet_number.getText().toString();
            amount = et_amount1.getText().toString();
        } else {
            user_id = getuserid();
            wallet_id = getWalletid();
            wallet_number = et_benid1.getText().toString();
            amount = et_amount2.getText().toString();
        }
        final Dialog dialog = CommonUtils.showProgress(getActivity());
        Retrofit retrofit = RetrofitClient.retroInit();
        final ApiInterface apiListener = retrofit.create(ApiInterface.class);
        Call<GenricModel> call = apiListener.tranfer_money(user_id,
                wallet_id, wallet_number, amount, key, getbeneficary());
        call.enqueue(new Callback<GenricModel>() {
            @Override
            public void onResponse(Call<GenricModel> call, Response<GenricModel> response) {
                GenricModel data = response.body();
                if (data != null) {
                    if (data.getCode().equalsIgnoreCase("201")) {
                        dialog.dismiss();
                        et_wallet_number.setText("");
                        et_amount2.getText().clear();
                  //      et_name1.getText().clear();
                        et_amount1.setText("");
                        Toast.makeText(getContext(), "" + data.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        dialog.dismiss();
                        Toast.makeText(getContext(), "" + data.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<GenricModel> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1){
            if (data!=null) {
                if (data.getStringExtra("result") != null) {
                    hitTransferApi("1");
                }
            }

        }
        if (requestCode==2){
            if (data!=null) {
                if (data.getStringExtra("result") != null) {
                    hitTransferApi("2");
                }
            }
        }
        if (requestCode==3){
            if (data!=null) {
                if (data.getStringExtra("result") != null) {
                    TransferMoney(token);
                }
            }
        }
    }
}