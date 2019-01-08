package com.example.eatnow.eatnow.Model;

public class CategoryItem {

    public String name;
    public int quantity;

    public double price;
    public int stall_id;

    public CategoryItem(String name, int quantity, double price, int stall_id) {
        this.name = name;
        this.quantity = quantity;
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
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
