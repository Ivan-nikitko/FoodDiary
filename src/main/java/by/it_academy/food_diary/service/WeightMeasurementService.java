package by.it_academy.food_diary.service;

import by.it_academy.food_diary.controller.dto.WeightMeasurementByDateDto;
import by.it_academy.food_diary.controller.dto.WeightMeasurementDto;
import by.it_academy.food_diary.dao.api.IWeightMeasurementDao;
import by.it_academy.food_diary.models.WeightMeasurement;
import by.it_academy.food_diary.security.UserHolder;
import by.it_academy.food_diary.service.api.IWeightMeasurementService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.OptimisticLockException;
import java.time.LocalDateTime;

@Service
public class WeightMeasurementService implements IWeightMeasurementService {

    private final IWeightMeasurementDao weightMeasurementDao;
    private final UserHolder userHolder;

    public WeightMeasurementService(IWeightMeasurementDao weightMeasurementDao, UserHolder userHolder) {
        this.weightMeasurementDao = weightMeasurementDao;
        this.userHolder = userHolder;
    }

    @Override
    public void save(WeightMeasurementDto weightMeasurementDto) {
        WeightMeasurement weightMeasurement = new WeightMeasurement();
        weightMeasurement.setUserCreator(userHolder.getUser());
        weightMeasurement.setProfile(weightMeasurementDto.getProfile());
        weightMeasurement.setWeight(weightMeasurementDto.getWeight());
        weightMeasurementDao.save(weightMeasurement);
    }

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
    public void update(WeightMeasurementDto weightMeasurementDto, Long id) {
        WeightMeasurement weightMeasurementToUpdate = get(id);
        if (weightMeasurementDto.getUpdateDate().isEqual(weightMeasurementToUpdate.getUpdateDate())) {
            weightMeasurementToUpdate.setProfile(weightMeasurementDto.getProfile());
            weightMeasurementToUpdate.setWeight(weightMeasurementDto.getWeight());
            weightMeasurementDao.saveAndFlush(weightMeasurementToUpdate);
        } else {
            throw new OptimisticLockException("Weight measurement has already been changed");
        }

    }

    @Override
    public void delete(WeightMeasurementDto weightMeasurementDto, Long id) {
        WeightMeasurement dataBaseWeightMeasurement = get(id);
        if (weightMeasurementDto.getUpdateDate().isEqual(dataBaseWeightMeasurement.getUpdateDate())) {
            weightMeasurementDao.deleteById(id);
        } else {
            throw new OptimisticLockException("Weight measurement has already been changed");
        }

    }

    @Override
    public WeightMeasurementByDateDto findAllByProfileIdAndCreationDate(LocalDateTime start, LocalDateTime end, Long id, Pageable pageable) {
        WeightMeasurementByDateDto weightMeasurementByDateDto = new WeightMeasurementByDateDto();
        Page<WeightMeasurement> weightMeasurements = weightMeasurementDao.findAllByCreationDateBetweenAndProfileId(start, end, id, pageable);
        weightMeasurementByDateDto.setWeightMeasurements(weightMeasurements);
        return weightMeasurementByDateDto;
    }
}
