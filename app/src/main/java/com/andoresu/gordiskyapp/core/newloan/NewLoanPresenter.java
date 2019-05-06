package com.andoresu.gordiskyapp.core.newloan;

import android.content.Context;

import com.andoresu.gordiskyapp.authorization.data.Person;
import com.andoresu.gordiskyapp.client.ObserverResponse;
import com.andoresu.gordiskyapp.client.ObserverResponseWhitError;
import com.andoresu.gordiskyapp.client.ServiceGenerator;
import com.andoresu.gordiskyapp.core.loandetail.data.LoanErrorsResponse;
import com.andoresu.gordiskyapp.core.loans.LoansService;
import com.andoresu.gordiskyapp.core.loans.data.Loan;
import com.andoresu.gordiskyapp.core.people.PeopleService;
import com.andoresu.gordiskyapp.security.SecureData;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class NewLoanPresenter implements NewLoanContract.ActionsListener{

    private final NewLoanContract.View view;
    private final Context context;
    private final LoansService loansService;
    private final PeopleService peopleService;

    public NewLoanPresenter(NewLoanContract.View view, Context context) {
        this.view = view;
        this.context = context;
        this.loansService = ServiceGenerator.createService(LoansService.class, SecureData.getToken());
        this.peopleService = ServiceGenerator.createService(PeopleService.class, SecureData.getToken());
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
//                            view.showLoan(loan);
                            view.showMessage("Prestamo Creada exitosamente");
                            view.goToLoanDetails(loan);
                        }else{
                            if(getError() != null) {
                                view.showLoanErrors(getError().errors);
                            }
                        }

                    }
                });
    }

    @Override
    public void projectLoan(Loan loan) {
        HashMap<String, Loan> hashMap = new HashMap<>();
        hashMap.put("loan", loan);
        loansService.project(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ObserverResponseWhitError<Response<Loan>, LoanErrorsResponse>(view, LoanErrorsResponse.class){
                    @Override
                    public void onNext(Response<Loan> response) {
                        super.onNext(response);
                        if(response.isSuccessful()){
                            Loan loan = response.body();
                            view.showLoan(loan);
                        }else{
                            if(getError() != null) {
                                view.showLoanErrors(getError().errors);
                            }
                        }

                    }
                });
    }

    @Override
    public void getPerson(String identification) {
        peopleService.getByIdentification(identification, identification)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ObserverResponse<Response<Person>>(view){
                    @Override
                    public void onNext(Response<Person> personResponse) {
                        super.onNext(personResponse);
                        if(personResponse.isSuccessful()){
                            view.showPerson(personResponse.body());
                        }else{
                            view.showMessage("Persona no encontrada");
                            view.showPerson(null);
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        view.showMessage("Persona no encontrada");
                        view.showPerson(null);
                    }
                });
    }
}
