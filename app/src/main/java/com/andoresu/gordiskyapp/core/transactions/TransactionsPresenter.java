package com.andoresu.gordiskyapp.core.transactions;

import android.content.Context;

import com.andoresu.gordiskyapp.authorization.data.Person;
import com.andoresu.gordiskyapp.client.ObserverResponse;
import com.andoresu.gordiskyapp.client.ObserverResponseWhitError;
import com.andoresu.gordiskyapp.client.ServiceGenerator;
import com.andoresu.gordiskyapp.core.people.data.PeopleResponse;
import com.andoresu.gordiskyapp.core.person.data.PersonErrorsResponse;
import com.andoresu.gordiskyapp.core.resume.data.Resume;
import com.andoresu.gordiskyapp.core.transactions.data.Transaction;
import com.andoresu.gordiskyapp.core.transactions.data.TransactionsResponse;
import com.andoresu.gordiskyapp.security.SecureData;

import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class TransactionsPresenter implements TransactionsContract.ActionsListener{

    private final TransactionsContract.View view;
    private final Context context;
    private final TransactionsService transactionsService;

    public TransactionsPresenter(TransactionsContract.View view, Context context) {
        this.view = view;
        this.context = context;
        this.transactionsService = ServiceGenerator.createService(TransactionsService.class, SecureData.getToken());
    }


    @Override
    public void getTransactions(Map<String, String> options) {
        transactionsService.index(options)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ObserverResponse<Response<TransactionsResponse>>(view){
                    @Override
                    public void onNext(Response<TransactionsResponse> response) {
                        super.onNext(response);
                        if(response.isSuccessful()){
                            TransactionsResponse transactionsResponse = response.body();
                            view.showTransactions(transactionsResponse);
                        }
                    }
                });
    }
}
