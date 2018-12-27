package com.example.eatnow.eatnow.Model;

public class OrderItem extends FoodItem {
    private int qty;

    public OrderItem() { }

    public OrderItem(int qty) {
        this.qty = qty;
    }

    public OrderItem(String n, float p, int s) {
        super(n, p, s);
    }

    public OrderItem(String n, float p, int s, int qty) {
        super(n, p, s);
        this.qty = qty;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
}
