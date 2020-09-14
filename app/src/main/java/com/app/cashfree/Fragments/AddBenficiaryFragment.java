package com.app.cashfree.Fragments;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

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

import com.app.cashfree.utils.CommonUtils;
import com.app.cashfree.Interface.ApiInterface;
import com.app.cashfree.Model.AddBenDetail;
import com.app.cashfree.Model.AutorizationModel;
import com.app.cashfree.Model.SignatureModel;
import com.app.cashfree.R;
import com.app.cashfree.Retrofit.RetrofitClient;
import com.app.cashfree.utils.PreferenceHandler;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class AddBenficiaryFragment extends Fragment implements View.OnClickListener {
    private EditText et_name, et_email, et_phone, et_bank_ac, et_ifsc, et_address, et_city, et_state, et_pin, et_beneId;
    private Button btn;
    private Retrofit retrofit;
    private String token;
    String[] bank = {"026291800001191", "00011020001772", "000890289871772", "000100289877623"};
    String[] ifsc1 = {"YESB0000262", "HDFC0000001", "SCBL0036078", "SBIN0008752"};
    TextView tv_bank;
    String bank_account, ifsc_lst;
    private NavController navController;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_benficiary, container, false);
        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
        initUI(view);
        setSpin(view);
        return view;
    }

    private void SaveToken(String token) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Token", token);
        editor.apply();
    }

    private void setSpin(View view) {
        Spinner spin = view.findViewById(R.id.spinner);
        final Spinner ifsc = view.findViewById(R.id.spinner_ifsc);
        ifsc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ifsc_lst = ifsc1[i];
                if (i == 0) {
                    tv_bank.setVisibility(View.VISIBLE);
                    tv_bank.setText("YES BANK");
                } else if (i == 1) {
                    tv_bank.setVisibility(View.VISIBLE);
                    tv_bank.setText("HDFC BANK");
                } else if (i == 2) {
                    tv_bank.setVisibility(View.VISIBLE);
                    tv_bank.setText("STANDARD CHARTERED BANK");
                } else if (i == 3) {
                    tv_bank.setVisibility(View.VISIBLE);
                    tv_bank.setText("SBI BANK");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                bank_account = bank[i];
                if (i == 0) {
                    tv_bank.setVisibility(View.VISIBLE);
                    tv_bank.setText("YES BANK");
                } else if (i == 1) {
                    tv_bank.setVisibility(View.VISIBLE);
                    tv_bank.setText("HDFC BANK");
                } else if (i == 2) {
                    tv_bank.setVisibility(View.VISIBLE);
                    tv_bank.setText("STANDARD CHARTERED BANK");
                } else if (i == 3) {
                    tv_bank.setVisibility(View.VISIBLE);
                    tv_bank.setText("SBI BANK");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter aa = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, bank);
        ArrayAdapter ifsc_list = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, ifsc1);

        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ifsc_list.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);
        ifsc.setAdapter(ifsc_list);
    }

    private void initUI(View view) {
        tv_bank = view.findViewById(R.id.tv_bank);
        et_beneId = view.findViewById(R.id.et_beneId);
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
                            SaveToken(data.getData().getToken());
                            AddBen(data.getData().getToken());
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


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_sub:
                if (Validations()){
                    hitGetSignatureApi();
                }

                break;
        }
    }


    private void AddBen(String token) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("beneId", et_beneId.getText().toString());
        jsonObject.addProperty("name", et_name.getText().toString());
        jsonObject.addProperty("email", et_email.getText().toString());
        jsonObject.addProperty("phone", et_phone.getText().toString());
        jsonObject.addProperty("bankAccount", bank_account);
        jsonObject.addProperty("ifsc", ifsc_lst);
        jsonObject.addProperty("address1", et_address.getText().toString());
        jsonObject.addProperty("city", et_city.getText().toString());
        jsonObject.addProperty("state", et_state.getText().toString());
        jsonObject.addProperty("pincode", et_pin.getText().toString());

        final Dialog dialog = CommonUtils.showProgress(getActivity());

        retrofit = RetrofitClient.retroInitHeader(token);
        final ApiInterface apiListener = retrofit.create(ApiInterface.class);
        Call<JsonObject> call = apiListener.addBen(jsonObject);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                JsonObject jsonObject1 = response.body();
                if (response.isSuccessful()) {

                    PreferenceHandler.writeString(getContext(), "ben", et_beneId.getText().toString());
                  //  SaveBene(et_beneId.getText().toString());
                    dialog.dismiss();
                    try {
                        JSONObject json2 = new JSONObject(jsonObject1.toString());
                        String subcode=json2.getString("subCode");
                        if (subcode.equalsIgnoreCase("200")){
                            hitAddBenDetail();
                            Toast.makeText(getContext(), "" + json2.getString("message"), Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getContext(), "" + json2.getString("message"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
//                    try {
//
//                        JSONObject json = new JSONObject(jsonObject1.toString());
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }

                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getuserid() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        return preferences.getString("userid", null);
    }


    private boolean Validations(){
        if (et_beneId.getText().toString().isEmpty()){
            Toast.makeText(getContext(), "Please Provide a valid Beneficiary Nick Name", Toast.LENGTH_SHORT).show();
            return false;
        }else if (et_name.getText().toString().isEmpty()){
            Toast.makeText(getContext(), "Please Enter Name", Toast.LENGTH_SHORT).show();
            return false;
        }else if (et_email.getText().toString().isEmpty()){
            Toast.makeText(getContext(), "Please Enter  Email", Toast.LENGTH_SHORT).show();
            return false;
        }else if (et_phone.getText().toString().isEmpty()){
            Toast.makeText(getContext(), "Please Enter  Phone Number", Toast.LENGTH_SHORT).show();
            return false;
        }else if (et_address.getText().toString().isEmpty()){
            Toast.makeText(getContext(), "Please Enter Address", Toast.LENGTH_SHORT).show();
            return false;
        }else if (et_city.getText().toString().isEmpty()){
            Toast.makeText(getContext(), "Please Enter City", Toast.LENGTH_SHORT).show();
            return false;
        }else if (et_state.getText().toString().isEmpty()){
            Toast.makeText(getContext(), "Please Enter State", Toast.LENGTH_SHORT).show();
            return false;
        }else if (et_pin.getText().toString().isEmpty()){
            Toast.makeText(getContext(), "Please Enter Pincode", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    private void hitAddBenDetail() {
        retrofit = RetrofitClient.retroInit();
        final ApiInterface apiListener = retrofit.create(ApiInterface.class);
        Call<AddBenDetail> call = apiListener.AddBeneficiary(et_beneId.getText().toString(),getuserid(),et_name.getText().toString());
        call.enqueue(new Callback<AddBenDetail>() {
            @Override
            public void onResponse(Call<AddBenDetail> call, Response<AddBenDetail> response) {
                AddBenDetail data = response.body();
                if (data!=null) {
                    if (data.getCode().equalsIgnoreCase("201")) {
                        Toast.makeText(getContext(), ""+data.getStatus(), Toast.LENGTH_SHORT).show();
                        SaveBene(data.getData().get(0).getBeneficiaryId());
                        SaveBenename(data.getData().get(0).getBeneficiaryName());

                    } else {
                        Toast.makeText(getContext(), ""+data.getStatus(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<AddBenDetail> call, Throwable t) {

            }
        });
    }


    private void SaveBene(String bene) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("benficairyid", bene);
        editor.apply();
    }

    private void SaveBenename(String bene) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("benficairyname", bene);
        editor.apply();
    }





}