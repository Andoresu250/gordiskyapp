package com.andoresu.gordiskyapp.core.transactions;

import com.andoresu.gordiskyapp.authorization.data.Person;
import com.andoresu.gordiskyapp.core.people.data.PeopleResponse;
import com.andoresu.gordiskyapp.core.resume.data.Resume;
import com.andoresu.gordiskyapp.core.transactions.data.Transaction;
import com.andoresu.gordiskyapp.core.transactions.data.TransactionsResponse;


import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface TransactionsService {

    @GET("monetary_transactions/resume/get")
    Observable<Response<Resume>> getResume(@QueryMap Map<String, String> options);

    @GET("monetary_transactions/")
    Observable<Response<TransactionsResponse>> index(@QueryMap Map<String, String> options);

    @GET("monetary_transactions/{id}")
    Observable<Response<Transaction>> get(@Path("id") String id);

    @POST("monetary_transactions/")
    Observable<Response<Transaction>> create(@Body HashMap<String, Transaction> transactionHashMap);

}
