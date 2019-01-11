package com.artistic_talent.eatnow.eatnow.Model;

public class CategoryItem {

    public String name;
    public int qty;

    public double price;
    public int stall_id;

    String order_id;
    String item_id;

    public CategoryItem(String name, int qty, double price, int stall_id) {
        this.name = name;
        this.qty = qty;
        this.price = price;
        this.stall_id = stall_id;
    }

    public CategoryItem(String name, int qty, double price, int stall_id, String order_id, String item_id) {
        this.name = name;
        this.qty = qty;
        this.price = price;
        this.stall_id = stall_id;
        this.order_id = order_id;
        this.item_id = item_id;
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

    public String getOrder_id() { return order_id; }

    public void setOrder_id(String order_id) { this.order_id = order_id; }

    public String getItem_id() { return item_id; }

    public void setItem_id(String item_id) { this.item_id = item_id; }
}
