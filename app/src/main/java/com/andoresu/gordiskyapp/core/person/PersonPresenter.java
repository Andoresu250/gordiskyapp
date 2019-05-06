package com.andoresu.gordiskyapp.core.person;

import android.content.Context;

import com.andoresu.gordiskyapp.authorization.data.Person;
import com.andoresu.gordiskyapp.client.ObserverResponse;
import com.andoresu.gordiskyapp.client.ObserverResponseWhitError;
import com.andoresu.gordiskyapp.client.ServiceGenerator;
import com.andoresu.gordiskyapp.core.people.PeopleService;
import com.andoresu.gordiskyapp.core.person.data.PersonErrorsResponse;
import com.andoresu.gordiskyapp.security.SecureData;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class PersonPresenter implements PersonContract.ActionsListener {

    private final PersonContract.View view;
    private final Context context;
    private final PeopleService peopleService;

    public PersonPresenter(PersonContract.View view, Context context) {
        this.view = view;
        this.context = context;
        this.peopleService = ServiceGenerator.createService(PeopleService.class, SecureData.getToken());
    }

    @Override
    public void createPerson(Person person) {
        HashMap<String, Person> hashMap = new HashMap<>();
        hashMap.put("person", person);
        peopleService.create(hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ObserverResponseWhitError<Response<Person>, PersonErrorsResponse>(view, PersonErrorsResponse.class){
                    @Override
                    public void onNext(Response<Person> response) {
                        super.onNext(response);
                        if(response.isSuccessful()){
                            Person person = response.body();
                            view.showPerson(person);
                            view.showMessage("Persona Creada exitosamente");
                        }else{
                            if(getError() != null) {
                                view.showPersonErrors(getError().errors);
                            }
                        }

                    }
                });
    }

    @Override
    public void updatePerson(Person person) {
        HashMap<String, Person> hashMap = new HashMap<>();
        hashMap.put("person", person);
        peopleService.update(person.id, hashMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ObserverResponseWhitError<Response<Person>, PersonErrorsResponse>(view, PersonErrorsResponse.class){
                    @Override
                    public void onNext(Response<Person> response) {
                        super.onNext(response);
                        if(response.isSuccessful()){
                            Person person = response.body();
                            view.showPerson(person);
                            view.showMessage("Persona Actualizada exitosamente");
                        }else{
                            if(getError() != null) {
                                view.showPersonErrors(getError().errors);
                            }
                        }
                    }
                });
    }

    @Override
    public void deletePerson(Person person) {
        peopleService.delete(person.id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ObserverResponse<Response<Person>>(view){
                    @Override
                    public void onNext(Response<Person> response) {
                        super.onNext(response);
                        if(response.isSuccessful()){
                            //TODO: show successful message
                        }
                    }
                });
    }
}