package com.example.datdoanonline.Domain;

import java.io.Serializable;

public class FoodDomain implements Serializable {
    private String title;
    private String description;
    private String picUrl;
    private double price;
    private int time;
    private int energy;
    private double score;
    private int numberinCart;

    public FoodDomain(String title, double score, int energy, int time, double price, String picUrl, String description) {
        this.title = title;
        this.score = score;
        this.energy = energy;
        this.time = time;
        this.price = price;
        this.picUrl = picUrl;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public int getNumberinCart() {
        return numberinCart;
    }

    public void setNumberinCart(int numberinCart) {
        this.numberinCart = numberinCart;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
