package com.andoresu.gordiskyapp.core.loans;

import com.andoresu.gordiskyapp.core.loans.data.Loan;
import com.andoresu.gordiskyapp.core.loans.data.LoansResponse;
import com.andoresu.gordiskyapp.utils.BaseView;

import java.util.Map;

public interface LoansContract {
    interface View extends BaseView {

        void showLoans(LoansResponse loansResponse);

    }

    interface  ActionsListener {

        void getLoans(Map<String, String> options);

    }

    interface InteractionListener {

        void goToLoanDetail(Loan loan);

    }
}
