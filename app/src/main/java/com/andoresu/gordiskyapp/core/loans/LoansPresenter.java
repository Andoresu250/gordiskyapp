package com.andoresu.gordiskyapp.core.loans;

import android.content.Context;

import com.andoresu.gordiskyapp.client.ObserverResponse;
import com.andoresu.gordiskyapp.client.ServiceGenerator;
import com.andoresu.gordiskyapp.core.loans.data.LoansResponse;
import com.andoresu.gordiskyapp.security.SecureData;

import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class LoansPresenter implements LoansContract.ActionsListener {

    private final LoansContract.View view;
    private final Context context;
    private final LoansService loansService;

    public LoansPresenter(LoansContract.View view, Context context) {
        this.view = view;
        this.context = context;
        this.loansService = ServiceGenerator.createService(LoansService.class, SecureData.getToken());
    }

    @Override
    public void getLoans(Map<String, String> options) {
        loansService.index(options)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ObserverResponse<Response<LoansResponse>>(view){
                    @Override
                    public void onNext(Response<LoansResponse> response) {
                        super.onNext(response);
                        if(response.isSuccessful()){
                            LoansResponse loansResponse = response.body();
                            view.showLoans(loansResponse);
                        }
                    }
                });
    }

}