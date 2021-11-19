package by.it_academy.food_diary.controller.dto;

import by.it_academy.food_diary.models.Training;
import by.it_academy.food_diary.models.WeightMeasurement;
import org.springframework.data.domain.Page;

public class WeightMeasurementByDateDto {
    private Page<WeightMeasurement> weightMeasurements;


    public Page<WeightMeasurement> getWeightMeasurements() {
        return weightMeasurements;
    }

    public void setWeightMeasurements(Page<WeightMeasurement> weightMeasurements) {
        this.weightMeasurements = weightMeasurements;
    }
}
