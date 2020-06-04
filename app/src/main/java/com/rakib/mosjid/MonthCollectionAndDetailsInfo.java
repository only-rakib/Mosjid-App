package com.rakib.mosjid;

public class MonthCollectionAndDetailsInfo {
    private String monthName;
    private String amount;

    public MonthCollectionAndDetailsInfo(String monthName, String amount) {
        this.monthName = monthName;
        this.amount = amount;
    }

    public String getMonthName() {
        return monthName;
    }

    public void setMonthName(String monthName) {
        this.monthName = monthName;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
