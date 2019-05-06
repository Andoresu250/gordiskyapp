package com.andoresu.gordiskyapp.core.transactions;

import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import com.andoresu.gordiskyapp.R;
import com.andoresu.gordiskyapp.core.transactions.data.Transaction;
import com.andoresu.gordiskyapp.utils.BaseRecyclerViewAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TransactionViewHolder extends BaseRecyclerViewAdapter.BaseViewHolder<Transaction> {

    @BindView(R.id.backCardView)
    public CardView backCardView;
    @BindView(R.id.transactionDescriptionTextView)
    public TextView transactionDescriptionTextView;
    @BindView(R.id.transactionValueTextView)
    public TextView transactionValueTextView;
    @BindView(R.id.transactionDateTextView)
    public TextView transactionDateTextView;

    public TransactionViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
