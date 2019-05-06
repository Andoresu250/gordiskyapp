package com.andoresu.gordiskyapp.core.people;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.andoresu.gordiskyapp.R;
import com.andoresu.gordiskyapp.authorization.data.Person;
import com.andoresu.gordiskyapp.utils.BaseRecyclerViewAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PersonViewHolder extends BaseRecyclerViewAdapter.BaseViewHolder<Person>{

    @BindView(R.id.personNameTextView)
    public TextView personNameTextView;
    @BindView(R.id.personPhoneTextView)
    public TextView personPhoneTextView;
    @BindView(R.id.personAddressTextView)
    public TextView personAddressTextView;
    @BindView(R.id.callButton)
    public Button callButton;

    public PersonViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
