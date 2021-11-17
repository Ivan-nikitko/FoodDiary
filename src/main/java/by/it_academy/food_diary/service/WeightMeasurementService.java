package by.it_academy.food_diary.service;

import by.it_academy.food_diary.controller.dto.WeightMeasurementDto;
import by.it_academy.food_diary.dao.api.IWeightMeasurementDao;
import by.it_academy.food_diary.models.Journal;
import by.it_academy.food_diary.models.WeightMeasurement;
import by.it_academy.food_diary.service.api.IWeightMeasurementService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.OptimisticLockException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class WeightMeasurementService implements IWeightMeasurementService {

    private final IWeightMeasurementDao weightMeasurementDao;

    public WeightMeasurementService(IWeightMeasurementDao weightMeasurementDao) {
        this.weightMeasurementDao = weightMeasurementDao;
    }

    @Override
    public void save(WeightMeasurement weightMeasurement) {
        weightMeasurementDao.save(weightMeasurement);
    }

    @Override
    public Page<WeightMeasurement> getAll(Pageable pageable) {
        return null;
    }

    @Override
    public WeightMeasurement get(Long id) {
        return weightMeasurementDao.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Weight measurement not found")
        );
    }

    @Override
    public void update(WeightMeasurement updatedWeightMeasurement, Long id) {
        WeightMeasurement weightMeasurementToUpdate = get(id);
        if (updatedWeightMeasurement.getUpdateDate() != weightMeasurementToUpdate.getUpdateDate()) {
            throw new OptimisticLockException("Weight measurement has already been changed");
        } else {
            weightMeasurementToUpdate.setProfile(updatedWeightMeasurement.getProfile());
            weightMeasurementToUpdate.setWeight(updatedWeightMeasurement.getWeight());
            weightMeasurementToUpdate.setUpdateDate(LocalDateTime.now());
            weightMeasurementDao.saveAndFlush(weightMeasurementToUpdate);
        }

    }

    @Override
    public void delete(WeightMeasurement weightMeasurement, Long id) {
        WeightMeasurement dataBaseWeightMeasurement = get(id);
        if (dataBaseWeightMeasurement.getUpdateDate() != dataBaseWeightMeasurement.getUpdateDate()) {
            throw new OptimisticLockException("Weight measurement has already been changed");
        } else {
            weightMeasurementDao.deleteById(id);
        }

    }

    @Override
    public WeightMeasurementDto findAllByProfileIdAndCreationDate(LocalDateTime start, LocalDateTime end, Long id) {
        WeightMeasurementDto weightMeasurementDto = new WeightMeasurementDto();
        List<WeightMeasurement> weightMeasurementList = new ArrayList<>();
        List<WeightMeasurement> weightMeasurements = weightMeasurementDao.findAllByCreationDateBetweenAndProfileId(start, end, id);
        for (WeightMeasurement weightMeasurement : weightMeasurements) {
            weightMeasurementList.add(weightMeasurement);
        }
        weightMeasurementDto.setWeightMeasurements(weightMeasurementList);
        return weightMeasurementDto;
    }
}
