package by.it_academy.food_diary.service;

import by.it_academy.food_diary.controller.dto.JournalByDayDto;
import by.it_academy.food_diary.dao.api.IJournalDao;
import by.it_academy.food_diary.models.Component;
import by.it_academy.food_diary.models.Journal;
import by.it_academy.food_diary.models.Product;
import by.it_academy.food_diary.models.Recipe;
import by.it_academy.food_diary.service.api.IJournalService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.OptimisticLockException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class JournalService implements IJournalService {

    private final IJournalDao journalDao;

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
        return journalDao.findByProfileId(profileId, pageable);
    }

    @Override
    public JournalByDayDto findAllByProfileIdAndCreationDate(LocalDateTime start, LocalDateTime end, Long id) {
        JournalByDayDto journalByDayDto = new JournalByDayDto();
        List<Journal> journalList = new ArrayList<>();
        double sumOfCalories = 0;
        List<Journal> journals = journalDao.findAllByCreationDateBetweenAndProfileId(start, end, id);
        for (Journal journal : journals) {
            journalList.add(journal);
            Double measure = journal.getMeasure();
            Recipe recipe = journal.getRecipe();
            Product product = journal.getProduct();
            if (product != null) {
                double productCalories = product.getCalories();
                double productMeasure = product.getMeasure();
                double calories = productCalories * measure / productMeasure;
                sumOfCalories += calories;
            }
            if (recipe != null) {
                double recipeMeasure = 0;
                double recipeCalories = 0;
                List<Component> components = recipe.getComponents();
                for (Component component : components) {
                    Product componentProduct = component.getProduct();
                    double componentProductCalories = componentProduct.getCalories();
                    double componentProductMeasure = componentProduct.getMeasure();
                    Double componentMeasure = component.getMeasure();
                    recipeMeasure += componentMeasure;
                    recipeCalories += componentProductCalories * componentMeasure / componentProductMeasure;
                    double calories = recipeCalories * measure / recipeMeasure;
                    sumOfCalories += calories;
                }
            }
        }
        journalByDayDto.setJournals(journalList);
        journalByDayDto.setSumOfCalories(sumOfCalories);
        return journalByDayDto;
    }

    @Override
    public List<Journal> findAllByProfile(Long id) {
        return journalDao.findAllByProfileId(id);
    }


    @Override
    public Journal get(Long id) {
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
