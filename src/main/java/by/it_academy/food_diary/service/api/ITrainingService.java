package by.it_academy.food_diary.service.api;

import by.it_academy.food_diary.controller.dto.TrainingByDateDto;
import by.it_academy.food_diary.controller.dto.TrainingDto;
import by.it_academy.food_diary.models.Training;

import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;


public interface ITrainingService {
    TrainingByDateDto findAllByProfileIdAndCreationDate(LocalDateTime start, LocalDateTime end, Long id, Pageable pageable);

    void save(TrainingDto trainingDto);

    Training get(Long id);

    void update(TrainingDto trainingDto, Long id);

    void delete(TrainingDto trainingDto, Long id);
}
