package com.pragma.powerup.domain.model;

public class OrderDish {

    private Dish dish;
    private Integer quantity;

    public OrderDish(Dish dish, Order order, Integer quantity) {
        this.dish = dish;
        this.quantity = quantity;
    }

    public OrderDish() {
    }

    public Dish getDish() {
        return dish;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

}