package com.andoresu.gordiskyapp.core.loans.data;

import com.andoresu.gordiskyapp.authorization.data.Company;
import com.andoresu.gordiskyapp.authorization.data.Person;
import com.andoresu.gordiskyapp.utils.BaseDataModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.andoresu.gordiskyapp.utils.MyUtils.toMoney;

public class Loan extends BaseDataModel {

    public static final String NAME = "LOAN_TAG";

    @SerializedName("amount")
    @Expose
    public Double amount;
    @SerializedName("interest")
    @Expose
    public Double interest;
    @SerializedName("debt")
    @Expose
    public Double debt;
    @SerializedName("fees")
    @Expose
    public Integer fees;
    @SerializedName("feesFulfilled")
    @Expose
    public Integer feesFulfilled;
    @SerializedName("remainingFees")
    @Expose
    public Integer remainingFees;
    @SerializedName("frequency")
    @Expose
    public Integer frequency;
    @SerializedName("paid")
    @Expose
    public Double paid;
    @SerializedName("lastPaid")
    @Expose
    public Date lastPaid;
    @SerializedName("nextPaid")
    @Expose
    public Date nextPaid;
    @SerializedName("feeValue")
    @Expose
    public String feeValue;
    @SerializedName("person")
    @Expose
    public Person person;
    @SerializedName("company")
    @Expose
    public Company company;
    @SerializedName("payments")
    @Expose
    public ArrayList<Payment> payments = new ArrayList<>();
    @SerializedName("personId")
    @Expose
    private String personId;
    @SerializedName("missedPayments")
    @Expose
    public Integer missedPayments;

    public String getLastPaid() {
        if(lastPaid == null){
            return "N/A";
        }
        return simpleDate(lastPaid);
    }

    public String getNextPaid() {
        if(nextPaid == null){
            return "N/A";
        }
        return simpleDate(nextPaid);
    }

    public String getDebt() {
        return toMoney(debt);
    }

    public String getPaid(){
        return toMoney(paid);
    }

    public String getAmount() {
        return toMoney(amount);
    }

    public String getInterest() {
        if(interest == null){
            return "";
        }
        return interest.toString();
    }

    public String getFrequency() {
        if(frequency == null){
            return "";
        }
        switch (frequency){
            case 7:
                return "Semanal";
            case 14:
                return "Quincenal";
            case 30:
                return "Mensual";
        }
        return frequency + "dias";
    }

    public void setPerson(Person person){
        this.person = person;
        if(person != null){
            this.personId = person.id;
        }else{
            this.personId = null;
        }
    }
}
