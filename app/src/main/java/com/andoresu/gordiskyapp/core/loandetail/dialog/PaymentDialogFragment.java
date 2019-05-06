package com.andoresu.gordiskyapp.core.loandetail.dialog;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.andoresu.gordiskyapp.R;
import com.andoresu.gordiskyapp.client.ErrorResponse;
import com.andoresu.gordiskyapp.core.loans.data.Loan;
import com.andoresu.gordiskyapp.core.loans.data.Payment;
import com.andoresu.gordiskyapp.utils.BaseDialogFragment;
import com.andoresu.gordiskyapp.utils.CurrencyEditText;

import butterknife.BindView;

import static com.andoresu.gordiskyapp.utils.MyUtils.getFirst;
import static com.andoresu.gordiskyapp.utils.MyUtils.pairTextViewWithView;

public class PaymentDialogFragment extends BaseDialogFragment implements PaymentDialogContract.View{

    @BindView(R.id.paymentValueTextInputLayout)
    public TextInputLayout paymentValueTextInputLayout;
    @BindView(R.id.paymentValueEditText)
    public EditText paymentValueEditText;
    @BindView(R.id.paymentAmountLabelTextView)
    public TextView paymentAmountLabelTextView;
    @BindView(R.id.paymentAmountEditText)
    public CurrencyEditText paymentAmountEditText;
    @BindView(R.id.progressBar)
    public ProgressBar progressBar;
    @BindView(R.id.fragmentLayout)
    public View fragmentLayout;

    public Loan loan;

    private PaymentDialogContract.ActionsListener actionsListener;
    public PaymentDialogContract.InteractionListener interactionListener;

    public static PaymentDialogFragment newInstance(@NonNull Loan loan, @NonNull PaymentDialogContract.InteractionListener interactionListener) {
        Bundle args = new Bundle();
        PaymentDialogFragment fragment = new PaymentDialogFragment();
        fragment.setArguments(args);
        fragment.interactionListener = interactionListener;
        fragment.loan = loan;
        fragment.setTitle("Ingresar Pago");
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.df_payment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        actionsListener = new PaymentDialogPresenter(this, getContext());
    }

    @Override
    public void handleView() {
        pairTextViewWithView(paymentAmountLabelTextView, paymentAmountEditText, getContext());
        Payment payment = getFirst(loan.payments);
        if(payment != null){
            paymentValueEditText.setText(payment.getValue());
        }
        builder.setNegativeButton(R.string.cancel, (dialogInterface, i) -> dialogInterface.dismiss());
        builder.setPositiveButton(R.string.pay, null);
    }

    @Override
    public void setLoan(Loan loan) {
        this.loan = loan;
        interactionListener.refreshLoan(loan);
        dismiss();
    }

    @Override
    public void showProgressIndicator(boolean active) {
        progressBar.setVisibility(active ? View.VISIBLE : View.GONE);
        fragmentLayout.setVisibility(active ? View.GONE : View.VISIBLE);
    }

    @Override
    public void showGlobalError(ErrorResponse errorResponse) {}

    @Override
    public void onLogoutFinish() {}

    @Override
    public void showMessage(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        positiveBtn = ((AlertDialog) getDialog()).getButton(AlertDialog.BUTTON_POSITIVE);
        if(positiveBtn != null){
            positiveBtn.setOnClickListener(view -> actionsListener.pay(loan, paymentAmountEditText.getDoubleValue()));
        }
    }
}
