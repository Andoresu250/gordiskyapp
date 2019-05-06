package com.andoresu.gordiskyapp.core.people;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andoresu.gordiskyapp.R;
import com.andoresu.gordiskyapp.authorization.data.Person;
import com.andoresu.gordiskyapp.client.ErrorResponse;
import com.andoresu.gordiskyapp.core.people.data.PeopleResponse;
import com.andoresu.gordiskyapp.list.RecyclerViewFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.OnClick;

public class PeopleFragment extends RecyclerViewFragment<Person> implements PeopleContract.View {

    private PeopleContract.InteractionListener interactionListener;
    private PeopleContract.ActionsListener actionsListener;
    private PeopleResponse peopleResponse;

    public static PeopleFragment newInstance(@NonNull PeopleContract.InteractionListener interactionListener) {
        Bundle args = new Bundle();
        PeopleFragment fragment = new PeopleFragment();
        fragment.setArguments(args);
        fragment.setInteractionListener(interactionListener);
        fragment.setCustomTitle("Clientes");
        fragment.addSearch = true;
        fragment.setHasOptionsMenu(true);
        return fragment;
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if(bundle != null){}
        actionsListener = new PeoplePresenter(this, getContext());
        init();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        viewAdapter = new PersonAdapter(getContext(), item -> interactionListener.goToPersonDetail(item));
        listRecyclerView.setAdapter(viewAdapter);
        onRefresh();
        return view;
    }

    public void setInteractionListener(PeopleContract.InteractionListener interactionListener) {
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
        return options;
    }


    @Override
    public void showPeople(PeopleResponse peopleResponse) {
        this.peopleResponse = peopleResponse;
        viewAdapter.addAll(peopleResponse.people);
        isEmpty();
    }

    @Override
    public void onRefresh(boolean clear) {
        super.onRefresh(clear);
        if(clear){
            viewAdapter.set(new ArrayList<>());
        }
        actionsListener.getPeople(getOptions());
    }

    @Override
    public int getTotalItems() {
        return peopleResponse.totalCount;
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
        return R.layout.fragment_people;
    }

    @Override
    public void onLogoutFinish() {

    }

    @OnClick(R.id.newPersonFloatingActionButton)
    public void newPerson(){
        interactionListener.goToPersonDetail(new Person());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
    }
}
