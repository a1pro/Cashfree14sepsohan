package com.app.cashfree.Fragments;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
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

import com.app.cashfree.Activities.LoginActivity;
import com.app.cashfree.utils.CommonUtils;
import com.app.cashfree.Interface.ApiInterface;
import com.app.cashfree.Model.CheckBalanceModelNew;
import com.app.cashfree.Model.GenricModel;
import com.app.cashfree.R;
import com.app.cashfree.Retrofit.RetrofitClient;
import com.app.cashfree.utils.Constants;
import com.app.cashfree.utils.PreferenceHandler;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.content.Context.MODE_PRIVATE;


public class BalanceFragment extends Fragment implements View.OnClickListener {
    private Button btn_add;
    private Retrofit retrofit;
    private TextView txt_account_holder;
    private EditText et_amount;

    private Spinner spinner;
    private String val;
    private SharedPreferences sharedPreferences;
    private String userid;
    //private String card_list_single;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_balance, container, false);
        sharedPreferences = getActivity().getSharedPreferences(Constants.pref, MODE_PRIVATE);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        userid = preferences.getString("userid", null);
        init(view);
        setSpin(view);


        return view;
    }

    private void setSpin(View view) {
        ArrayList<String> cardlists=new ArrayList<>();
        cardlists.add(0,"Select Card");
        if (getcardno()!=null){
            cardlists.add(1,getcardno());
        }

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               if (i == 0) {
                    txt_account_holder.setVisibility(View.INVISIBLE);
                    val = "Select card";
                }else   if (i == 1) {
                   val = "Select";
                   txt_account_holder.setText("Account holder name : " + GetCardHolder());
                   txt_account_holder.setVisibility(View.VISIBLE);

               }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter card_list_adap = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, cardlists);
        card_list_adap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(card_list_adap);
    }


    private void newBalanceAPI() {
        //  final Dialog dialog = CommonUtils.showProgress(getActivity());
        retrofit = RetrofitClient.retroInit();
        final ApiInterface apiListener = retrofit.create(ApiInterface.class);
        Call<CheckBalanceModelNew> call = apiListener.getWallet(userid);
        call.enqueue(new Callback<CheckBalanceModelNew>() {
            @Override
            public void onResponse(Call<CheckBalanceModelNew> call, Response<CheckBalanceModelNew> response) {
                if (response.isSuccessful()) {
                    CheckBalanceModelNew data = response.body();
                    if (data.getCode().equalsIgnoreCase("201")) {
                        //       dialog.dismiss();
                        //    Toast.makeText(getContext(), "" + data.getStatus(), Toast.LENGTH_SHORT).show();
                        if (data.getData().get(0).getWalletAmount() != null){
                 //           txt_amount.setText("Wallet Balance : " + data.getData().get(0).getWalletAmount() + " $");
                        }
                    } else {
                        //   dialog.dismiss();
                        //   Toast.makeText(getContext(), "" + data.getStatus(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<CheckBalanceModelNew> call, Throwable t) {
                //    dialog.dismiss();
                //   Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

//    private String getuserid () {
//        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
//        return preferences.getString("userid", null);
//    }


    private void logout() {
        final Dialog dialog = CommonUtils.showProgress(getActivity());
        dialog.show();
        retrofit = RetrofitClient.retroInit();
        final ApiInterface apiListener = retrofit.create(ApiInterface.class);
        Call<GenricModel> call = apiListener.logout(getuserid());
        call.enqueue(new Callback<GenricModel>() {
            @Override
            public void onResponse(Call<GenricModel> call, Response<GenricModel> response) {
                if (response.isSuccessful()) {
                    GenricModel data = response.body();
                    if (data.getCode().equalsIgnoreCase("201")) {
                        dialog.dismiss();
                        Intent i = new Intent(getContext(), LoginActivity.class);
                        startActivity(i);
                        getActivity().finish();

                    } else {
                        dialog.dismiss();
                        Toast.makeText(getContext(), "" + data.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<GenricModel> call, Throwable t) {
                dialog.dismiss();
                //    Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getuserid() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        return preferences.getString("userid", null);
    }


    private String getcardno() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        return preferences.getString("card", null);
    }

    private String GetCardHolder() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        return preferences.getString("cardholder", null);
    }




    private void AddWallet() {
        final Dialog dialog = CommonUtils.showProgress(getActivity());
        retrofit = RetrofitClient.retroInit();
        final ApiInterface apiListener = retrofit.create(ApiInterface.class);
        Call<GenricModel> call = apiListener.addWallet(PreferenceHandler.readString(getContext(),
                PreferenceHandler.USER_ID, ""), et_amount.getText().toString(),
                PreferenceHandler.readString(getContext(), "walletId", ""));
        call.enqueue(new Callback<GenricModel>() {
            @Override
            public void onResponse(Call<GenricModel> call, Response<GenricModel> response) {
                if (response.isSuccessful()) {
                    GenricModel data = response.body();
                    if (data.getCode().equalsIgnoreCase("201")) {
                        dialog.dismiss();
                        et_amount.setText("");
                        if (userid != null) {
                            newBalanceAPI();
                        }

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





    private void init(View view) {
        spinner = view.findViewById(R.id.spinner_card_list);

        et_amount = view.findViewById(R.id.et_amount);
        btn_add = view.findViewById(R.id.btn_add);
        txt_account_holder = view.findViewById(R.id.txt_account_holder);
        btn_add.setOnClickListener(this);

        sharedPreferences = getActivity().getSharedPreferences(Constants.pref, MODE_PRIVATE);
    }


    @Override
    public void onClick(View view) {


        switch (view.getId()) {

            case R.id.btn_add:
                if (!val.equalsIgnoreCase("Select card") && (getcardno()!=null)) {
                    AddWallet();
                } else {
                    Toast.makeText(getActivity(), "Please select card", Toast.LENGTH_LONG).show();
                }
                break;

        }
    }
}