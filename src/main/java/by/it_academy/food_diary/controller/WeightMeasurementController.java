package by.it_academy.food_diary.controller;

import by.it_academy.food_diary.controller.dto.WeightMeasurementDto;
import by.it_academy.food_diary.models.Profile;
import by.it_academy.food_diary.models.WeightMeasurement;
import by.it_academy.food_diary.service.api.IProfileService;
import by.it_academy.food_diary.service.api.IWeightMeasurementService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.OptimisticLockException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@RestController
@RequestMapping("api/profile")
public class WeightMeasurementController {
    private final IWeightMeasurementService weightMeasurementService;
    private final IProfileService profileService;

    public WeightMeasurementController(IWeightMeasurementService weightMeasurementService, IProfileService profileService) {
        this.weightMeasurementService = weightMeasurementService;
        this.profileService = profileService;
    }

    @GetMapping("/{id_profile}/journal/weight/")
    public ResponseEntity<?> show(@PathVariable("id_profile") Long idProfile,
                                  @RequestParam(value = "page", defaultValue = "0") int page,
                                  @RequestParam(value = "size", defaultValue = "2") int size,
                                  @RequestParam (value = "dt_start") Long dateStartMilliseconds,
                                  @RequestParam(value = "dt_end") Long dateEndMilliseconds) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
            LocalDateTime startOfDate =
                    LocalDateTime.ofInstant(Instant.ofEpochMilli(dateStartMilliseconds), ZoneId.systemDefault());
            LocalDateTime endOfDate =
                    LocalDateTime.ofInstant(Instant.ofEpochMilli(dateEndMilliseconds), ZoneId.systemDefault());
            Page<WeightMeasurement> measurementPage = weightMeasurementService.findAllByProfileIdAndCreationDate(startOfDate, endOfDate, idProfile, pageable);
            return new ResponseEntity<>(measurementPage, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{id_profile}/journal/weight")
    public ResponseEntity<?> save(@RequestBody WeightMeasurement weightMeasurement,
                                  @PathVariable("id_profile") Long idProfile) {
        Profile profile = profileService.get(idProfile);
        weightMeasurement.setProfile(profile);
        weightMeasurement.setCreationDate(LocalDateTime.now());
        weightMeasurement.setUpdateDate(weightMeasurement.getCreationDate());
        weightMeasurementService.save(weightMeasurement);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id_profile}/journal/weight/{id_weight}")
    public ResponseEntity<?> showOne(@PathVariable("id_profile") Long idProfile,
                                     @PathVariable("id_weight") Long idWeight) {
        try {
            WeightMeasurement weightMeasurement = weightMeasurementService.get(idWeight);
            return new ResponseEntity<>(weightMeasurement, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id_profile}/journal/weight/{id_weight}/dt_update/{dt_update}")
    public ResponseEntity<?> update(@RequestBody WeightMeasurement weightMeasurement,
                                    @PathVariable("id_profile") Long idProfile,
                                    @PathVariable("id_weight") Long idWeight,
                                    @PathVariable("dt_update") Long dtUpdateMilliseconds) {
        try {
            LocalDateTime dtUpdate =
                    LocalDateTime.ofInstant(Instant.ofEpochMilli(dtUpdateMilliseconds), ZoneId.systemDefault());
            weightMeasurement.setUpdateDate(dtUpdate);
            weightMeasurementService.update(weightMeasurement, idWeight);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (OptimisticLockException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/{id_profile}/journal/weight/{id_weight}/dt_update/{dt_update}")
    public ResponseEntity<?> delete(@RequestBody WeightMeasurement weightMeasurement,
                                    @PathVariable("id_profile") Long id_profile,
                                    @PathVariable("id_weight") Long idWeight,
                                    @PathVariable("dt_update") Long dtUpdateMilliseconds) {
        try {
            LocalDateTime dtUpdate =
                    LocalDateTime.ofInstant(Instant.ofEpochMilli(dtUpdateMilliseconds), ZoneId.systemDefault());
            weightMeasurement.setUpdateDate(dtUpdate);
            weightMeasurementService.delete(weightMeasurement, idWeight);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
