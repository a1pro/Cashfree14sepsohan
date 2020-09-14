package com.app.cashfree.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.cashfree.Activities.AddBankAccountActivity;
import com.app.cashfree.Activities.AddCardActivity;
import com.app.cashfree.Activities.AddMoneyFirstActivity;
import com.app.cashfree.Activities.ChooseBankActivity;
import com.app.cashfree.Activities.LoginActivity;
import com.app.cashfree.Activities.ScannerActivity;
import com.app.cashfree.Activities.TransactionActivity;
import com.app.cashfree.Activities.TransferActivity;
import com.app.cashfree.utils.CommonUtils;
import com.app.cashfree.Interface.ApiInterface;
import com.app.cashfree.Model.AutorizationModel;
import com.app.cashfree.Model.CheckBalanceModel;
import com.app.cashfree.Model.CheckBalanceModelNew;
import com.app.cashfree.Model.GenricModel;
import com.app.cashfree.Model.SignatureModel;
import com.app.cashfree.R;
import com.app.cashfree.Retrofit.RetrofitClient;
import com.app.cashfree.utils.PreferenceHandler;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomeFragment extends Fragment implements View.OnClickListener {
    private TextView tv_wallet_balance,tv_bank_balance,tv_logout;
    private Retrofit retrofit;
    private String userid;
    private LinearLayout ll_addmoney,ll_add_bankaccount;
    LinearLayout ll_pay,ll_card,ll_add_acc,ll_add_money_wallet,ll_transfer,ll_trans_his,ll_setting;
    private NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home, container, false);
        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        userid = preferences.getString("userid", null);
        initUI(view);
        hitGetSignatureApi();
        return view;
    }

    private void initUI(View view) {
        ll_add_bankaccount=view.findViewById(R.id.ll_add_bankaccount);
        ll_addmoney=view.findViewById(R.id.ll_addmoney);
        ll_add_acc=view.findViewById(R.id.ll_add_acc);
        ll_card=view.findViewById(R.id.ll_card);
        ll_pay=view.findViewById(R.id.ll_pay);
        tv_logout=view.findViewById(R.id.tv_logout);
        tv_wallet_balance=view.findViewById(R.id.tv_wallet_balance);
        tv_logout.setOnClickListener(this);
        ll_addmoney.setOnClickListener(this);
        ll_add_bankaccount.setOnClickListener(this);
        ll_card.setOnClickListener(this);
       /* ll_pay.setOnClickListener(this);

        ll_add_acc.setOnClickListener(this);
        ll_add_money_wallet.setOnClickListener(this);
        ll_transfer.setOnClickListener(this);
        ll_trans_his.setOnClickListener(this);
        ll_setting.setOnClickListener(this);*/
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

                }
            }

            @Override
            public void onFailure(Call<SignatureModel> call, Throwable t) {

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
                    AutorizationModel data = response.body();
                    if (data != null) {
                        if (data.getSubCode().equalsIgnoreCase("200")) {
                            if (data.getData().getToken() != null)
                                SaveToken(data.getData().getToken());
                            newBalanceAPI();
                            CheckBalanceApi(data.getData().getToken());
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
                        if (data.getData().get(0).getWalletAmount() != null)
                            tv_wallet_balance.setText("Wallet Balance : " + data.getData().get(0).getWalletAmount() + " $");
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

    private void CheckBalanceApi(String token) {
        //  final Dialog dialog = CommonUtils.showProgress(getActivity());
        retrofit = RetrofitClient.retroInitHeader(token);
        final ApiInterface apiListener = retrofit.create(ApiInterface.class);
        Call<CheckBalanceModel> call = apiListener.ChcekBalance();
        call.enqueue(new Callback<CheckBalanceModel>() {
            @Override
            public void onResponse(Call<CheckBalanceModel> call, Response<CheckBalanceModel> response) {
                if (response.isSuccessful()) {
                    //     dialog.dismiss();
                    CheckBalanceModel data = response.body();
                    if (data != null) {
                        if (data.getSubCode().equalsIgnoreCase("200")) {
                            //         dialog.dismiss();
                        //    tv_bank_balance.setText("Bank Account Balance :"+data.getData().getAvailableBalance());
                        } else {
                            //    dialog.dismiss();
                            Toast.makeText(getContext(), "" + data.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<CheckBalanceModel> call, Throwable t) {
                // dialog.dismiss();
                Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
//                progress.setVisibility(View.INVISIBLE);
            }
        });
    }

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
    private void SaveToken(String token) {
        try {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("Token", token);
            editor.apply();
        } catch (Exception e) {

        }

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ll_add_bankaccount:
                Intent intent2=new Intent(getContext(), ChooseBankActivity.class);
                startActivity(intent2);
                break;
            case R.id.ll_card:
                Intent i=new Intent(getContext(), TransferActivity.class);
                startActivity(i);
                break;
            case R.id.ll_add_acc:
            //    navController.navigate(R.id.addBenficiaryFragment);
                break;
            case R.id.ll_pay:
                Intent i1=new Intent(getContext(), ScannerActivity.class);
                startActivity(i1);
                break;

            case R.id.ll_addmoney:
                Intent intent=new Intent(getContext(), AddMoneyFirstActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_logout:
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                alertDialog.setMessage("Are you sure you want to logout?");
                alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        PreferenceHandler.clearPref(getContext());
                        logout();

                    }
                });
                alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                alertDialog.show();
                break;

        }
    }
}
