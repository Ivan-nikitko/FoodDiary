package by.it_academy.food_diary.models;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class WeightMeasurement {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;

    private double weight;
    @OneToOne
    private Profile profile;

    @OneToOne
    private User userCreator;
    private LocalDateTime creationDate;
    private LocalDateTime updateDate;

}
