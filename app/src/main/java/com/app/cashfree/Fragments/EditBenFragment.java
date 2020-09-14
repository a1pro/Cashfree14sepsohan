package com.app.cashfree.Fragments;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.app.cashfree.utils.CommonUtils;
import com.app.cashfree.Interface.ApiInterface;
import com.app.cashfree.Model.AutorizationModel;
import com.app.cashfree.Model.GetBenModel;
import com.app.cashfree.Model.SignatureModel;
import com.app.cashfree.R;
import com.app.cashfree.Retrofit.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class EditBenFragment extends Fragment implements View.OnClickListener {
    private EditText et_name, et_email, et_phone, et_bank_ac, et_ifsc, et_address, et_city, et_state, et_pin;
    private Button btn;
    private Retrofit retrofit;
    private String token;
    NavController navController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_ben, container, false);
        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        initUI(view);
        hitGetSignatureApi();
        return view;
    }

    private void initUI(View view) {
        btn = view.findViewById(R.id.btn_sub);
        btn.setOnClickListener(this);
        et_name = view.findViewById(R.id.et_name);
        et_email = view.findViewById(R.id.et_email);
        et_phone = view.findViewById(R.id.et_phone);
        et_bank_ac = view.findViewById(R.id.et_bank_ac);
        et_ifsc = view.findViewById(R.id.et_ifsc);
        et_address = view.findViewById(R.id.et_address);
        et_city = view.findViewById(R.id.et_city);
        et_state = view.findViewById(R.id.et_state);
        et_pin = view.findViewById(R.id.et_pin);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_sub:
                //navController.navigate(R.id.editBenFragment);
                break;
        }

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


    private void Autorize(String sig_value) {
        retrofit = RetrofitClient.retroInitHeaderAuthorisation(sig_value);
        final ApiInterface apiListener = retrofit.create(ApiInterface.class);
        Call<AutorizationModel> call = apiListener.AutorizationApi();
        call.enqueue(new Callback<AutorizationModel>() {
            @Override
            public void onResponse(Call<AutorizationModel> call, Response<AutorizationModel> response) {
                if (response.isSuccessful()) {
                    AutorizationModel data = response.body();
                    if (data != null) {
                        if (data.getSubCode().equalsIgnoreCase("200")) {
                            GetBen(data.getData().getToken());
                            Log.e("token", data.getData().getToken());
                            SaveToken(data.getData().getToken());
                            token = data.getData().getToken();
                            RetrofitClient retrofitClient = new RetrofitClient(getContext(), token);
                            Toast.makeText(getContext(), "" + data.getMessage(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "" + data.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<AutorizationModel> call, Throwable t) {
                Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void SaveToken(String token) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Token", token);
        editor.apply();
    }


    private void GetBen(String token) {
        final Dialog dialog = CommonUtils.showProgress(getActivity());
        retrofit = RetrofitClient.retroInitHeader(token);
        final ApiInterface apiListener = retrofit.create(ApiInterface.class);
        Call<GetBenModel> call = apiListener.getBen("Sohan18w011q670");
        call.enqueue(new Callback<GetBenModel>() {
            @Override
            public void onResponse(Call<GetBenModel> call, Response<GetBenModel> response) {
                if (response.isSuccessful()) {
                    GetBenModel data = response.body();
                    if (data != null) {
                        if (data.getSubCode().equalsIgnoreCase("200")) {
                            et_name.setText(data.getData().getName());
                        }
                    }
                    dialog.dismiss();
                    try {
                        Log.e("response-success", response.body().toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<GetBenModel> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
//                progress.setVisibility(View.INVISIBLE);
            }
        });
    }
}
