package com.andoresu.gordiskyapp.core.transaction;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.andoresu.gordiskyapp.R;
import com.andoresu.gordiskyapp.core.transaction.data.TransactionErrors;
import com.andoresu.gordiskyapp.core.transactions.data.Transaction;
import com.andoresu.gordiskyapp.utils.BaseFragment;
import com.andoresu.gordiskyapp.utils.CurrencyEditText;

import butterknife.BindView;
import butterknife.OnClick;

import static com.andoresu.gordiskyapp.utils.MyUtils.getFirst;
import static com.andoresu.gordiskyapp.utils.MyUtils.pairTextViewWithView;
import static com.andoresu.gordiskyapp.utils.MyUtils.showErrorDialog;

public class TransactionFragment extends BaseFragment implements TransactionContract.View {

    @BindView(R.id.transactionValueLabelTextView)
    public TextView transactionValueLabelTextView;
    @BindView(R.id.transactionValueEditText)
    public CurrencyEditText transactionValueEditText;
    @BindView(R.id.transactionDescriptionTextInputLayout)
    public TextInputLayout transactionDescriptionTextInputLayout;
    @BindView(R.id.transactionDescriptionEditText)
    public EditText transactionDescriptionEditText;
    @BindView(R.id.saveButton)
    public Button saveButton;

    public TransactionContract.InteractionListener interactionListener;

    private TransactionContract.ActionsListener actionsListener;

    private Transaction transaction;

    public static TransactionFragment newInstance(@NonNull TransactionContract.InteractionListener interactionListener) {

        Bundle args = new Bundle();

        TransactionFragment fragment = new TransactionFragment();
        fragment.setArguments(args);
        fragment.interactionListener = interactionListener;
        fragment.setCustomTitle("Nueva Transaccion");
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if(bundle != null){}
        actionsListener = new TransactionPresenter(this, getContext());
    }

    @Override
    public void handleView() {
        pairTextViewWithView(transactionValueLabelTextView, transactionValueEditText, getContext());
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_transaction;
    }

    @Override
    public void showTransactionErrors(TransactionErrors transactionErrors) {
        transactionValueEditText.setError(getFirst(transactionErrors.value));
        transactionDescriptionTextInputLayout.setError(getFirst(transactionErrors.description));
    }

    @Override
    public void onTransactionCreated(Transaction transaction) {
        interactionListener.goToTransactions();
    }

    public void setTransaction(){
        if(transaction == null){
            transaction = new Transaction();
        }
        transaction.mode = Transaction.PASIVO;
        transaction.description = transactionDescriptionEditText.getText().toString();
        transaction.value = transactionValueEditText.getDoubleValue();
    }

    @OnClick(R.id.saveButton)
    public void createTransaction(){
        setTransaction();
        String msg = "Esta seguro que desea crear este " + transaction.mode + " por " + transaction.getValue() + "?, verifique los datos antes de procedes";
        showErrorDialog(getContext(), "Alerta", msg,
                (dialogInterface, i) -> {
                    actionsListener.createTransaction(transaction);
                });
    }
}
