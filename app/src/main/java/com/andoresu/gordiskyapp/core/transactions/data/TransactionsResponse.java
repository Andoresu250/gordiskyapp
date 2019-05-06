package com.andoresu.gordiskyapp.core.transactions.data;

import com.andoresu.gordiskyapp.utils.BaseListResponse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TransactionsResponse extends BaseListResponse implements Serializable {

    public List<Transaction> monetaryTransactions = new ArrayList<>();

}
