package by.it_academy.food_diary.controller.dto;

import by.it_academy.food_diary.models.Profile;
import by.it_academy.food_diary.models.User;
import by.it_academy.food_diary.models.WeightMeasurement;

import javax.persistence.Column;
import javax.persistence.OneToOne;
import javax.persistence.Version;
import java.time.LocalDateTime;
import java.util.List;

public class WeightMeasurementDto {

    private Long id;

    private double weight;

    private Profile profile;

    private LocalDateTime updateDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
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
