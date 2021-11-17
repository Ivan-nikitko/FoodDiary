package by.it_academy.food_diary.controller.dto;

import by.it_academy.food_diary.models.Training;

import java.util.List;

public class TrainingByDateDto {
    private List<Training> trainings;
    private double sumOfCalories;

    public List<Training> getTrainings() {
        return trainings;
    }

    public void setTrainings(List<Training> trainings) {
        this.trainings = trainings;
    }

    public double getSumOfCalories() {
        return sumOfCalories;
    }

    public void setSumOfCalories(double sumOfCalories) {
        this.sumOfCalories = sumOfCalories;
    }
}
