package com.andoresu.gordiskyapp.core.loans;

import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.andoresu.gordiskyapp.R;
import com.andoresu.gordiskyapp.core.loans.data.Loan;
import com.andoresu.gordiskyapp.utils.BaseRecyclerViewAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

import static com.andoresu.gordiskyapp.utils.MyUtils.DISABLE_VIEW;
import static com.andoresu.gordiskyapp.utils.MyUtils.HIDE_VIEW;
import static com.andoresu.gordiskyapp.utils.MyUtils.NON_FOCUSABLE_VIEW;

public class LoanViewHolder extends BaseRecyclerViewAdapter.BaseViewHolder<Loan>{

    @BindView(R.id.personFullNameEditText)
    public EditText personFullNameEditText;
    @BindView(R.id.personIdentificationEditText)
    public EditText personIdentificationEditText;
    @BindView(R.id.loanAmountEditText)
    public EditText loanAmountEditText;
    @BindView(R.id.loanInterestEditText)
    public EditText loanInterestEditText;
    @BindView(R.id.loanDebtEditText)
    public EditText loanDebtEditText;
    @BindView(R.id.loanPaidEditText)
    public EditText loanPaidEditText;
    @BindView(R.id.loanFeesEditText)
    public EditText loanFeesEditText;
    @BindView(R.id.loanFeesFulfilledEditText)
    public EditText loanFeesFulfilledEditText;
    @BindView(R.id.loanExpiredPaymentsEditText)
    public EditText loanExpiredPaymentsEditText;
    @BindView(R.id.loanFrequencyEditText)
    public EditText loanFrequencyEditText;
    @BindView(R.id.loanLastPaidEditText)
    public EditText loanLastPaidEditText;
    @BindView(R.id.loanNextPaidEditText)
    public EditText loanNextPaidEditText;

    @BindView(R.id.personFullNameTextInputLayout)
    public TextInputLayout personFullNameTextInputLayout;
    @BindView(R.id.personIdentificationTextInputLayout)
    public TextInputLayout personIdentificationTextInputLayout;
    @BindView(R.id.loanAmountTextInputLayout)
    public TextInputLayout loanAmountTextInputLayout;
    @BindView(R.id.loanInterestTextInputLayout)
    public TextInputLayout loanInterestTextInputLayout;
    @BindView(R.id.loanDebtTextInputLayout)
    public TextInputLayout loanDebtTextInputLayout;
    @BindView(R.id.loanPaidTextInputLayout)
    public TextInputLayout loanPaidTextInputLayout;
    @BindView(R.id.loanFeesTextInputLayout)
    public TextInputLayout loanFeesTextInputLayout;
    @BindView(R.id.loanFeesFulfilledTextInputLayout)
    public TextInputLayout loanFeesFulfilledTextInputLayout;
    @BindView(R.id.loanExpiredPaymentsTextInputLayout)
    public TextInputLayout loanExpiredPaymentsTextInputLayout;
    @BindView(R.id.loanFrequencyTextInputLayout)
    public TextInputLayout loanFrequencyTextInputLayout;
    @BindView(R.id.loanLastPaidTextInputLayout)
    public TextInputLayout loanLastPaidTextInputLayout;
    @BindView(R.id.loanNextPaidTextInputLayout)
    public TextInputLayout loanNextPaidTextInputLayout;

    @BindView(R.id.detailButton)
    public Button detailButton;

    @BindViews({
            R.id.personFullNameEditText, R.id.personIdentificationEditText, R.id.loanAmountEditText, R.id.loanInterestEditText,
            R.id.loanDebtEditText, R.id.loanPaidEditText, R.id.loanFeesEditText, R.id.loanFeesFulfilledEditText,
            R.id.loanExpiredPaymentsEditText, R.id.loanFrequencyEditText, R.id.loanLastPaidEditText, R.id.loanNextPaidEditText,
            R.id.personFullNameTextInputLayout, R.id.personIdentificationTextInputLayout, R.id.loanAmountTextInputLayout, R.id.loanInterestTextInputLayout,
            R.id.loanDebtTextInputLayout, R.id.loanPaidTextInputLayout, R.id.loanFeesTextInputLayout, R.id.loanFeesFulfilledTextInputLayout,
            R.id.loanExpiredPaymentsTextInputLayout, R.id.loanFrequencyTextInputLayout, R.id.loanLastPaidTextInputLayout, R.id.loanNextPaidTextInputLayout
    })
    public List<View> inputs;

    @BindViews({
            R.id.personIdentificationTextInputLayout, R.id.loanAmountTextInputLayout, R.id.loanInterestTextInputLayout,
            R.id.loanExpiredPaymentsTextInputLayout, R.id.loanFrequencyTextInputLayout, R.id.feesTextView, R.id.feesButton,
            R.id.listRecyclerViewFragment, R.id.paymentButton
    })
    public List<View> toHide;

    public LoanViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        ButterKnife.apply(inputs, NON_FOCUSABLE_VIEW);
        ButterKnife.apply(inputs, DISABLE_VIEW);
        ButterKnife.apply(toHide, HIDE_VIEW);
    }
}
