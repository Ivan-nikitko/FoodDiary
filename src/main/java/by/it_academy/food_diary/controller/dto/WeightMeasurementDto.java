package by.it_academy.food_diary.controller.dto;

import by.it_academy.food_diary.models.Profile;
import by.it_academy.food_diary.models.WeightMeasurement;

import java.util.List;

public class WeightMeasurementDto {
    private List<WeightMeasurement> weightMeasurements;
    private Profile profile;

    public List<WeightMeasurement> getWeightMeasurements() {
        return weightMeasurements;
    }

    public void setWeightMeasurements(List<WeightMeasurement> weightMeasurements) {
        this.weightMeasurements = weightMeasurements;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
}
