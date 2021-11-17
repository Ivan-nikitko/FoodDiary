package by.it_academy.food_diary.controller;


import by.it_academy.food_diary.controller.dto.TrainingByDateDto;
import by.it_academy.food_diary.models.Journal;
import by.it_academy.food_diary.models.Profile;
import by.it_academy.food_diary.models.Training;
import by.it_academy.food_diary.service.api.IProfileService;
import by.it_academy.food_diary.service.api.ITrainingService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.OptimisticLockException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@RestControllerAdvice
@RequestMapping("api/profile")
public class TrainingController {

    private final ITrainingService trainingService;
    private final IProfileService profileService;

    public TrainingController(ITrainingService trainingService, IProfileService profileService) {
        this.trainingService = trainingService;
        this.profileService = profileService;
    }

    @GetMapping("/{id_profile}/journal/active/")
    public ResponseEntity<?> show(@PathVariable("id_profile") Long idProfile,
                                  @RequestParam(value = "page", defaultValue = "0") int page,
                                  @RequestParam(value = "size", defaultValue = "2") int size,
                                  @RequestParam (value = "dt_start") Long dateStartMilliseconds,
                                  @RequestParam(value = "dt_end") Long dateEndMilliseconds) {
        try {
            LocalDateTime startOfDate =
                    LocalDateTime.ofInstant(Instant.ofEpochMilli(dateStartMilliseconds), ZoneId.systemDefault());
            LocalDateTime endOfDate =
                    LocalDateTime.ofInstant(Instant.ofEpochMilli(dateEndMilliseconds), ZoneId.systemDefault());

            TrainingByDateDto trainingByDateDto = trainingService.findAllByProfileIdAndCreationDate(startOfDate, endOfDate, idProfile);
            return new ResponseEntity<>(trainingByDateDto, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{id_profile}/journal/active")
    public ResponseEntity<?> save(@RequestBody Training training,
                                  @PathVariable("id_profile") Long idProfile) {
        Profile profile = profileService.get(idProfile);
        training.setProfile(profile);
        training.setCreationDate(LocalDateTime.now());
        training.setUpdateDate(training.getCreationDate());
        trainingService.save(training);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id_profile}/journal/active/{id_active}")
    public ResponseEntity<?> showOne(@PathVariable("id_profile") Long idProfile,
                                  @PathVariable("id_active") Long idActive) {
        try {
            Training training = trainingService.get(idActive);
            return new ResponseEntity<>(training, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id_profile}/journal/active/{id_active}/dt_update/{dt_update}")
    public ResponseEntity<?> update(@RequestBody Training training,
                                    @PathVariable("id_profile") Long idProfile,
                                    @PathVariable("id_active") Long idActive,
                                    @PathVariable("dt_update") String dtUpdate) {
        try {
            training.setUpdateDate(LocalDateTime.parse(dtUpdate));
            trainingService.update(training, idActive);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (OptimisticLockException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/{id_profile}/journal/active/{id_food}/dt_update/{dt_update}")
    public ResponseEntity<?> delete(@RequestBody Training training,
                                    @PathVariable("id_profile") Long id_profile,
                                    @PathVariable("id_active") Long idActive,
                                    @PathVariable("dt_update") Long dtUpdateMilliseconds) {
        try {
            LocalDateTime dtUpdate =
                    LocalDateTime.ofInstant(Instant.ofEpochMilli(dtUpdateMilliseconds), ZoneId.systemDefault());
            training.setUpdateDate(dtUpdate);
            trainingService.delete(training, idActive);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
