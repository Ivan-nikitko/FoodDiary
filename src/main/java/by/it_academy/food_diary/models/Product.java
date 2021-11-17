package by.it_academy.food_diary.models;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "Product")
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String name;
    @Column
    private String brand;
    @Column
    private double calories;
    @Column
    private double protein;
    @Column
    private double fats;
    @Column
    private double carbonates;
    @Column
    private double measure;

    @OneToOne
    private User userCreator;
    private LocalDateTime creationDate;

    @Column
   //TODO add @Version
    //TODO add email
    private LocalDateTime updateDate;


    public long getId() {
        return id;
    }

    public Product() {
        this.creationDate = LocalDateTime.now();
        this.updateDate = creationDate;
    }

    public String getName() {
        return name;
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
