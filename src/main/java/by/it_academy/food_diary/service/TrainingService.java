package by.it_academy.food_diary.service;


import by.it_academy.food_diary.controller.dto.TrainingByDateDto;
import by.it_academy.food_diary.dao.api.ITrainingDao;
import by.it_academy.food_diary.models.*;
import by.it_academy.food_diary.service.api.ITrainingService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.OptimisticLockException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TrainingService implements ITrainingService {

    private final ITrainingDao trainingDao;

    public TrainingService(ITrainingDao trainingDao) {
        this.trainingDao = trainingDao;
    }

    @Override
    public TrainingByDateDto findAllByProfileIdAndCreationDate(LocalDateTime start, LocalDateTime end, Long id) {
        TrainingByDateDto trainingByDateDto = new TrainingByDateDto();
        List<Training> trainingList = new ArrayList<>();
        double sumOfCalories = 0;
        List<Training> trainings = trainingDao.findAllByCreationDateBetweenAndProfileId(start, end, id);
        for (Training training : trainings) {
            trainingList.add(training);
            sumOfCalories += training.getCalories();
        }
        trainingByDateDto.setTrainings(trainingList);
        trainingByDateDto.setSumOfCalories(sumOfCalories);
        return trainingByDateDto;
    }

    @Override
    public void save(Training item) {

    }

    @Override
    public Page<Training> getAll(Pageable pageable) {
        return null;
    }

    @Override
    public Training get(Long id) {
        return trainingDao.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Training not found"));
    }

    @Override
    public void update(Training updatedTraining, Long id) {
        Training trainingToUpdate = get(id);
        if (updatedTraining.getUpdateDate() != trainingToUpdate.getUpdateDate()) {
            throw new OptimisticLockException("Journal has already been changed");
        } else {
            trainingToUpdate.setName(updatedTraining.getName());
            trainingToUpdate.setProfile(updatedTraining.getProfile());
            trainingToUpdate.setCalories(updatedTraining.getCalories());
            trainingToUpdate.setUpdateDate(LocalDateTime.now());
            trainingDao.saveAndFlush(trainingToUpdate);
        }

    }

    @Override
    public void delete(Training training, Long id) {
        Training dataBaseTraining = get(id);
        if (training.getUpdateDate() != dataBaseTraining.getUpdateDate()) {
            throw new OptimisticLockException("Product has already been changed");
        } else {
            trainingDao.deleteById(id);
        }

    }
}
