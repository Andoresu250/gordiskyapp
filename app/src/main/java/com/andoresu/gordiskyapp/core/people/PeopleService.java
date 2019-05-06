package com.andoresu.gordiskyapp.core.people;

import com.andoresu.gordiskyapp.authorization.data.Person;
import com.andoresu.gordiskyapp.core.people.data.PeopleResponse;

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

public interface PeopleService {

    @GET("people/")
    Observable<Response<PeopleResponse>> index(@QueryMap Map<String, String> options);

    @GET("people/{id}")
    Observable<Response<Person>> get(@Path("id") String id);

    @GET("people/{id}")
    Observable<Response<Person>> getByIdentification(@Path("id") String id, @Query("identification") String identification);

    @POST("people/")
    Observable<Response<Person>> create(@Body HashMap<String, Person> personHashMap);

    @PUT("people/{id}")
    Observable<Response<Person>> update(@Path("id") String id, @Body HashMap<String, Person> personHashMap);

    @DELETE("people/{id}")
    Observable<Response<Person>> delete(@Path("id") String id);

}
