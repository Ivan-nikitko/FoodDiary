package by.it_academy.food_diary.controller.dto;

import by.it_academy.food_diary.models.Profile;

import javax.persistence.*;
import java.time.LocalDateTime;

public class TrainingDto {

    private String name;
    private double calories;

    private Profile profile;


    private LocalDateTime updateDate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }
}
