package com.pragma.powerup.infrastructure.out.jpa.entity;

import com.pragma.powerup.domain.model.State;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long customerId;

    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private State state;

    private Long chefId;

    @ManyToOne
    @JoinColumn(name = "restaurantId")
    private RestaurantEntity restaurant;

    @OneToMany(mappedBy = "order", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private List<OrderDishEntity> dishes = new ArrayList<>();

    private Long employeeAssigned;


}
