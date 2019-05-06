package com.andoresu.gordiskyapp.core.loandetail;

import android.content.Context;


import com.andoresu.gordiskyapp.client.ObserverResponse;
import com.andoresu.gordiskyapp.client.ObserverResponseWhitError;
import com.andoresu.gordiskyapp.client.ServiceGenerator;
import com.andoresu.gordiskyapp.core.loandetail.data.LoanErrorsResponse;
import com.andoresu.gordiskyapp.core.loans.data.Loan;
import com.andoresu.gordiskyapp.core.loans.LoansService;
import com.andoresu.gordiskyapp.security.SecureData;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class LoanDetailPresenter implements LoanDetailContract.ActionsListener{

    private final LoanDetailContract.View view;
    private final Context context;
    private final LoansService loansService;

    public LoanDetailPresenter(LoanDetailContract.View view, Context context) {
        this.view = view;
        this.context = context;
        this.loansService = ServiceGenerator.createService(LoansService.class, SecureData.getToken());
    }

    @Override
    public void createLoan(Loan loan) {
        HashMap<String, Loan> hashMap = new HashMap<>();
        hashMap.put("loan", loan);
        loansService.create(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ObserverResponseWhitError<Response<Loan>, LoanErrorsResponse>(view, LoanErrorsResponse.class){
                    @Override
                    public void onNext(Response<Loan> response) {
                        super.onNext(response);
                        if(response.isSuccessful()){
                            Loan loan = response.body();
                            view.showLoan(loan);
                            view.showMessage("Prestamo Creado exitosamente");
                        }else{
                            if(getError() != null) {
                                view.showLoanErrors(getError().errors);
                            }
                        }

                    }
                });
    }

    @Override
    public void updateLoan(Loan loan) {
        HashMap<String, Loan> hashMap = new HashMap<>();
        hashMap.put("loan", loan);
        loansService.update(loan.id, hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ObserverResponseWhitError<Response<Loan>, LoanErrorsResponse>(view, LoanErrorsResponse.class){
                    @Override
                    public void onNext(Response<Loan> response) {
                        super.onNext(response);
                        if(response.isSuccessful()){
                            Loan loan = response.body();
                            view.showLoan(loan);
                            view.showMessage("Prestamo Actualizado exitosamente");
                        }else{
                            if(getError() != null) {
                                view.showLoanErrors(getError().errors);
                            }
                        }
                    }
                });
    }

    @Override
    public void deleteLoan(Loan loan) {
        loansService.delete(loan.id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ObserverResponse<Response<Loan>>(view){
                    @Override
                    public void onNext(Response<Loan> response) {
                        super.onNext(response);
                        if(response.isSuccessful()){
                            //TODO: show successful message
                        }
                    }
                });
    }
}
