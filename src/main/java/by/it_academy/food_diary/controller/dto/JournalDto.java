package by.it_academy.food_diary.controller.dto;

import by.it_academy.food_diary.models.Product;
import by.it_academy.food_diary.models.Profile;
import by.it_academy.food_diary.models.Recipe;
import by.it_academy.food_diary.models.api.EMealTime;


import java.time.LocalDateTime;

public class JournalDto {

    private Profile profile;

    private Recipe recipe;

    private Product product;

    private Double measure;

    private EMealTime mealTime;


    private LocalDateTime updateDate;

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

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }
}
