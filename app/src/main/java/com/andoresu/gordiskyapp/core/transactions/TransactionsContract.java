package com.andoresu.gordiskyapp.core.transactions;

import com.andoresu.gordiskyapp.core.resume.data.Resume;
import com.andoresu.gordiskyapp.core.transactions.data.TransactionsResponse;
import com.andoresu.gordiskyapp.utils.BaseView;

import java.util.Map;

public interface TransactionsContract {

    interface View extends BaseView {

        void showTransactions(TransactionsResponse transactionsResponse);

    }

    interface  ActionsListener {

        void getTransactions(Map<String, String> options);

    }

    interface InteractionListener {

        void goToNewTransaction();

    }
}
