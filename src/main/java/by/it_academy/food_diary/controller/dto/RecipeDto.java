package by.it_academy.food_diary.controller.dto;

import by.it_academy.food_diary.models.Ingredient;

import java.time.LocalDateTime;
import java.util.List;

public class RecipeDto {


    private String name;

    private List<Ingredient> ingredients;

    private LocalDateTime updateDate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }
}
