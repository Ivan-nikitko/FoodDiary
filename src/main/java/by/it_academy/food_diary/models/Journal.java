package by.it_academy.food_diary.models;

import by.it_academy.food_diary.models.api.EMealTime;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Journal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne
    private Profile profile;
    @OneToOne
    private Recipe recipe;
    @OneToOne
    private Product product;
    @Column
    private Double measure;
    @Column
    private EMealTime mealTime;


    private LocalDateTime creationDate;
    private LocalDateTime updateDate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Double getMeasure() {
        return measure;
    }

    public void setMeasure(Double measure) {
        this.measure = measure;
    }

    public EMealTime getMealTime() {
        return mealTime;
    }

    public void setMealTime(EMealTime mealTime) {
        this.mealTime = mealTime;
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

