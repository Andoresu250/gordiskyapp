package com.andoresu.gordiskyapp.core.loans.data;

import com.andoresu.gordiskyapp.utils.BaseListResponse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LoansResponse extends BaseListResponse implements Serializable {

    public List<Loan> loans = new ArrayList<>();
}
