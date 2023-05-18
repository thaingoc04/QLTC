package com.example.qltc;

import java.io.Serializable;

public class ThuChi implements Serializable {
    private int id;
    private String name;
    private String date;
    private int cost;
    private int ThuChi;

    public ThuChi(int id, String name, String date, int cost, int thuChi) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.cost = cost;
        ThuChi = thuChi;
    }

    public ThuChi(String name, String date, int cost, int thuChi) {
        this.name = name;
        this.date = date;
        this.cost = cost;
        ThuChi = thuChi;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int isThuChi() {
        return ThuChi;
    }

    public void setThuChi(int thuChi) {
        ThuChi = thuChi;
    }

}
