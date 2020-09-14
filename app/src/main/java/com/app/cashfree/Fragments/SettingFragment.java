package com.app.cashfree.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.cashfree.Activities.MyProfileActivity;
import com.app.cashfree.Activities.SettingActivity;
import com.app.cashfree.Activities.TransactionActivity;
import com.app.cashfree.R;
import com.app.cashfree.utils.PreferenceHandler;
import com.bumptech.glide.Glide;

import java.util.Objects;


public class SettingFragment extends Fragment  {
    TextView tvSetting,tvMyScan,tranaction,Profile_tv;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_setting, container, false);
       // initUI(view);
        return view;
    }

    /*private void initUI(View view) {
        Profile_tv=view.findViewById(R.id.Profile_tv);
        tranaction=view.findViewById(R.id.tranaction);
        tvSetting=view.findViewById(R.id.tvSetting);
        tvMyScan=view.findViewById(R.id.tvMyScan);
        tvMyScan.setOnClickListener(this);
        tvSetting.setOnClickListener(this);
        tranaction.setOnClickListener(this);
        Profile_tv.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tvSetting:
            Intent i=new Intent(getContext(), SettingActivity.class);
            startActivity(i);
            break;
            case R.id.tvMyScan:
                final Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.scan_dialog);
                dialog.setCanceledOnTouchOutside(true);
                Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                final RelativeLayout mainview = (RelativeLayout) dialog.findViewById(R.id.scan);
                ImageView iv_scanner=dialog.findViewById(R.id.iv_scanner);
                mainview.getLayoutParams().width = getHeightWidth("width", getContext()) - 20;

                Glide
                        .with(getContext())
                        .load("http://a1professionals.net/chessFree/uploads/qr_image/" +
                                PreferenceHandler.readString(getContext(),"myscanner",""))
                        .centerCrop()
                        .into(iv_scanner);

                dialog.show();
                break;
            case R.id.tranaction:
                Intent i1=new Intent(getContext(), TransactionActivity.class);
                startActivity(i1);
                break;
            case R.id.Profile_tv:
                Intent i2=new Intent(getContext(), MyProfileActivity.class);
                startActivity(i2);
                break;
        }
    }
    public static int getHeightWidth(String mode, Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        int lenthval = 0;
        if (mode.equalsIgnoreCase("height")) {
            lenthval = height;
        } else if (mode.equalsIgnoreCase("width")) {
            lenthval = width;
        }
        return lenthval;
    }*/
}
