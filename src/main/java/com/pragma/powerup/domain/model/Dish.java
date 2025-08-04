package com.pragma.powerup.domain.model;

public class Dish {

    private Long id;
    private String name;
    private Long idCategoria;  //cambiar por modelo de categoria
    private String description;
    private float price;
    private Long restaurantId;
    private String urlImage;
    private boolean isActive;

}
