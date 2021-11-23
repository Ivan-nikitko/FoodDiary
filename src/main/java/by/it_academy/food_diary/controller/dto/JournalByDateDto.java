package by.it_academy.food_diary.controller.dto;

import by.it_academy.food_diary.models.Journal;


import java.util.List;

public class JournalByDateDto {
    private List<Journal> journals;
    private double sumOfProteins;
    private double sumOfFats;
    private double sumOfCarbonates;
    private double sumOfCalories;

    public double getSumOfProteins() {
        return sumOfProteins;
    }

    public void setSumOfProteins(double sumOfProteins) {
        this.sumOfProteins = sumOfProteins;
    }

    public double getSumOfFats() {
        return sumOfFats;
    }

    public void setSumOfFats(double sumOfFats) {
        this.sumOfFats = sumOfFats;
    }

    public double getSumOfCarbonates() {
        return sumOfCarbonates;
    }

    public void setSumOfCarbonates(double sumOfCarbonates) {
        this.sumOfCarbonates = sumOfCarbonates;
    }

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
