package com.andoresu.gordiskyapp.core.people.data;

import com.andoresu.gordiskyapp.authorization.data.Person;
import com.andoresu.gordiskyapp.utils.BaseListResponse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PeopleResponse extends BaseListResponse implements Serializable {

    public List<Person> people = new ArrayList<>();

}
