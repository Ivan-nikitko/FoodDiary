package by.it_academy.food_diary.models;

import by.it_academy.food_diary.models.api.EActivity;
import by.it_academy.food_diary.models.api.EPurpose;
import by.it_academy.food_diary.models.api.ESex;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    User user;
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

    @OneToOne
    private User userCreator;
    @Column
    private LocalDateTime creationDate;
    @Column
    private LocalDateTime updateDate;

}
