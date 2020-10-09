package com.example.talk_with_doctor;

public class Income {

    private String ID;
    private Integer month;
    private Integer sales;
    private Integer income;
    private Integer expences;
    private Integer profit;

    public Income() {
    }

    public Integer getProfit() {

        profit= income-expences;
        return profit;
    }

    public void setProfit(Integer profit) {
        this.profit = profit;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

    public Integer getIncome() {
        return income;
    }

    public void setIncome(Integer income) {
        this.income = income;
    }

    public Integer getExpences() {
        return expences;
    }

    public void setExpences(Integer expences) {
        this.expences = expences;
    }
}
