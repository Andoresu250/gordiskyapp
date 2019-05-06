package com.andoresu.gordiskyapp.core.loans;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andoresu.gordiskyapp.R;

import com.andoresu.gordiskyapp.core.loans.data.Loan;
import com.andoresu.gordiskyapp.list.RecyclerViewAdapter;


public class LoanAdapter extends RecyclerViewAdapter<Loan> {

    public LoanAdapter(Context context, OnItemClickListener<Loan> listener) {
        super(context, listener);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void setData(BaseViewHolder<Loan> holder, int position) {
        LoanViewHolder viewHolder = (LoanViewHolder) holder;
        Loan loan = get(position);
        viewHolder.personFullNameEditText.setText(loan.person.getFullName());
        viewHolder.loanDebtEditText.setText(loan.getDebt());
        viewHolder.loanPaidEditText.setText(loan.getPaid());
        viewHolder.loanFeesEditText.setText(loan.fees.toString());
        viewHolder.loanFeesFulfilledEditText.setText(loan.feesFulfilled.toString());
        viewHolder.loanLastPaidEditText.setText(loan.getLastPaid());
        viewHolder.loanNextPaidEditText.setText(loan.getNextPaid());
        viewHolder.detailButton.setOnClickListener(view -> {
            if (listener != null) {
                listener.onItemClick(loan);
            }
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.item_loan;
    }

    @NonNull
    @Override
    public BaseViewHolder<Loan> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(getLayoutResId(), parent, false);
        return new LoanViewHolder(view);
    }
}
