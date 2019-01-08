package com.example.eatnow.eatnow.Model;

public class CategoryItem {

    public String name;
    public int qty;

    public double price;
    public int stall_id;

    public CategoryItem(String name, int qty, double price, int stall_id) {
        this.name = name;
        this.qty = qty;
        this.price = price;
        this.stall_id = stall_id;
    }

    public CategoryItem() {
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStall_id() {
        return stall_id;
    }

    public void setStall_id(int stall_id) {
        this.stall_id = stall_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return qty;
    }

    public void setQuantity(int qty) {
        this.qty = qty;
    }
}
