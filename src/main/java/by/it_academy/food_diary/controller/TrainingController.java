package by.it_academy.food_diary.controller;


import by.it_academy.food_diary.controller.dto.TrainingByDateDto;
import by.it_academy.food_diary.controller.dto.TrainingDto;
import by.it_academy.food_diary.models.Profile;
import by.it_academy.food_diary.models.Training;
import by.it_academy.food_diary.security.UserHolder;
import by.it_academy.food_diary.service.api.IProfileService;
import by.it_academy.food_diary.service.api.ITrainingService;
import by.it_academy.food_diary.utils.TimeUtil;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.OptimisticLockException;
import java.time.LocalDateTime;

@RestControllerAdvice
@RequestMapping("api/profile")
public class TrainingController {

    private final ITrainingService trainingService;
    private final IProfileService profileService;
    private final TimeUtil timeUtil;
    private final UserHolder userHolder;

    public TrainingController(ITrainingService trainingService, IProfileService profileService, TimeUtil timeUtil, UserHolder userHolder) {
        this.trainingService = trainingService;
        this.profileService = profileService;
        this.timeUtil = timeUtil;
        this.userHolder = userHolder;
    }

    @GetMapping("/{id_profile}/journal/active/")
    public ResponseEntity<?> show(@PathVariable("id_profile") Long idProfile,
                                  @RequestParam(value = "page", defaultValue = "0") int page,
                                  @RequestParam(value = "size", defaultValue = "2") int size,
                                  @RequestParam(value = "dt_start") Long dateStartMicroseconds,
                                  @RequestParam(value = "dt_end") Long dateEndMicroseconds) {

        if (Boolean.TRUE.equals(profileValidation(idProfile))) {
            try {
                Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
                LocalDateTime startOfDate = timeUtil.microsecondsToLocalDateTime(dateStartMicroseconds);
                LocalDateTime endOfDate = timeUtil.microsecondsToLocalDateTime(dateEndMicroseconds);
                TrainingByDateDto trainingByDateDto = trainingService.findAllByProfileIdAndCreationDate(startOfDate, endOfDate, idProfile,pageable);
                return new ResponseEntity<>(trainingByDateDto, HttpStatus.OK);
            } catch (IllegalArgumentException e) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/{id_profile}/journal/active/{id_active}")
    public ResponseEntity<?> showOne(@PathVariable("id_profile") Long idProfile,
                                     @PathVariable("id_active") Long idActive) {
        if (Boolean.TRUE.equals(profileValidation(idProfile))) {
            try {
                Training training = trainingService.get(idActive);
                return new ResponseEntity<>(training, HttpStatus.OK);
            } catch (IllegalArgumentException e) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/{id_profile}/journal/active")
    public ResponseEntity<?> save(@RequestBody TrainingDto trainingDto,
                                  @PathVariable("id_profile") Long idProfile) {
        if (Boolean.TRUE.equals(profileValidation(idProfile))) {
            trainingDto.setProfile(profileService.get(idProfile));
            trainingService.save(trainingDto);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping("/{id_profile}/journal/active/{id_active}/dt_update/{dt_update}")
    public ResponseEntity<?> update(@RequestBody TrainingDto trainingDto,
                                    @PathVariable("id_profile") Long idProfile,
                                    @PathVariable("id_active") Long idActive,
                                    @PathVariable("dt_update") Long dtUpdate) {
        if (Boolean.TRUE.equals(profileValidation(idProfile))) {
            try {
                trainingDto.setUpdateDate(timeUtil.microsecondsToLocalDateTime(dtUpdate));
                trainingService.update(trainingDto, idActive);
                return new ResponseEntity<>(HttpStatus.OK);
            } catch (OptimisticLockException e) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            } catch (IllegalArgumentException e) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("/{id_profile}/journal/active/{id_food}/dt_update/{dt_update}")
    public ResponseEntity<?> delete(@RequestBody TrainingDto trainingDto,
                                    @PathVariable("id_profile") Long idProfile,
                                    @PathVariable("id_active") Long idActive,
                                    @PathVariable("dt_update") Long dtUpdate) {
        if (Boolean.TRUE.equals(profileValidation(idProfile))) {
            try {
                trainingDto.setUpdateDate(timeUtil.microsecondsToLocalDateTime(dtUpdate));
                trainingService.delete(trainingDto, idActive);
                return new ResponseEntity<>(HttpStatus.OK);
            } catch (EmptyResultDataAccessException e) {
                return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    private Boolean profileValidation(Long idProfile) {
        return userHolder.getUser().getId() == profileService.get(idProfile).getUser().getId();
    }
}
