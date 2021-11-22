package by.it_academy.food_diary.service;


import by.it_academy.food_diary.controller.dto.TrainingByDateDto;
import by.it_academy.food_diary.controller.dto.TrainingDto;
import by.it_academy.food_diary.dao.api.ITrainingDao;
import by.it_academy.food_diary.models.*;
import by.it_academy.food_diary.security.UserHolder;
import by.it_academy.food_diary.service.api.ITrainingService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.OptimisticLockException;
import java.time.LocalDateTime;


@Service
public class TrainingService implements ITrainingService {

    private final ITrainingDao trainingDao;
    private final UserHolder userHolder;


    public TrainingService(ITrainingDao trainingDao, UserHolder userHolder) {
        this.trainingDao = trainingDao;
        this.userHolder = userHolder;
    }

    @Override
    public TrainingByDateDto findAllByProfileIdAndCreationDate(LocalDateTime start, LocalDateTime end, Long id, Pageable pageable) {
        TrainingByDateDto trainingByDateDto = new TrainingByDateDto();
        double sumOfCalories = 0;
        Page<Training> trainings = trainingDao.findAllByCreationDateBetweenAndProfileId(start, end, id, pageable);
        for (Training training : trainings) {
            sumOfCalories += training.getCalories();
        }
        trainingByDateDto.setTrainings(trainings);
        trainingByDateDto.setSumOfCalories(sumOfCalories);
        return trainingByDateDto;
    }

    @Override
    public void save(TrainingDto trainingDto) {
        Training training = new Training();
        training.setUserCreator(userHolder.getUser());
        training.setProfile(trainingDto.getProfile());
        training.setName(trainingDto.getName());
        training.setCalories(trainingDto.getCalories());
        Training savedTraining = trainingDao.save(training);
        trainingDto.setId(savedTraining.getId());
    }

    @Override
    public Training get(Long id) {
        return trainingDao.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Training not found"));
    }

    @Override
    public void update(TrainingDto trainingDto, Long id) {
        Training trainingToUpdate = get(id);
        if (trainingDto.getUpdateDate().isEqual(trainingToUpdate.getUpdateDate())) {
            trainingToUpdate.setName(trainingDto.getName());
            trainingToUpdate.setProfile(trainingDto.getProfile());
            trainingToUpdate.setCalories(trainingDto.getCalories());
            trainingDao.saveAndFlush(trainingToUpdate);
            trainingDto.setId(id);
        } else {
            throw new OptimisticLockException("Training has already been changed");
        }
    }

    @Override
    public void delete(TrainingDto trainingDto, Long id) {
        Training dataBaseTraining = get(id);
        if (dataBaseTraining == null) {
            throw new IllegalArgumentException("Training not found");
        }
        if (trainingDto.getUpdateDate().isEqual(dataBaseTraining.getUpdateDate())) {
            trainingDao.deleteById(id);
            trainingDto.setId(id);
        } else {
            throw new OptimisticLockException("Training has already been changed");
        }

    }
}
