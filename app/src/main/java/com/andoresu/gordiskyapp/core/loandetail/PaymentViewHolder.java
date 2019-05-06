package com.andoresu.gordiskyapp.core.loandetail;

import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.andoresu.gordiskyapp.R;
import com.andoresu.gordiskyapp.core.loans.data.Payment;
import com.andoresu.gordiskyapp.utils.BaseRecyclerViewAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PaymentViewHolder extends BaseRecyclerViewAdapter.BaseViewHolder<Payment>{

    @BindView(R.id.paymentNumberEditText)
    public EditText paymentNumberEditText;
    @BindView(R.id.paymentStateEditText)
    public EditText paymentStateEditText;
    @BindView(R.id.paymentValueEditText)
    public EditText paymentValueEditText;
    @Nullable
    @BindView(R.id.paymentPaidValueEditText)
    public EditText paymentPaidValueEditText;
    @BindView(R.id.paymentDateEditText)
    public EditText paymentDateEditText;
    @Nullable
    @BindView(R.id.paymentPaidAtEditText)
    public EditText paymentPaidAtEditText;

    @Nullable
    @BindView(R.id.paymentNumberTextInputLayout)
    public TextInputLayout paymentNumberTextInputLayout;
    @Nullable
    @BindView(R.id.paymentStateTextInputLayout)
    public TextInputLayout paymentStateTextInputLayout;
    @Nullable
    @BindView(R.id.paymentValueTextInputLayout)
    public TextInputLayout paymentValueTextInputLayout;
    @Nullable
    @BindView(R.id.paymentPaidValueTextInputLayout)
    public TextInputLayout paymentPaidValueTextInputLayout;
    @Nullable
    @BindView(R.id.paymentDateTextInputLayout)
    public TextInputLayout paymentDateTextInputLayout;
    @Nullable
    @BindView(R.id.paymentPaidAtTextInputLayout)
    public TextInputLayout paymentPaidAtTextInputLayout;

    public PaymentViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}