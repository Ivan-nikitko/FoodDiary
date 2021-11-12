package by.it_academy.food_diary.service;

import by.it_academy.food_diary.dao.api.IJournalDao;
import by.it_academy.food_diary.models.Journal;
import by.it_academy.food_diary.service.api.IJournalService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.OptimisticLockException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class JournalService implements IJournalService {

    private IJournalDao journalDao;

    public JournalService(IJournalDao journalDao) {
        this.journalDao = journalDao;
    }

    @Override
    public void save(Journal journal) {
        journalDao.save(journal);
    }

    @Override
    public Page<Journal> getAll(Pageable pageable) {
        return journalDao.findAll(pageable);
    }

    @Override
    public Page<Journal> getAllByProfileId(Pageable pageable, Long profileId) {
        return journalDao.findByProfile(profileId, pageable);
    }

    @Override
    public List<Journal> getOneDay(Integer day) {
        return journalDao.findByCreationDateIs(day);
    }

    @Override
    public Journal get(Long aLong) {
        return null;
    }

    @Override
    public void update(Journal updatedJournal, Long id) {
        Journal journalToUpdate = get(id);
        if (updatedJournal.getUpdateDate() != journalToUpdate.getUpdateDate()) {
            throw new OptimisticLockException("Journal has already been changed");
        } else {
            journalToUpdate.setProduct(updatedJournal.getProduct());
            journalToUpdate.setRecipe(updatedJournal.getRecipe());
            journalToUpdate.setMeasure(updatedJournal.getMeasure());
            journalToUpdate.setMealTime(updatedJournal.getMealTime());
            journalToUpdate.setUpdateDate(LocalDateTime.now());
            journalDao.saveAndFlush(journalToUpdate);
        }
    }

    @Override
    public void delete(Journal journal, Long id) {
        Journal dataBaseJournal = get(id);
        if (journal.getUpdateDate() != dataBaseJournal.getUpdateDate()) {
            throw new OptimisticLockException("Product has already been changed");
        } else {
            journalDao.deleteById(id);
        }
    }
}
