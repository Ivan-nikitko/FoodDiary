package by.it_academy.food_diary.dao.api;

import by.it_academy.food_diary.models.Audit;
import by.it_academy.food_diary.models.Journal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAuditDao extends JpaRepository <Audit,Long>{
}
