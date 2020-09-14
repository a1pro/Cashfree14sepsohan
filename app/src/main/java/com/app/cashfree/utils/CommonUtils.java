package com.app.cashfree.utils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.app.cashfree.R;

public class CommonUtils extends AppCompatActivity {
        private static CommonUtils commonUtils;

        public static CommonUtils getInstance() {
            if (commonUtils == null) {
                commonUtils = new CommonUtils();
            }
            return commonUtils;
        }

        public static boolean isNetworkAvailable(Context context) {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = manager.getActiveNetworkInfo();
            return info != null && info.isConnected();
        }

        public static void closeKeyBoard(FragmentActivity activity) {
            try {
                InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
            } catch (Exception e) {
                // do nothing
                e.printStackTrace();
            }
        }

        public static void myLog(String tag, String message) {
            boolean isDebuggerOn = true;
            if (isDebuggerOn) {
                try {
                    Log.d(tag, message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        public static void showSmallToast(Context context, String message) {
            Toast.makeText(context.getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        }

        public static void showLongToast(Context context, String message) {
            Toast.makeText(context.getApplicationContext(), message, Toast.LENGTH_LONG).show();
        }


    public static Dialog showProgress(Activity activity) {
        Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(0));
        dialog.setContentView(R.layout.dialog_progress);
        ProgressBar progressBar = dialog.findViewById(R.id.progressBar);
        progressBar.getIndeterminateDrawable().setColorFilter(activity.getResources().getColor(R.color.colorPrimaryDark), PorterDuff.Mode.MULTIPLY);
        dialog.setCancelable(false);
        dialog.show();
        return dialog;
    }
}
