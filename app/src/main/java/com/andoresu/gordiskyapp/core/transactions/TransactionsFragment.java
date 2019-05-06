package com.andoresu.gordiskyapp.core.transactions;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuInflater;

import com.andoresu.gordiskyapp.R;
import com.andoresu.gordiskyapp.core.transactions.data.Transaction;
import com.andoresu.gordiskyapp.core.transactions.data.TransactionsResponse;
import com.andoresu.gordiskyapp.list.RecyclerViewFragment;
import com.andoresu.gordiskyapp.utils.BaseDataModel;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class TransactionsFragment extends RecyclerViewFragment<Transaction> implements TransactionsContract.View {

    private static final String ALL_TRANSACTIONS = "Todas las transacciones";
    private static final String ACTIVO_TRANSACTIONS = "Todos los activos";
    private static final String PASIVO_TRANSACTIONS = "Todos los pasivos";

    public TransactionsContract.InteractionListener interactionListener;
    private TransactionsContract.ActionsListener actionsListener;
    private TransactionsResponse transactionsResponse;

    @BindView(R.id.transactionModeSpinner)
    public MaterialSpinner transactionModeSpinner;

    private String selectedItem = ALL_TRANSACTIONS;

    public static TransactionsFragment newInstance(TransactionsContract.InteractionListener interactionListener) {

        Bundle args = new Bundle();

        TransactionsFragment fragment = new TransactionsFragment();
        fragment.setArguments(args);
        fragment.setCustomTitle("Transacciones");
        fragment.addSearch = true;
        fragment.setHasOptionsMenu(true);
        fragment.interactionListener = interactionListener;
        fragment.showCalendar = true;
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if(bundle != null){}
        actionsListener = new TransactionsPresenter(this, getContext());
    }

    @Override
    public void handleView() {
        viewAdapter = new TransactionAdapter(getContext());
        actionsListener.getTransactions(getOptions());
        transactionModeSpinner.setItems(ALL_TRANSACTIONS, ACTIVO_TRANSACTIONS, PASIVO_TRANSACTIONS);
        transactionModeSpinner.setOnItemSelectedListener((MaterialSpinner.OnItemSelectedListener<String>) (view, position, id, item) -> {
            currentPage = 1;
            selectedItem = item;
            viewAdapter.set(new ArrayList<>());
            actionsListener.getTransactions(getOptions());
        });
        super.handleView();
    }

    @SuppressLint("SimpleDateFormat")
    private Map<String, String> getOptions(){
        Map<String, String> options = new HashMap<>();
        options.put("page", currentPage + "");
        if(searchQuery != null){
            options.put("search", searchQuery);
        }
        switch (selectedItem){
            case ACTIVO_TRANSACTIONS:
                options.put("by_mode", Transaction.ACTIVO);
                break;
            case PASIVO_TRANSACTIONS:
                options.put("by_mode", Transaction.PASIVO);
                break;
        }
        if(date != null){
            options.put("by_created_date", new SimpleDateFormat(BaseDataModel.DATE_FORMAT_SIMPLE_QUERY).format(date));
        }else{
            if(startDate != null){
                options.put("by_created_start_date", new SimpleDateFormat(BaseDataModel.DATE_FORMAT_SIMPLE_QUERY).format(startDate));
            }
            if(endDate != null){
                options.put("by_created_end_date", new SimpleDateFormat(BaseDataModel.DATE_FORMAT_SIMPLE_QUERY).format(endDate));
            }
        }

        return options;
    }

    @Override
    public void showTransactions(TransactionsResponse transactionsResponse) {
        this.transactionsResponse = transactionsResponse;
        viewAdapter.addAll(transactionsResponse.monetaryTransactions);
        isEmpty();
    }

    @Override
    public void onRefresh(boolean clear) {
        super.onRefresh(clear);
        actionsListener.getTransactions(getOptions());
    }

    @Override
    public int getTotalItems() {
        return transactionsResponse.totalCount;
    }

    @Override
    public void showProgressIndicator(boolean active) {
        showLoading(active);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_transactions;
    }

    @OnClick(R.id.newTransactionFloatingActionButton)
    public void newTransaction(){
        interactionListener.goToNewTransaction();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
    }
}
