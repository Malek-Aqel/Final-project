package com.example.afinal;

import java.io.Serializable;
import java.util.Date;

public class room implements Serializable {


    int id;
    String name;
    String disc;
    int price;
    int available;
    Date from;
    Date too;
    String area;


    public room(int id, String name, String disc, int price, int available, Date from, Date too, String area) {
        this.id = id;
        this.name = name;
        this.disc = disc;
        this.price = price;
        this.available = available;
        this.from = from;
        this.too = too;
        this.area = area;
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

    public String getDisc() {
        return disc;
    }

    public void setDisc(String disc) {
        this.disc = disc;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getToo() {
        return too;
    }

    public void setToo(Date too) {
        this.too = too;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}
