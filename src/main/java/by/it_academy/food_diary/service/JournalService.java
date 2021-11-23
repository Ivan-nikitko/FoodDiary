package by.it_academy.food_diary.service;

import by.it_academy.food_diary.controller.dto.JournalByDateDto;
import by.it_academy.food_diary.controller.dto.JournalDto;
import by.it_academy.food_diary.dao.api.IJournalDao;
import by.it_academy.food_diary.models.Journal;
import by.it_academy.food_diary.service.api.IJournalService;
import by.it_academy.food_diary.service.calculator.NutrientCalculator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.OptimisticLockException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class JournalService implements IJournalService {

    private final IJournalDao journalDao;
    private final NutrientCalculator nutrientCalculator;

    public JournalService(IJournalDao journalDao, NutrientCalculator nutrientCalculator) {
        this.journalDao = journalDao;
        this.nutrientCalculator = nutrientCalculator;
    }

    @Override
    public void save(JournalDto journalDto) {
        Journal journal = new Journal();
        journal.setProfile(journalDto.getProfile());
        journal.setProduct(journalDto.getProduct());
        journal.setRecipe(journalDto.getRecipe());
        journal.setMeasure(journalDto.getMeasure());
        journal.setMealTime(journalDto.getMealTime());
        Journal savedJournal = journalDao.save(journal);
        journalDto.setId(savedJournal.getId());
    }

    @Override
    public Page<Journal> getAll(Pageable pageable) {
        return journalDao.findAll(pageable);
    }

    @Override
    public Page<Journal> getAllByProfileId(Pageable pageable, Long profileId) {
        return journalDao.findByProfileId(profileId, pageable);
    }

    @Override
    public JournalByDateDto findAllByProfileIdAndCreationDate(LocalDateTime start, LocalDateTime end, Long id) {
        List<Journal> journals = journalDao.findAllByCreationDateBetweenAndProfileId(start, end, id);
        return nutrientCalculator.calculateNutrients(journals);
    }

    @Override
    public List<Journal> findAllByProfile(Long id) {
        return journalDao.findAllByProfileId(id);
    }


    @Override
    public Journal get(Long id) {
        return journalDao.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Journal not found"));
    }

    @Override
    public void update(JournalDto journalDto, Long id) {
        Journal journalToUpdate = get(id);
        if (journalDto.getUpdateDate().isEqual(journalToUpdate.getUpdateDate())) {
            journalToUpdate.setProduct(journalDto.getProduct());
            journalToUpdate.setRecipe(journalDto.getRecipe());
            journalToUpdate.setMeasure(journalDto.getMeasure());
            journalToUpdate.setMealTime(journalDto.getMealTime());
            journalDao.saveAndFlush(journalToUpdate);
            journalDto.setId(id);
        } else {
            throw new OptimisticLockException("Journal has already been changed");
        }
    }

    @Override
    public void delete(JournalDto journalDto, Long id) {
        Journal dataBaseJournal = get(id);
        if (dataBaseJournal == null) {
            throw new IllegalArgumentException("Journal not found");
        }
        if (journalDto.getUpdateDate().isEqual(dataBaseJournal.getUpdateDate())) {
            journalDao.deleteById(id);
            journalDto.setId(id);
        } else {
            throw new OptimisticLockException("Journal has already been changed");
        }
    }


}
