package com.pragma.powerup.infrastructure.out.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DishEntity{

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity category;
    private String description;
    private float price;
    private Long restaurantId;
    private String urlImage;
    private boolean isActive;

}
