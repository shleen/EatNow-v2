package com.artistic_talent.eatnow.eatnow;

public class Stuff {
    private String name;
    private int qty;
    private int stall_id;

    public Stuff(){}

    public Stuff(String name, int qty, int stall_id) {
        this.name = name;
        this.qty = qty;
        this.stall_id = stall_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getStall_id() {
        return stall_id;
    }

    public void setStall_id(int stall_id) {
        this.stall_id = stall_id;
    }
}
