package com.andoresu.gordiskyapp.core.people;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andoresu.gordiskyapp.R;
import com.andoresu.gordiskyapp.authorization.data.Person;
import com.andoresu.gordiskyapp.list.RecyclerViewAdapter;

import static com.andoresu.gordiskyapp.utils.MyUtils.phoneIntent;

public class PersonAdapter extends RecyclerViewAdapter<Person> {

    public PersonAdapter(Context context, OnItemClickListener<Person> listener) {
        super(context, listener);
    }

    @Override
    public void setData(BaseViewHolder<Person> holder, int position) {
        PersonViewHolder viewHolder = (PersonViewHolder) holder;
        Person person = get(position);
        viewHolder.personNameTextView.setText(getText(R.string.lbld_personName, person.getFullName()));
        viewHolder.personAddressTextView.setText(getText(R.string.lbld_personAddress, person.address));
        viewHolder.personPhoneTextView.setText(getText(R.string.lbld_personPhone, person.phone));
        viewHolder.callButton.setOnClickListener(view -> context.startActivity(phoneIntent(person.phone)));
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.item_person;
    }

    @NonNull
    @Override
    public BaseViewHolder<Person> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(getLayoutResId(), parent, false);
        return new PersonViewHolder(view);
    }
}
