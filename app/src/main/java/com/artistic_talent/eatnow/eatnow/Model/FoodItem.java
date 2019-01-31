package com.artistic_talent.eatnow.eatnow.Model;

public class FoodItem {
    private String name;
    private float price;
    private int stall_id;
    private String image_addr;

    public FoodItem() {  }

    public FoodItem(String n, float p, int s)
    {
        name = n;
        price = p;
        stall_id = s;
    }

    public FoodItem(String n, float p, int s, String img)
    {
        name = n;
        price = p;
        stall_id = s;
        image_addr = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getStall_id() {
        return stall_id;
    }

    public void setStall_id(int stall_id) {
        this.stall_id = stall_id;
    }

    public String getImage_addr() {
        return image_addr;
    }

    public void setImage_addr(String image_addr) {
        this.image_addr = image_addr;
    }
}
