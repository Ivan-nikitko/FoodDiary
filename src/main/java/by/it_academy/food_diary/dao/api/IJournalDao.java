package by.it_academy.food_diary.dao.api;

import by.it_academy.food_diary.models.Journal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface IJournalDao extends JpaRepository <Journal,Long>{
    Page<Journal> findByProfileId(Long id, Pageable pageable);
    List<Journal> findAllByCreationDateBetweenAndProfileId(LocalDateTime start, LocalDateTime end, Long id);
    List<Journal> findAllByProfileId(Long id);
}
