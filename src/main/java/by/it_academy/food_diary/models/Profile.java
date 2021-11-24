package by.it_academy.food_diary.models;

import by.it_academy.food_diary.models.api.EActivity;
import by.it_academy.food_diary.models.api.EPurpose;
import by.it_academy.food_diary.models.api.ESex;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonIgnore
    @OneToOne
    private User user;
    @Column
    private double height;
    @Column
    private double weight;
    @Column
    private ESex sex;
    @Column
    private EActivity activity;
    @Column
    private EPurpose purpose;
    @Column(name = "dateOfBirth")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private LocalDate dateOfBirth;

    @JsonIgnore
    @OneToOne
    private User userCreator;
    @Column
    private LocalDateTime creationDate;
    @Column
    private LocalDateTime updateDate;

    public Profile() {
        this.creationDate = LocalDateTime.now();
        this.updateDate = creationDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public User getUserCreator() {
        return userCreator;
    }

    public void setUserCreator(User userCreator) {
        this.userCreator = userCreator;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }
}
