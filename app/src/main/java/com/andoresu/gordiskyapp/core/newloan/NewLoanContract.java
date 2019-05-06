package com.andoresu.gordiskyapp.core.newloan;

import com.andoresu.gordiskyapp.authorization.data.Person;
import com.andoresu.gordiskyapp.core.loandetail.data.LoanErrors;
import com.andoresu.gordiskyapp.core.loans.data.Loan;
import com.andoresu.gordiskyapp.utils.BaseView;

public interface NewLoanContract {
    interface View extends BaseView {

        void showLoanErrors(LoanErrors loanErrors);

        void showLoan(Loan loan);

        void showPerson(Person person);

        void goToLoanDetails(Loan loan);

    }

    interface  ActionsListener {

        void createLoan(Loan loan);

        void getPerson(String identification);

        void projectLoan(Loan loan);

    }

    interface InteractionListener {

        void goToLoanDetail(Loan loan);

    }
}
