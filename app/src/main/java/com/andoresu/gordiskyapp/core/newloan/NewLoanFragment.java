package com.andoresu.gordiskyapp.core.newloan;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.andoresu.gordiskyapp.R;
import com.andoresu.gordiskyapp.authorization.data.Person;
import com.andoresu.gordiskyapp.core.loandetail.PaymentAdapter;
import com.andoresu.gordiskyapp.core.loandetail.data.LoanErrors;
import com.andoresu.gordiskyapp.core.loans.data.Loan;
import com.andoresu.gordiskyapp.core.loans.data.Payment;
import com.andoresu.gordiskyapp.core.newloan.data.Frequency;
import com.andoresu.gordiskyapp.list.ListDialogFragment;
import com.andoresu.gordiskyapp.list.SimpleRecyclerViewFragment;
import com.andoresu.gordiskyapp.utils.BaseFragment;
import com.andoresu.gordiskyapp.utils.CurrencyEditText;
import com.andoresu.gordiskyapp.utils.MyUtils;
import com.jaredrummler.materialspinner.MaterialSpinner;


import butterknife.BindView;
import butterknife.OnClick;

import static com.andoresu.gordiskyapp.utils.MyUtils.getFirst;
import static com.andoresu.gordiskyapp.utils.MyUtils.pairTextViewWithView;
import static com.andoresu.gordiskyapp.utils.MyUtils.showErrorDialog;

public class NewLoanFragment extends BaseFragment implements NewLoanContract.View, ListDialogFragment.ListDialogListener {

    public static final String TAG = BASE_TAG + NewLoanFragment.class.getSimpleName();

    @BindView(R.id.identificationTextInputLayout)
    public TextInputLayout identificationTextInputLayout;
    @BindView(R.id.identificationEditText)
    public EditText identificationEditText;
    @BindView(R.id.personFullNameTextInputLayout)
    public TextInputLayout personFullNameTextInputLayout;
    @BindView(R.id.personFullNameEditText)
    public EditText personFullNameEditText;
    @BindView(R.id.loanAmountEditText)
    public CurrencyEditText loanAmountEditText;
    @BindView(R.id.loanAmountLabelTextView)
    public TextView loanAmountLabelTextView;
    @BindView(R.id.loanInterestTextInputLayout)
    public TextInputLayout loanInterestTextInputLayout;
    @BindView(R.id.loanInterestEditText)
    public EditText loanInterestEditText;
    @BindView(R.id.frequencyLabelTextView)
    public TextView frequencyLabelTextView;
    @BindView(R.id.loanFrequencySpinner)
    public MaterialSpinner loanFrequencySpinner;
    @BindView(R.id.loanFrequencyDaysTextInputLayout)
    public TextInputLayout loanFrequencyDaysTextInputLayout;
    @BindView(R.id.loanFrequencyDaysEditText)
    public EditText loanFrequencyDaysEditText;
    @BindView(R.id.loanFeesTextInputLayout)
    public TextInputLayout loanFeesTextInputLayout;
    @BindView(R.id.loanFeesEditText)
    public EditText loanFeesEditText;
    @BindView(R.id.loanDebtTextInputLayout)
    public TextInputLayout loanDebtTextInputLayout;
    @BindView(R.id.loanDebtEditText)
    public EditText loanDebtEditText;

    @BindView(R.id.feesLayout)
    public View feesLayout;
    
    @BindView(R.id.feesButton)
    public Button feesButton;

    private SimpleRecyclerViewFragment<Payment> paymentSimpleRecyclerViewFragment;

    private NewLoanContract.ActionsListener actionsListener;

    private Loan loan;

    private FrequencyAdapter frequencyAdapter;

    private Person person;

    private PaymentAdapter paymentAdapter;

    public NewLoanContract.InteractionListener interactionListener;

    public NewLoanFragment(){}

