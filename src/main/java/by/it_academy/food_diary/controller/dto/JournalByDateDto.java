package by.it_academy.food_diary.controller.dto;

import by.it_academy.food_diary.models.Journal;


import java.util.List;

public class JournalByDateDto {
    private List<Journal> journals;
    private double sumOfCalories;

    public List<Journal> getJournals() {
        return journals;
    }

    public void setJournals(List<Journal> journals) {
        this.journals = journals;
    }

    public double getSumOfCalories() {
        return sumOfCalories;
    }

    public void setSumOfCalories(double sumOfCalories) {
        this.sumOfCalories = sumOfCalories;
    }
}
