package com.andoresu.gordiskyapp.core.transactions;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andoresu.gordiskyapp.R;
import com.andoresu.gordiskyapp.core.people.PersonViewHolder;
import com.andoresu.gordiskyapp.core.transactions.data.Transaction;
import com.andoresu.gordiskyapp.list.RecyclerViewAdapter;

import java.util.ArrayList;

public class TransactionAdapter extends RecyclerViewAdapter<Transaction> {

    public TransactionAdapter(Context context) {
        super(context);
    }

    @Override
    public void setData(BaseViewHolder<Transaction> holder, int position) {
        TransactionViewHolder viewHolder = (TransactionViewHolder) holder;
        Transaction transaction = get(position);
        if(transaction.isActivo()){
            viewHolder.backCardView.setCardBackgroundColor(context.getResources().getColor(R.color.colorAccent));
        }else{
            viewHolder.backCardView.setCardBackgroundColor(context.getResources().getColor(R.color.colorAlert));
        }
        viewHolder.transactionDateTextView.setText(transaction.getSimpleCreatedAt());
        viewHolder.transactionValueTextView.setText(transaction.getValue());
        viewHolder.transactionDescriptionTextView.setText(transaction.description);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.item_transaction;
    }

    @NonNull
    @Override
    public BaseViewHolder<Transaction> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(getLayoutResId(), parent, false);
        return new TransactionViewHolder(view);
    }
}
