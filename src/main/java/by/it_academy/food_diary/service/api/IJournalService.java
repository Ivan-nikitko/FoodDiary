package by.it_academy.food_diary.service.api;

import by.it_academy.food_diary.controller.dto.JournalByDateDto;
import by.it_academy.food_diary.models.Journal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface IJournalService extends IService<Journal,Long> {
    Page<Journal> getAllByProfileId(Pageable pageable, Long profileId);
    JournalByDateDto findAllByProfileIdAndCreationDate(LocalDateTime start, LocalDateTime end, Long id);
    List<Journal> findAllByProfile(Long id);

}
