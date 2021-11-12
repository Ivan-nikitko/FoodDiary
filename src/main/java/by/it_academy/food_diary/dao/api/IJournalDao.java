package by.it_academy.food_diary.dao.api;

import by.it_academy.food_diary.models.Journal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IJournalDao extends JpaRepository <Journal,Long>{
    Page<Journal> findByProfile(Long id, Pageable pageable);
    List<Journal> findByCreationDateIs(Integer day);
}
