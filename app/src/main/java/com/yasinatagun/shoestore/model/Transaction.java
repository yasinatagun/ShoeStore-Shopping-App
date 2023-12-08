package com.yasinatagun.shoestore.model;

import java.util.Date;
import java.util.Random;

public class Transaction {
    Random r = new Random();
    private CreditCard creditCard;
    private String adress;
    private String buyerName;
    private String zipCode;
    private String phoneNumber;
    private int transactionId;
    private Date date;

    public Transaction(CreditCard creditCard, String adress, String buyerName, String zipCode, String phoneNumber, int transactionId, Date date) {
        this.creditCard = creditCard;
        this.adress = adress;
        this.buyerName = buyerName;
        this.zipCode = zipCode;
        this.phoneNumber = phoneNumber;
        this.transactionId = transactionId;
        this.date = date;
    }


}
