package com.app.cashfree.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.app.cashfree.Activities.LoginActivity;
import com.app.cashfree.Activities.SignUpActivity;
import com.app.cashfree.R;


public class WelcomeFragment3 extends Fragment implements View.OnClickListener {
    private Button bt_signup;
    private TextView tv_login;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_welcome3, container, false);
        initUI(view);
        return view;
    }

    private void initUI(View view) {
        bt_signup=view.findViewById(R.id.bt_signup);
        tv_login=view.findViewById(R.id.tv_login);
        tv_login.setOnClickListener(this);
        bt_signup.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_signup:
                Intent i=new Intent(getActivity(), SignUpActivity.class);
                startActivity(i);
                break;
            case R.id.tv_login:
                Intent intent=new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                break;
        }
    }
}