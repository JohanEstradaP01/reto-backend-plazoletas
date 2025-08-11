package com.pragma.powerup.domain.model;

import java.time.LocalDate;
import java.util.List;

public class Order {

    private Long id;
    private User customer;
    private LocalDate date;
    private State state;
    private Long chefId;
    private Restaurant restaurant;
    private List<OrderDish> dishes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Long getChefId() {
        return chefId;
    }

    public void setChefId(Long chefId) {
        this.chefId = chefId;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public List<OrderDish> getDishes() {
        return dishes;
    }

    public void setDishes(List<OrderDish> dishes) {
        this.dishes = dishes;
    }

    public Order() {
    }

    public Order(Long id, User customer, LocalDate date, State state, Long chefId, Restaurant restaurant, List<OrderDish> dishes) {
        this.id = id;
        this.customer = customer;
        this.date = date;
        this.state = state;
        this.chefId = chefId;
        this.restaurant = restaurant;
        this.dishes = dishes;
    }

}
