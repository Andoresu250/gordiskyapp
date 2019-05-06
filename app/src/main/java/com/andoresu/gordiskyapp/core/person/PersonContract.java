package com.andoresu.gordiskyapp.core.person;

import com.andoresu.gordiskyapp.authorization.data.Person;
import com.andoresu.gordiskyapp.core.person.data.PersonErrors;
import com.andoresu.gordiskyapp.utils.BaseView;

public interface PersonContract {

    interface View extends BaseView {

        void showPerson(Person person);

        void showPersonErrors(PersonErrors personErrors);

    }

    interface  ActionsListener {

        void createPerson(Person person);

        void updatePerson(Person person);

        void deletePerson(Person person);

    }

    interface InteractionListener {

        void goToPersonLoans(Person person);

    }
}
