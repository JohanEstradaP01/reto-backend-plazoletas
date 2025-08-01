package com.pragma.powerup.domain.model;

public class Restaurant {

    private Long id;
    private String name;
    private String address;
    private Long ownerId;
    private String phoneNumber;
    private String logoUrl;
    private String nit;


    public Restaurant() {
    }

    public Restaurant(Long id, String name, String address, Long ownerId, String phoneNumber, String logoUrl, String nit) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.ownerId = ownerId;
        this.phoneNumber = phoneNumber;
        this.logoUrl = logoUrl;
        this.nit = nit;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getAddress() {
        return address;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public String getNit() {
        return nit;
    }

}
