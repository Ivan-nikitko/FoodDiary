package by.it_academy.food_diary.controller.dto;

import by.it_academy.food_diary.models.User;
import by.it_academy.food_diary.models.api.EActivity;
import by.it_academy.food_diary.models.api.EPurpose;
import by.it_academy.food_diary.models.api.ESex;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ProfileDto {


    private User user;
    private double height;
    private double weight;
    private ESex sex;
    private EActivity activity;
    private EPurpose purpose;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private LocalDate dateOfBirth;
    private LocalDateTime updateDate;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public ESex getSex() {
        return sex;
    }

    public void setSex(ESex sex) {
        this.sex = sex;
    }

    public EActivity getActivity() {
        return activity;
    }

    public void setActivity(EActivity activity) {
        this.activity = activity;
    }

    public EPurpose getPurpose() {
        return purpose;
    }

    public void setPurpose(EPurpose purpose) {
        this.purpose = purpose;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }
}
