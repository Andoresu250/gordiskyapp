package com.andoresu.gordiskyapp.core.person;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.widget.Button;
import android.widget.EditText;

import com.andoresu.gordiskyapp.R;
import com.andoresu.gordiskyapp.authorization.data.Person;
import com.andoresu.gordiskyapp.core.person.data.PersonErrors;
import com.andoresu.gordiskyapp.utils.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;

import static com.andoresu.gordiskyapp.utils.MyUtils.getFirst;

public class PersonFragment extends BaseFragment implements PersonContract.View{

    @BindView(R.id.personFirstNamesEditText)
    public EditText personFirstNamesEditText;
    @BindView(R.id.personLastNamesEditText)
    public EditText personLastNamesEditText;
    @BindView(R.id.personIdentificationEditText)
    public EditText personIdentificationEditText;
    @BindView(R.id.personPhoneEditText)
    public EditText personPhoneEditText;
    @BindView(R.id.personAddressEditText)
    public EditText personAddressEditText;
    
    @BindView(R.id.personFirstNamesTextInputLayout)
    public TextInputLayout personFirstNamesTextInputLayout;
    @BindView(R.id.personLastNamesTextInputLayout)
    public TextInputLayout personLastNamesTextInputLayout;
    @BindView(R.id.personIdentificationTextInputLayout)
    public TextInputLayout personIdentificationTextInputLayout;
    @BindView(R.id.personPhoneTextInputLayout)
    public TextInputLayout personPhoneTextInputLayout;
    @BindView(R.id.personAddressTextInputLayout)
    public TextInputLayout personAddressTextInputLayout;

    @BindView(R.id.saveButton)
    public Button button;

    private PersonContract.ActionsListener actionsListener;

    public PersonContract.InteractionListener interactionListener;

    private Person person;

    public PersonFragment(){}

    public static PersonFragment newInstance(Person person, PersonContract.InteractionListener interactionListener) {
        Bundle args = new Bundle();
        args.putSerializable(Person.NAME, person);
        PersonFragment fragment = new PersonFragment();
        if(person == null || person.id == null){
            fragment.setCustomTitle("Nuevo Cliente");
        }else{
            fragment.setCustomTitle("Detalle Cliente");
        }
        fragment.setArguments(args);
        fragment.interactionListener = interactionListener;
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if(bundle != null){
            person = (Person) bundle.getSerializable(Person.NAME);
        }
        actionsListener = new PersonPresenter(this, getContext());
    }

    @Override
    public void handleView() {
        showPerson(person);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_person;
    }

    @Override
    public void showPerson(Person person) {
        if(person == null){
            return;
        }
        personFirstNamesEditText.setText(person.firstNames);
        personLastNamesEditText.setText(person.lastNames);
        personIdentificationEditText.setText(person.identification);
        personPhoneEditText.setText(person.phone);
        personAddressEditText.setText(person.address);
    }

    @Override
    public void showPersonErrors(PersonErrors personErrors) {
        if(personErrors == null){
            showMessage("Ha ocurrido un error");
            return;
        }
        personFirstNamesTextInputLayout.setError(getFirst(personErrors.firstNames));
        personLastNamesTextInputLayout.setError(getFirst(personErrors.lastNames));
        personIdentificationTextInputLayout.setError(getFirst(personErrors.identification));
        personPhoneTextInputLayout.setError(getFirst(personErrors.phone));
        personAddressTextInputLayout.setError(getFirst(personErrors.address));
    }

    @Override
    public void onLogoutFinish() {

    }

    @OnClick(R.id.saveButton)
    public void savePerson(){
        assignPersonAttributes();
        removePersonErrors();
        if(person.id != null){
            actionsListener.updatePerson(person);
        }else{
            actionsListener.createPerson(person);
        }
    }

    @OnClick(R.id.loansButton)
    public void goToPersonLoans(){
        interactionListener.goToPersonLoans(person);
    }

    public void assignPersonAttributes(){
        if(person == null){
            person = new Person();
        }
        person.firstNames = personFirstNamesEditText.getText().toString();
        person.lastNames = personLastNamesEditText.getText().toString();
        person.identification = personIdentificationEditText.getText().toString();
        person.phone = personPhoneEditText.getText().toString();
        person.address = personAddressEditText.getText().toString();
    }

    public void removePersonErrors(){
        personFirstNamesTextInputLayout.setError(null);
        personLastNamesTextInputLayout.setError(null);
        personIdentificationTextInputLayout.setError(null);
        personPhoneTextInputLayout.setError(null);
        personAddressTextInputLayout.setError(null);
    }
}
