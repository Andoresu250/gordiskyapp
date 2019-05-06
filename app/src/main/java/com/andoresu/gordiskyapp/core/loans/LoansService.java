package com.andoresu.gordiskyapp.core.loans;

import com.andoresu.gordiskyapp.core.loans.data.Loan;
import com.andoresu.gordiskyapp.core.loans.data.LoansResponse;

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
import retrofit2.http.QueryMap;

public interface LoansService {
    @GET("loans/")
    Observable<Response<LoansResponse>> index(@QueryMap Map<String, String> options);

    @GET("loans/{id}")
    Observable<Response<Loan>> get(@Path("id") String id);

    @POST("loans/")
    Observable<Response<Loan>> create(@Body HashMap<String, Loan> personHashMap);

    @POST("loans/project/get")
    Observable<Response<Loan>> project(@Body HashMap<String, Loan> personHashMap);

    @PUT("loans/{id}")
    Observable<Response<Loan>> update(@Path("id") String id, @Body HashMap<String, Loan> personHashMap);

    @PUT("loans/{id}/pay")
    Observable<Response<Loan>> pay(@Path("id") String id, @Body HashMap<String, HashMap<String, Double>> bodyHashMap);

    @DELETE("loans/{id}")
    Observable<Response<Loan>> delete(@Path("id") String id);
}
