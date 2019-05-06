package com.andoresu.gordiskyapp.core.loandetail;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andoresu.gordiskyapp.R;
import com.andoresu.gordiskyapp.core.loans.data.Payment;
import com.andoresu.gordiskyapp.list.RecyclerViewAdapter;

import java.util.ArrayList;

public class PaymentAdapter  extends RecyclerViewAdapter<Payment> {

    public static String TAG = "GORDISKY_" + PaymentAdapter.class.getSimpleName();

    public final boolean isSimpleView;

    public PaymentAdapter(Context context, @NonNull ArrayList<Payment> items, OnItemClickListener<Payment> listener, boolean isSimpleView) {
        super(context, items, listener);
        this.isSimpleView = isSimpleView;
    }

    public PaymentAdapter(Context context, boolean isSimpleView) {
        super(context);
        this.isSimpleView = isSimpleView;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void setData(BaseViewHolder<Payment> holder, int position) {
        PaymentViewHolder viewHolder = (PaymentViewHolder) holder;
        Payment payment = get(position);
        viewHolder.paymentNumberEditText.setText(payment.number.toString());
        viewHolder.paymentStateEditText.setText(payment.state);
        viewHolder.paymentValueEditText.setText(payment.getValue());
        viewHolder.paymentDateEditText.setText(payment.getDate());

        if(viewHolder.paymentPaidAtEditText != null) {
            viewHolder.paymentPaidAtEditText.setText(payment.getPaidAt());
        }
        if(viewHolder.paymentPaidValueEditText != null) {
            viewHolder.paymentPaidValueEditText.setText(payment.getPaidValue());
        }
        if(payment.isMissed){
            viewHolder.itemView.setBackgroundColor(context.getResources().getColor(R.color.colorMissed));
            viewHolder.paymentStateEditText.setText("vencida");
        }else{
            viewHolder.itemView.setBackgroundColor(context.getResources().getColor(R.color.colorWhite));
            viewHolder.paymentStateEditText.setText(payment.state);
        }
    }

    @Override
    protected int getLayoutResId() {
        if(isSimpleView){
            return R.layout.item_payment_simple;
        }
        return R.layout.item_payment;
    }

    @NonNull
    @Override
    public BaseViewHolder<Payment> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(getLayoutResId(), parent, false);
        return new PaymentViewHolder(view);
    }
}
