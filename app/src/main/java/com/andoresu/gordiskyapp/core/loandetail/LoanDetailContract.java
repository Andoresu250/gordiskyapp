package com.andoresu.gordiskyapp.core.loandetail;

import com.andoresu.gordiskyapp.core.loandetail.data.LoanErrors;
import com.andoresu.gordiskyapp.core.loans.data.Loan;
import com.andoresu.gordiskyapp.utils.BaseView;

public interface LoanDetailContract {
    interface View extends BaseView {

        void showLoan(Loan loan);

        void showLoanErrors(LoanErrors loanErrors);

    }

    interface  ActionsListener {

        void createLoan(Loan loan);

        void updateLoan(Loan loan);

        void deleteLoan(Loan loan);

    }

    interface InteractionListener {



    }
}
