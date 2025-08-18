package com.pragma.powerup.domain.model;

import java.time.LocalDateTime;

public class TraceabilityModel {

    private Long orderId;
    private Long customerId;
    private String customerEmail;
    private LocalDateTime date;
    private State previousState;
    private State newState;
    private Long employeeId;
    private String employeeEmail;

    public TraceabilityModel(Long orderId, Long customerId, String customerEmail, LocalDateTime date, State previousState, State newState, Long employeeId, String employeeEmail) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.customerEmail = customerEmail;
        this.date = date;
        this.previousState = previousState;
        this.newState = newState;
        this.employeeId = employeeId;
        this.employeeEmail = employeeEmail;
    }

    public TraceabilityModel() {
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public State getPreviousState() {
        return previousState;
    }

    public void setPreviousState(State previousState) {
        this.previousState = previousState;
    }

    public State getNewState() {
        return newState;
    }

    public void setNewState(State newState) {
        this.newState = newState;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeEmail() {
        return employeeEmail;
    }

    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }

}
