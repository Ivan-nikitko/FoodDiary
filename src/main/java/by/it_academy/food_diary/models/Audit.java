package by.it_academy.food_diary.models;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Audit {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private User user;
    @Column
    private String description;

    @Column
    private String essenceName;

    @Column
    private Long essenceId;

    private LocalDateTime creationDate;


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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEssenceName() {
        return essenceName;
    }

    public void setEssenceName(String essenceName) {
        this.essenceName = essenceName;
    }

    public Long getEssenceId() {
        return essenceId;
    }

    public void setEssenceId(Long essenceId) {
        this.essenceId = essenceId;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }
}
