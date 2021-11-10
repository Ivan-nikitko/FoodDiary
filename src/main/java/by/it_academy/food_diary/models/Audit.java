package by.it_academy.food_diary.models;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Audit {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private User user;
    private String entityType;
    private Long entityId;

    @OneToOne
    private User userCreator;
    private LocalDateTime creationDate;
}
