package com.andoresu.gordiskyapp.core.people;

import com.andoresu.gordiskyapp.authorization.data.Person;
import com.andoresu.gordiskyapp.core.people.data.PeopleResponse;
import com.andoresu.gordiskyapp.utils.BaseView;

import java.util.Map;

public interface PeopleContract {

    interface View extends BaseView {

        void showPeople(PeopleResponse peopleResponse);

    }

    interface  ActionsListener {

        void getPeople(Map<String, String> options);

    }

    interface InteractionListener {

        void goToPersonDetail(Person person);

    }

}
