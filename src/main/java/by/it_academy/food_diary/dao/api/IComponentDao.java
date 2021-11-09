package by.it_academy.food_diary.dao.api;

import by.it_academy.food_diary.models.Component;
import by.it_academy.food_diary.models.Journal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IComponentDao extends JpaRepository <Component,Long>{
}
