package com.andoresu.gordiskyapp.core.loandetail;

import android.annotation.SuppressLint;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.EditText;

import com.andoresu.gordiskyapp.R;
import com.andoresu.gordiskyapp.core.loandetail.data.LoanErrors;
import com.andoresu.gordiskyapp.core.loandetail.dialog.PaymentDialogContract;
import com.andoresu.gordiskyapp.core.loandetail.dialog.PaymentDialogFragment;
import com.andoresu.gordiskyapp.core.loans.data.Loan;
import com.andoresu.gordiskyapp.core.loans.data.Payment;
import com.andoresu.gordiskyapp.list.ListDialogFragment;
import com.andoresu.gordiskyapp.list.SimpleRecyclerViewFragment;
import com.andoresu.gordiskyapp.utils.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;

public class LoanDetailFragment extends BaseFragment implements LoanDetailContract.View, PaymentDialogContract.InteractionListener {

    public static final String TAG = BASE_TAG + LoanDetailFragment.class.getSimpleName();

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

    private LoanDetailContract.ActionsListener actionsListener;

    private Loan loan;

    private PaymentAdapter paymentAdapter;

    private SimpleRecyclerViewFragment<Payment> paymentSimpleRecyclerViewFragment;

    public LoanDetailFragment(){}

    public static LoanDetailFragment newInstance(Loan loan) {
        Bundle args = new Bundle();
        args.putSerializable(Loan.NAME, loan);
        LoanDetailFragment fragment = new LoanDetailFragment();
        if(loan == null || loan.id == null){
            fragment.setCustomTitle("Nuevo Prestamo");
        }else{
            fragment.setCustomTitle("Detalle Prestamo");
        }
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if(bundle != null){
            loan = (Loan) bundle.getSerializable(Loan.NAME);
        }
        actionsListener = new LoanDetailPresenter(this, getContext());
    }

    @Override
    public void handleView() {
        paymentAdapter = new PaymentAdapter(getContext(), false);
        paymentSimpleRecyclerViewFragment =  SimpleRecyclerViewFragment.newInstance();
        paymentSimpleRecyclerViewFragment.setViewAdapter(paymentAdapter);
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.listRecyclerViewFragment, paymentSimpleRecyclerViewFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        showLoan(loan);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_loan_detail;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void showLoan(Loan loan) {
        if(loan == null){
            showMessage("Ha ocurrido un error");
            return;
        }
        if(loan.person != null){
            personFullNameEditText.setText(loan.person.getFullName());
            personIdentificationEditText.setText(loan.person.identification);
        }
        loanAmountEditText.setText(loan.getAmount());
        loanInterestEditText.setText(loan.getInterest());
        loanDebtEditText.setText(loan.getDebt());
        loanPaidEditText.setText(loan.getPaid());
        if(loan.fees != null) {
            loanFeesEditText.setText(loan.fees.toString());
        }
        if(loan.feesFulfilled != null) {
            loanFeesFulfilledEditText.setText(loan.feesFulfilled.toString());
        }
        if(loan.missedPayments != null){
            loanExpiredPaymentsEditText.setText(loan.missedPayments.toString());
        }
        loanFrequencyEditText.setText(loan.getFrequency());
        loanLastPaidEditText.setText(loan.getLastPaid());
        loanNextPaidEditText.setText(loan.getNextPaid());
        paymentSimpleRecyclerViewFragment.clear();
        paymentSimpleRecyclerViewFragment.set(loan.payments);

    }

    @Override
    public void showLoanErrors(LoanErrors loanErrors) {

    }

    @OnClick(R.id.feesButton)
    public void openPaymentsModal(){
        ListDialogFragment<Payment> listDialogFragment = ListDialogFragment.newInstance(() -> {});
        listDialogFragment.setRecyclerViewAdapter(paymentAdapter);
        listDialogFragment.setTitle("Cuotas");
        listDialogFragment.widthPercentage = 0.9;
        listDialogFragment.heightPercentage = 0.9;
        listDialogFragment.show(this);
    }
    
    @OnClick(R.id.paymentButton)
    public void openSetPaymentModel(){
        Log.i(TAG, "openSetPaymentModel: click");
        PaymentDialogFragment paymentDialogFragment = PaymentDialogFragment.newInstance(loan, this);
        paymentDialogFragment.show(this);
    }

    @Override
    public void refreshLoan(Loan loan) {
        this.loan = loan;
        showLoan(this.loan);
    }
}
