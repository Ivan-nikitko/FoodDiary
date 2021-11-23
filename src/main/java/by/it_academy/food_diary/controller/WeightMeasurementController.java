package by.it_academy.food_diary.controller;

import by.it_academy.food_diary.controller.dto.WeightMeasurementByDateDto;
import by.it_academy.food_diary.controller.dto.WeightMeasurementDto;
import by.it_academy.food_diary.models.User;
import by.it_academy.food_diary.models.WeightMeasurement;
import by.it_academy.food_diary.models.api.ERole;
import by.it_academy.food_diary.security.UserHolder;
import by.it_academy.food_diary.service.api.IProfileService;
import by.it_academy.food_diary.service.api.IWeightMeasurementService;
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

@RestController
@RequestMapping("api/profile")
public class WeightMeasurementController {
    private final IWeightMeasurementService weightMeasurementService;
    private final IProfileService profileService;
    private final TimeUtil timeUtil;
    private final UserHolder userHolder;

    public WeightMeasurementController(IWeightMeasurementService weightMeasurementService, IProfileService profileService, TimeUtil timeUtil, UserHolder userHolder) {
        this.weightMeasurementService = weightMeasurementService;
        this.profileService = profileService;
        this.timeUtil = timeUtil;
        this.userHolder = userHolder;
    }

    @GetMapping("/{id_profile}/journal/weight/")
    public ResponseEntity<?> show(@PathVariable("id_profile") Long idProfile,
                                  @RequestParam(value = "page", defaultValue = "0") int page,
                                  @RequestParam(value = "size", defaultValue = "2") int size,
                                  @RequestParam(value = "dt_start") Long dateStartMicroseconds,
                                  @RequestParam(value = "dt_end") Long dateEndMicroseconds
    ) {
        if (Boolean.TRUE.equals(profileCheck(idProfile))) {
            try {
                Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
                LocalDateTime startOfDate = timeUtil.microsecondsToLocalDateTime(dateStartMicroseconds);
                LocalDateTime endOfDate = timeUtil.microsecondsToLocalDateTime(dateEndMicroseconds);
                WeightMeasurementByDateDto weightMeasurementByDateDto = weightMeasurementService.findAllByProfileIdAndCreationDate(startOfDate, endOfDate, idProfile, pageable);
                return new ResponseEntity<>(weightMeasurementByDateDto, HttpStatus.OK);
            } catch (IllegalArgumentException e) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);

        }
    }

    @GetMapping("/{id_profile}/journal/weight/{id_weight}")
    public ResponseEntity<?> showOne(@PathVariable("id_profile") Long idProfile,
                                     @PathVariable("id_weight") Long idWeight) {
        try {
            if (Boolean.TRUE.equals(profileCheck(idProfile, idWeight))) {
                WeightMeasurement weightMeasurement = weightMeasurementService.get(idWeight);
                return new ResponseEntity<>(weightMeasurement, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }


    @PostMapping("/{id_profile}/journal/weight")
    public ResponseEntity<?> save(@RequestBody WeightMeasurementDto weightMeasurementDto,
                                  @PathVariable("id_profile") Long idProfile) {
        if (Boolean.TRUE.equals(profileCheck(idProfile))) {
            weightMeasurementDto.setProfile(profileService.findById(idProfile));
            weightMeasurementService.save(weightMeasurementDto);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping("/{id_profile}/journal/weight/{id_weight}/dt_update/{dt_update}")
    public ResponseEntity<?> update(@RequestBody WeightMeasurementDto weightMeasurementDto,
                                    @PathVariable("id_profile") Long idProfile,
                                    @PathVariable("id_weight") Long idWeight,
                                    @PathVariable("dt_update") Long dtUpdate) {
        try {
            if (Boolean.TRUE.equals(profileCheck(idProfile, idWeight))) {
                weightMeasurementDto.setUpdateDate(timeUtil.microsecondsToLocalDateTime(dtUpdate));
                weightMeasurementDto.setProfile(profileService.findById(idProfile));
                weightMeasurementService.update(weightMeasurementDto, idWeight);
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

    @DeleteMapping("/{id_profile}/journal/weight/{id_weight}/dt_update/{dt_update}")
    public ResponseEntity<?> delete(@RequestBody WeightMeasurementDto weightMeasurementDto,
                                    @PathVariable("id_profile") Long idProfile,
                                    @PathVariable("id_weight") Long idWeight,
                                    @PathVariable("dt_update") Long dtUpdate) {
        if (Boolean.TRUE.equals(profileCheck(idProfile,idWeight))) {
            try {
                weightMeasurementDto.setUpdateDate(timeUtil.microsecondsToLocalDateTime(dtUpdate));
                weightMeasurementService.delete(weightMeasurementDto, idWeight);
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
            Long trainingProfileId = weightMeasurementService.get(idActive).getProfile().getId();
            return userHolderId == userProfileId && Objects.equals(trainingProfileId, idProfile);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }


    private Boolean profileCheck(Long idProfile) {
        try {
            User currentUser = userHolder.getUser();
            if (currentUser.getRole().equals(ERole.ROLE_ADMIN)) {
                return true;
            }
            return userHolder.getUser().getId() == profileService.findById(idProfile).getUser().getId();
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
