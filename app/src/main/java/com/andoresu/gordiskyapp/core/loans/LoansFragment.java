package com.andoresu.gordiskyapp.core.loans;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andoresu.gordiskyapp.R;
import com.andoresu.gordiskyapp.client.ErrorResponse;
import com.andoresu.gordiskyapp.core.loans.data.Loan;
import com.andoresu.gordiskyapp.core.loans.data.LoansResponse;
import com.andoresu.gordiskyapp.list.RecyclerViewFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.OnClick;

public class LoansFragment extends RecyclerViewFragment<Loan> implements LoansContract.View {

    private LoansContract.InteractionListener interactionListener;
    private LoansContract.ActionsListener actionsListener;
    private LoansResponse loansResponse;

    public String personId;

    public static LoansFragment newInstance(@NonNull LoansContract.InteractionListener interactionListener) {
        Bundle args = new Bundle();
        LoansFragment fragment = new LoansFragment();
        fragment.setArguments(args);
        fragment.setInteractionListener(interactionListener);
        fragment.setCustomTitle("Prestamos");
        fragment.addSearch = true;
        fragment.setHasOptionsMenu(true);
        return fragment;
    }

    public static LoansFragment newInstance(@NonNull LoansContract.InteractionListener interactionListener, String personId) {
        LoansFragment fragment = newInstance(interactionListener);
        fragment.personId = personId;
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if(bundle != null){}
        actionsListener = new LoansPresenter(this, getContext());
        init();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        viewAdapter = new LoanAdapter(getContext(), item -> interactionListener.goToLoanDetail(item));
        listRecyclerView.setAdapter(viewAdapter);
        onRefresh();
        return view;
    }

    public void setInteractionListener(LoansContract.InteractionListener interactionListener) {
        this.interactionListener = interactionListener;
    }

    private void init(){

    }

    private Map<String, String> getOptions(){
        Map<String, String> options = new HashMap<>();
        options.put("page", currentPage + "");
        if(searchQuery != null){
            options.put("search", searchQuery);
        }

        if(personId != null){
            options.put("by_person_id", personId);
        }
        return options;
    }


    @Override
    public void showLoans(LoansResponse loansResponse) {
        this.loansResponse = loansResponse;
        viewAdapter.addAll(loansResponse.loans);
        isEmpty();
    }

    @Override
    public void onRefresh(boolean clear) {
        super.onRefresh(clear);
        if(clear){
            viewAdapter.set(new ArrayList<>());
        }
        actionsListener.getLoans(getOptions());
    }

    @Override
    public int getTotalItems() {
        return loansResponse.totalCount;
    }

    @Override
    public void showProgressIndicator(boolean active) {
        showLoading(active);
    }

    @Override
    public void showGlobalError(ErrorResponse errorResponse) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_loans;
    }

    @Override
    public void onLogoutFinish() {

    }

    @OnClick(R.id.newLoanFloatingActionButton)
    public void newLoan(){
        interactionListener.goToLoanDetail(new Loan());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
    }
}
