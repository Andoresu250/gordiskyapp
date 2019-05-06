package com.andoresu.gordiskyapp.core.transaction;

import android.content.Context;

import com.andoresu.gordiskyapp.authorization.data.Person;
import com.andoresu.gordiskyapp.client.ObserverResponseWhitError;
import com.andoresu.gordiskyapp.client.ServiceGenerator;
import com.andoresu.gordiskyapp.core.person.data.PersonErrorsResponse;
import com.andoresu.gordiskyapp.core.transaction.data.TransactionErrorsResponse;
import com.andoresu.gordiskyapp.core.transactions.TransactionsService;
import com.andoresu.gordiskyapp.core.transactions.data.Transaction;
import com.andoresu.gordiskyapp.security.SecureData;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class TransactionPresenter implements TransactionContract.ActionsListener{

    private final TransactionContract.View view;
    private final Context context;
    private final TransactionsService transactionsService;

    public TransactionPresenter(TransactionContract.View view, Context context) {
        this.view = view;
        this.context = context;
        this.transactionsService = ServiceGenerator.createService(TransactionsService.class, SecureData.getToken());
    }


    @Override
    public void createTransaction(Transaction transaction) {
        HashMap<String, Transaction> hashMap = new HashMap<>();
        hashMap.put("monetary_transaction", transaction);
        transactionsService.create(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ObserverResponseWhitError<Response<Transaction>, TransactionErrorsResponse>(view, TransactionErrorsResponse.class){
                    @Override
                    public void onNext(Response<Transaction> response) {
                        super.onNext(response);
                        if(response.isSuccessful()){
                            Transaction transaction = response.body();
                            view.showMessage("Transaccion Creada exitosamente");
                            view.onTransactionCreated(transaction);
                        }else{
                            if(getError() != null) {
                                view.showTransactionErrors(getError().errors);
                            }
                        }

                    }
                });
    }
}
