package com.andoresu.gordiskyapp.core.loandetail.dialog;

import com.andoresu.gordiskyapp.authorization.data.Person;
import com.andoresu.gordiskyapp.core.loans.data.Loan;
import com.andoresu.gordiskyapp.core.person.data.PersonErrors;
import com.andoresu.gordiskyapp.utils.BaseView;

public interface PaymentDialogContract {

    interface View extends BaseView {
        void setLoan(Loan loan);
    }

    interface  ActionsListener {

        void pay(Loan loan, Double value);

    }

    interface InteractionListener {

        void refreshLoan(Loan loan);

    }

}
