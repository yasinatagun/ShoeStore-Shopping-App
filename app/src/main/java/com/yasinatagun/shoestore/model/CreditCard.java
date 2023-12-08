package com.yasinatagun.shoestore.model;

public class CreditCard {
    private String cardNumber;
    private String cardName;
    private String cardMonth;
    private String cardYear;
    private String cardCVV;

    public CreditCard(String cardNumber, String cardName, String cardMonth, String cardYear, String cardCVV) {
        this.cardNumber = cardNumber;
        this.cardName = cardName;
        this.cardMonth = cardMonth;
        this.cardYear = cardYear;
        this.cardCVV = cardCVV;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getCardMonth() {
        return cardMonth;
    }

    public void setCardMonth(String cardMonth) {
        this.cardMonth = cardMonth;
    }

    public String getCardYear() {
        return cardYear;
    }

    public void setCardYear(String cardYear) {
        this.cardYear = cardYear;
    }

    public String getCardCVV() {
        return cardCVV;
    }

    public void setCardCVV(String cardCVV) {
        this.cardCVV = cardCVV;
    }
}
