package by.it_academy.food_diary.controller;


import by.it_academy.food_diary.controller.dto.TrainingByDateDto;
import by.it_academy.food_diary.controller.dto.TrainingDto;
import by.it_academy.food_diary.controller.validation.ProfileValidator;
import by.it_academy.food_diary.models.Training;
import by.it_academy.food_diary.models.User;
import by.it_academy.food_diary.models.api.ERole;
import by.it_academy.food_diary.security.UserHolder;
import by.it_academy.food_diary.service.api.IProfileService;
import by.it_academy.food_diary.service.api.ITrainingService;
import by.it_academy.food_diary.utils.TimeUtil;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.OptimisticLockException;
import java.time.LocalDateTime;
import java.util.Objects;

@RestControllerAdvice
@RequestMapping("api/profile")
public class TrainingController {

    private final ITrainingService trainingService;
    private final IProfileService profileService;
    private final TimeUtil timeUtil;
    private final UserHolder userHolder;
    private final ProfileValidator profileValidator;

    public TrainingController(ITrainingService trainingService, IProfileService profileService, TimeUtil timeUtil, UserHolder userHolder, ProfileValidator profileValidator) {
        this.trainingService = trainingService;
        this.profileService = profileService;
        this.timeUtil = timeUtil;
        this.userHolder = userHolder;
        this.profileValidator = profileValidator;
    }

    @GetMapping("/{id_profile}/journal/active/")
    public ResponseEntity<?> show(@PathVariable("id_profile") Long idProfile,
                                  @RequestParam(value = "page", defaultValue = "0") int page,
                                  @RequestParam(value = "size", defaultValue = "2") int size,
                                  @RequestParam(value = "dt_start") Long dateStartMicroseconds,
                                  @RequestParam(value = "dt_end") Long dateEndMicroseconds) {

        if (profileValidator.profileCheck(idProfile)) {
            try {
                Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
                LocalDateTime startOfDate = timeUtil.microsecondsToLocalDateTime(dateStartMicroseconds);
                LocalDateTime endOfDate = timeUtil.microsecondsToLocalDateTime(dateEndMicroseconds);
                TrainingByDateDto trainingByDateDto = trainingService.findAllByProfileIdAndCreationDate(startOfDate, endOfDate, idProfile, pageable);
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
        try {
            if (Boolean.TRUE.equals(profileCheck(idProfile,idActive))) {
                Training training = trainingService.get(idActive);
                return new ResponseEntity<>(training, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping("/{id_profile}/journal/active")
    public ResponseEntity<?> save(@RequestBody TrainingDto trainingDto,
                                  @PathVariable("id_profile") Long idProfile) {
        try {


            if (profileValidator.profileCheck(idProfile)) {
                trainingDto.setProfile(profileService.findById(idProfile));
                trainingService.save(trainingDto);
                return new ResponseEntity<>(HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id_profile}/journal/active/{id_active}/dt_update/{dt_update}")
    public ResponseEntity<?> update(@RequestBody TrainingDto trainingDto,
                                    @PathVariable("id_profile") Long idProfile,
                                    @PathVariable("id_active") Long idActive,
                                    @PathVariable("dt_update") Long dtUpdate) {
        try {
            if (Boolean.TRUE.equals(profileCheck(idProfile, idActive))) {
                trainingDto.setUpdateDate(timeUtil.microsecondsToLocalDateTime(dtUpdate));
                trainingDto.setProfile(profileService.findById(idProfile));
                trainingService.update(trainingDto, idActive);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        } catch (OptimisticLockException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id_profile}/journal/active/{id_active}/dt_update/{dt_update}")
    public ResponseEntity<?> delete(@RequestBody TrainingDto trainingDto,
                                    @PathVariable("id_profile") Long idProfile,
                                    @PathVariable("id_active") Long idActive,
                                    @PathVariable("dt_update") Long dtUpdate) {
        if (Boolean.TRUE.equals(profileCheck(idProfile, idActive))) {
            try {
                trainingDto.setUpdateDate(timeUtil.microsecondsToLocalDateTime(dtUpdate));
                trainingService.delete(trainingDto, idActive);
                return new ResponseEntity<>(HttpStatus.OK);
            } catch (OptimisticLockException e) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
            } catch (IllegalArgumentException e) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    private Boolean profileCheck(Long idProfile, Long idActive) {
        try {
            User currentUser = userHolder.getUser();
            if (currentUser.getRole().equals(ERole.ROLE_ADMIN)) {
                return true;
            }
            long userHolderId = userHolder.getUser().getId();
            long userProfileId = profileService.findById(idProfile).getUser().getId();
            Long trainingProfileId = trainingService.get(idActive).getProfile().getId();
            return userHolderId == userProfileId && Objects.equals(trainingProfileId, idProfile);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }


}
