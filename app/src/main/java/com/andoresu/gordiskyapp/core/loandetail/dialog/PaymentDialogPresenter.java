package com.andoresu.gordiskyapp.core.loandetail.dialog;

import android.content.Context;

import com.andoresu.gordiskyapp.authorization.data.Person;
import com.andoresu.gordiskyapp.client.ObserverResponse;
import com.andoresu.gordiskyapp.client.ObserverResponseWhitError;
import com.andoresu.gordiskyapp.client.ServiceGenerator;
import com.andoresu.gordiskyapp.core.loans.LoansService;
import com.andoresu.gordiskyapp.core.loans.data.Loan;
import com.andoresu.gordiskyapp.core.person.data.PersonErrorsResponse;
import com.andoresu.gordiskyapp.security.SecureData;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class PaymentDialogPresenter implements PaymentDialogContract.ActionsListener{

    private final PaymentDialogContract.View view;
    private final Context context;
    private final LoansService loansService;

    public PaymentDialogPresenter(PaymentDialogContract.View view, Context context) {
        this.view = view;
        this.context = context;
        this.loansService = ServiceGenerator.createService(LoansService.class, SecureData.getToken());
    }

    @Override
    public void pay(Loan loan, Double value) {
        HashMap<String, HashMap<String, Double>> bodyHashMap = new HashMap<>();
        HashMap<String, Double> loanHashMap = new HashMap<>();
        loanHashMap.put("value", value);
        bodyHashMap.put("loan", loanHashMap);
        loansService.pay(loan.id, bodyHashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ObserverResponse<Response<Loan>>(view){
                    @Override
                    public void onNext(Response<Loan> response) {
                        super.onNext(response);
                        if(response.isSuccessful()){
                            Loan loan = response.body();
                            view.setLoan(loan);
                            view.showMessage("pago efectuado exitosamente");
                        }
                    }
                });
    }
}
