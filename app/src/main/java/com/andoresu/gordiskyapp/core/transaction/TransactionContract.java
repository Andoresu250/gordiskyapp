package com.andoresu.gordiskyapp.core.transaction;

import com.andoresu.gordiskyapp.authorization.data.Person;
import com.andoresu.gordiskyapp.core.person.data.PersonErrors;
import com.andoresu.gordiskyapp.core.transaction.data.TransactionErrors;
import com.andoresu.gordiskyapp.core.transactions.data.Transaction;
import com.andoresu.gordiskyapp.utils.BaseView;

public interface TransactionContract {

    interface View extends BaseView {

        void showTransactionErrors(TransactionErrors transactionErrors);

        void onTransactionCreated(Transaction transaction);

    }

    interface  ActionsListener {

        void createTransaction(Transaction transaction);

    }

    interface InteractionListener {
        void goToTransactions();
    }

}
