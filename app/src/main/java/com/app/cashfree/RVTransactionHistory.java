package com.app.cashfree;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.app.cashfree.Activities.ScannerActivity;
import com.app.cashfree.Activities.TransactionActivity;
import com.app.cashfree.Model.GenricModel;
import com.app.cashfree.Model.TransModel;
import com.app.cashfree.utils.PreferenceHandler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class RVTransactionHistory extends RecyclerView.Adapter<RVTransactionHistory.RVTransactionHistoryHolder> {
    TransactionActivity context;
    TransModel data;
    public RVTransactionHistory(TransactionActivity context, TransModel data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public RVTransactionHistoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_trans, parent, false);
        return new RVTransactionHistoryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RVTransactionHistoryHolder holder, int position) {

        if(PreferenceHandler.readString(context,
                "walletId", "").equalsIgnoreCase(data.getData().get(position).getsWalletId())){
            holder.tv_amount.setTextColor(context.getResources().getColor(R.color.red));
            holder.tv_amount.setText(data.getData().get(position).getAmount());
        }else {
            holder.tv_amount.setTextColor(context.getResources().getColor(R.color.CTA_bg));
            holder.tv_amount.setText(data.getData().get(position).getAmount());

        }
        holder.tv_wallet_id.setText(data.getData().get(position).getrWalletId());
        holder.tv_wallet_id.setText(data.getData().get(position).getsWalletId());
        holder.tv_date.setText(changeTimeUnixToGMT(data.getData().get(position).getUpdatedAt()));
        if (getWalletid().equalsIgnoreCase(data.getData().get(position).getsWalletId())){
            if (data.getData().get(position).getTrnasType().equalsIgnoreCase("2")){
                holder.tv_sender.setText("Paid to: "+data.getData().get(position).getRecevierNames());
            }else {
                holder.tv_sender.setText("Paid to: "+data.getData().get(position).getReceiverName());
            }
        }else {
            holder.tv_sender.setText("Paid by: "+data.getData().get(position).getSenderName());
        }
    }

    @Override
    public int getItemCount() {
        return data.getData().size();
    }

    public class RVTransactionHistoryHolder extends RecyclerView.ViewHolder {
        private TextView tv_wallet_id, tv_amount, tv_date,tv_reciver,tv_sender;

        public RVTransactionHistoryHolder(@NonNull View itemView) {
            super(itemView);
            //tv_sender=itemView.findViewById(R.id.tv_sender);
          //  tv_wallet_id = itemView.findViewById(R.id.tv_wallet_id);
            tv_amount = itemView.findViewById(R.id.tv_amount);
         //   tv_date = itemView.findViewById(R.id.tv_date);
        }
    }


    private String getuserid () {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString("userid", null);
    }


    private String getWalletid () {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString("walletid", null);
    }

    private String changeTimeUnixToGMT(String ourDate) {

        {
            try {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd H:mm:s", Locale.getDefault());
//                formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
                formatter.setTimeZone(TimeZone.getDefault());
                Date value = formatter.parse(ourDate);

                SimpleDateFormat dateFormatter = new SimpleDateFormat("EEE,dd MMM yyyy, hh:mm a", Locale.getDefault()); //this format changeable
                dateFormatter.setTimeZone(TimeZone.getDefault());
                ourDate = dateFormatter.format(value);

                Log.d("ourDate", ourDate);
            } catch (Exception e) {
                ourDate = "00-00-0000 00:00";
            }
            return ourDate;
        }
    }
}
