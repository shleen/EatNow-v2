package com.example.eatnow.eatnow.Model;

public class CategoryItem {

    public String name;
    public Integer quantity;

    public CategoryItem(String name, Integer quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public CategoryItem() {
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

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
