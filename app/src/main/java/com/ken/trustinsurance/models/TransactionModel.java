package com.ken.trustinsurance.models;

public class TransactionModel {

    String plan,subscription,cost,name;

    public TransactionModel() {
    }

    public TransactionModel(String plan, String subscription, String cost, String name) {
        this.plan = plan;
        this.subscription = subscription;
        this.cost = cost;
        this.name = name;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public String getSubscription() {
        return subscription;
    }

    public void setSubscription(String subscription) {
        this.subscription = subscription;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