    public static NewLoanFragment newInstance(@NonNull NewLoanContract.InteractionListener interactionListener) {

        Bundle args = new Bundle();

        NewLoanFragment fragment = new NewLoanFragment();
        fragment.setArguments(args);
        fragment.setCustomTitle("Nuevo Prestamo");
        fragment.interactionListener = interactionListener;
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if(bundle != null){
            loan = (Loan) bundle.getSerializable(Loan.NAME);
        }
        actionsListener = new NewLoanPresenter(this, getContext());
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void handleView() {
        feesLayout.setVisibility(View.GONE);
        pairTextViewWithView(loanAmountLabelTextView, loanAmountEditText, getContext());
        pairTextViewWithView(frequencyLabelTextView, loanFrequencySpinner, getContext());
        frequencyAdapter = new FrequencyAdapter(getContext(), Frequency.FREQUENCIES_LIST);
        loanFrequencySpinner.setAdapter(frequencyAdapter);
        loanFrequencySpinner.setOnClickListener(MyUtils::closeKeyboard);
        loanFrequencySpinner.setOnItemSelectedListener((MaterialSpinner.OnItemSelectedListener<Frequency>) (view, position, id, item) -> {
            if(item.value == null){
                loanFrequencyDaysEditText.setEnabled(true);
                loanFrequencyDaysEditText.setText(null);
                loanFrequencyDaysEditText.requestFocus();
            }else{
                loanFrequencyDaysEditText.setEnabled(false);
                if(item.value == -1){
                    loanFrequencyDaysEditText.setText(null);
                }else{
                    loanFrequencyDaysEditText.setText(item.value.toString());
                }
            }
        });
        paymentSimpleRecyclerViewFragment =  SimpleRecyclerViewFragment.newInstance();
        paymentAdapter = new PaymentAdapter(getContext(), true);
        paymentSimpleRecyclerViewFragment.setViewAdapter(paymentAdapter);
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.listRecyclerViewFragment, paymentSimpleRecyclerViewFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @OnClick(R.id.searchPersonImageButton)
    public void searchPerson(){
        actionsListener.getPerson(identificationEditText.getText().toString());
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_new_loan;
    }


    @Override
    public void showLoanErrors(LoanErrors loanErrors) {
        if(loanErrors == null){
            showMessage("Ha ocurrido un error");
            return;
        }
        identificationTextInputLayout.setError(getFirst(loanErrors.person));
        personFullNameTextInputLayout.setError(getFirst(loanErrors.person));
        loanInterestTextInputLayout.setError(getFirst(loanErrors.interest));
        loanFrequencyDaysTextInputLayout.setError(getFirst(loanErrors.frequency));
        loanFeesTextInputLayout.setError(getFirst(loanErrors.fees));
        loanAmountEditText.setError(getFirst(loanErrors.amount));
    }

    @Override
    public void showLoan(Loan loan) {
        loanDebtEditText.setText(loan.getDebt());
        feesLayout.setVisibility(View.VISIBLE);
        paymentSimpleRecyclerViewFragment.set(loan.payments);
    }

    @Override
    public void showPerson(Person person) {
        this.person = person;
        if(person == null){
            personFullNameEditText.setText(null);
        }else{
            personFullNameEditText.setText(person.getFullName());
        }
    }

    @Override
    public void goToLoanDetails(Loan loan) {
        interactionListener.goToLoanDetail(loan);
    }

    @OnClick(R.id.feesButton)
    public void openPaymentsModal(){
        ListDialogFragment<Payment> listDialogFragment = ListDialogFragment.newInstance(this);
        listDialogFragment.setRecyclerViewAdapter(paymentAdapter);
        listDialogFragment.setTitle("Cuotas");
        listDialogFragment.widthPercentage = 0.9;
        listDialogFragment.heightPercentage = 0.9;
        listDialogFragment.show(this);
    }

    @OnClick(R.id.saveLoanButton)
    public void saveLoan(){
        showErrorDialog(getContext(), "Alerta", "Esta seguro de que desea crear este prestamo?, verifique los datos antes de proceder",
                (dialogInterface, i) -> {
                    setLoan();
                    actionsListener.createLoan(loan);
                });
    }

    private void setLoan(){
        loan = new Loan();
        loan.setPerson(person);
        loan.amount = loanAmountEditText.getDoubleValue();
        try{
            loan.interest = Double.parseDouble(loanInterestEditText.getText().toString());
        }catch (Exception e){
            e.printStackTrace();
        }
        try{
            loan.fees = Integer.parseInt(loanFeesEditText.getText().toString());
        }catch (Exception e){
            e.printStackTrace();
        }
        try{
            loan.frequency = Integer.parseInt(loanFrequencyDaysEditText.getText().toString());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @OnClick(R.id.projectLoanButton)
    public void projectLoan(){
        setLoan();
        actionsListener.projectLoan(loan);
    }

    @Override
    public void onClickPositiveButton() {

    }

}
