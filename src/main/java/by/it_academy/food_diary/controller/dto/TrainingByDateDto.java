package by.it_academy.food_diary.controller.dto;

import by.it_academy.food_diary.models.Training;
import org.springframework.data.domain.Page;

import java.util.List;

public class TrainingByDateDto {
    private Page<Training> trainings;
    private double sumOfCalories;

    public Page<Training> getTrainings() {
        return trainings;
    }

    public void setTrainings(Page<Training> trainings) {
        this.trainings = trainings;
    }

    public double getSumOfCalories() {
        return sumOfCalories;
    }

    public void setSumOfCalories(double sumOfCalories) {
        this.sumOfCalories = sumOfCalories;
    }
}
