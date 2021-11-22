package by.it_academy.food_diary.controller.dto;

import by.it_academy.food_diary.models.User;


import java.time.LocalDateTime;

public class ProductDto {
    private Long id;
    private String name;
    private String brand;
    private double calories;
    private double protein;
    private double fats;
    private double carbonates;
    private double measure;
    private User userCreator;
    private LocalDateTime creationDate;
    private LocalDateTime updateDate;

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public double getProtein() {
        return protein;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }

    public double getFats() {
        return fats;
    }

    public void setFats(double fats) {
        this.fats = fats;
    }

    public double getCarbonates() {
        return carbonates;
    }

    public void setCarbonates(double carbonates) {
        this.carbonates = carbonates;
    }

    public double getMeasure() {
        return measure;
    }

    public void setMeasure(double measure) {
        this.measure = measure;
    }

    public User getUserCreator() {
        return userCreator;
    }

    public void setUserCreator(User userCreator) {
        this.userCreator = userCreator;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }
}
