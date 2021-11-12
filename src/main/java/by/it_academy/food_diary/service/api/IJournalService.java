package by.it_academy.food_diary.service.api;

import by.it_academy.food_diary.models.Journal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IJournalService extends Iservice<Journal,Long>{
    Page<Journal> getAllByProfileId(Pageable pageable, Long profileId);
    List<Journal> getOneDay(Integer day);
}
