package by.it_academy.food_diary.service.api;

import by.it_academy.food_diary.controller.dto.TrainingByDateDto;
import by.it_academy.food_diary.models.Training;

import java.time.LocalDateTime;

public interface ITrainingService extends IService<Training,Long> {
    TrainingByDateDto findAllByProfileIdAndCreationDate(LocalDateTime start, LocalDateTime end, Long id);
}
