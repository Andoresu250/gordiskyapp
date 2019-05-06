package com.andoresu.gordiskyapp.core.people;

import android.content.Context;

import com.andoresu.gordiskyapp.authorization.data.Person;
import com.andoresu.gordiskyapp.client.ObserverResponse;
import com.andoresu.gordiskyapp.client.ServiceGenerator;
import com.andoresu.gordiskyapp.core.people.data.PeopleResponse;
import com.andoresu.gordiskyapp.core.person.PersonContract;
import com.andoresu.gordiskyapp.security.SecureData;

import org.reactivestreams.Subscription;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class PeoplePresenter implements PeopleContract.ActionsListener {

    private final PeopleContract.View view;
    private final Context context;
    private final PeopleService peopleService;

    public PeoplePresenter(PeopleContract.View view, Context context) {
        this.view = view;
        this.context = context;
        this.peopleService = ServiceGenerator.createService(PeopleService.class, SecureData.getToken());
    }

    @Override
    public void getPeople(Map<String, String> options) {
        peopleService.index(options)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ObserverResponse<Response<PeopleResponse>>(view){
                    @Override
                    public void onNext(Response<PeopleResponse> response) {
                        super.onNext(response);
                        if(response.isSuccessful()){
                            PeopleResponse peopleResponse = response.body();
                            view.showPeople(peopleResponse);
                        }
                    }
                });
    }

}
