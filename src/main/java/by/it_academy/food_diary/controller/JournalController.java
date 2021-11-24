package by.it_academy.food_diary.controller;

import by.it_academy.food_diary.controller.dto.JournalByDateDto;
import by.it_academy.food_diary.controller.dto.JournalDto;
import by.it_academy.food_diary.controller.validation.ProfileValidator;
import by.it_academy.food_diary.models.Journal;
import by.it_academy.food_diary.models.User;
import by.it_academy.food_diary.models.api.ERole;
import by.it_academy.food_diary.security.UserHolder;
import by.it_academy.food_diary.service.api.IJournalService;
import by.it_academy.food_diary.service.api.IProfileService;
import by.it_academy.food_diary.utils.TimeUtil;
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
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoField;
import java.util.Objects;
import java.util.concurrent.TimeUnit;


@RestController
@RequestMapping("/api/profile")
public class JournalController {

    private final IJournalService journalService;
    private final IProfileService profileService;
    private final UserHolder userHolder;
    private final TimeUtil timeUtil;
    private final ProfileValidator profileValidator;

    public JournalController(IJournalService journalService, IProfileService profileService, UserHolder userHolder, TimeUtil timeUtil, ProfileValidator profileValidator) {
        this.journalService = journalService;
        this.profileService = profileService;
        this.userHolder = userHolder;
        this.timeUtil = timeUtil;
        this.profileValidator = profileValidator;
    }

    @GetMapping("/{id_profile}/journal/food/")
    public ResponseEntity<?> index(@PathVariable("id_profile") Long idProfile,
                                   @RequestParam(value = "page", defaultValue = "0") int page,
                                   @RequestParam(value = "size", defaultValue = "2") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        try {
            if (profileValidator.profileCheck(idProfile)) {
                Page<Journal> journals = journalService.getAllByProfileId(pageable, idProfile);
                return new ResponseEntity<>(journals, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id_profile}/journal/food/{id_food}")
    public ResponseEntity<?> show(@PathVariable("id_profile") Long idProfile,
                                  @PathVariable("id_food") Long idFood) {
        if (profileValidator.profileCheck(idProfile)) {
            try {
                Journal journal = journalService.get(idFood);
                return new ResponseEntity<>(journal, HttpStatus.OK);
            } catch (IllegalArgumentException e) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/{id_profile}/journal/food/byDay/{day}")
    public ResponseEntity<?> showDay(@PathVariable("id_profile") Long idProfile,
                                     @PathVariable("day") Long day) {

        if (profileValidator.profileCheck(idProfile)) {

            long dayInMilliseconds = TimeUnit.DAYS.toMillis(day);
            LocalDateTime date =
                    LocalDateTime.ofInstant(Instant.ofEpochMilli(dayInMilliseconds), ZoneId.systemDefault());
            LocalDateTime endOfDate = date.with(ChronoField.NANO_OF_DAY, LocalTime.MAX.toNanoOfDay());
            LocalDateTime startOfDate = endOfDate.minusDays(1L);

            JournalByDateDto journalByDateDto = journalService.findAllByProfileIdAndCreationDate(startOfDate, endOfDate, idProfile);
            return new ResponseEntity<>(journalByDateDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/{id_profile}/journal/food")
    public ResponseEntity<?> save(@RequestBody JournalDto journalDto,
                                  @PathVariable("id_profile") Long idProfile) {
        try {
            if (profileValidator.profileCheck(idProfile)) {
                journalDto.setProfile(profileService.findById(idProfile));
                journalService.save(journalDto);
                return new ResponseEntity<>(HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        }catch (IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id_profile}/journal/food/{id_food}/dt_update/{dt_update}")
    public ResponseEntity<?> update(@RequestBody JournalDto journalDto,
                                    @PathVariable("id_profile") Long idProfile,
                                    @PathVariable("id_food") Long idFood,
                                    @PathVariable("dt_update") Long dtUpdate) {
        if (Boolean.TRUE.equals(profileCheck(idProfile,idFood))) {
            try {
                journalDto.setUpdateDate(timeUtil.microsecondsToLocalDateTime(dtUpdate));
                journalService.update(journalDto, idFood);
                return new ResponseEntity<>(HttpStatus.OK);
            } catch (OptimisticLockException e) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
            } catch (IllegalArgumentException e) {
                return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("/{id_profile}/journal/food/{id_food}/dt_update/{dt_update}")
    public ResponseEntity<?> delete(@RequestBody JournalDto journalDto,
                                    @PathVariable("id_profile") Long idProfile,
                                    @PathVariable("id_food") Long idFood,
                                    @PathVariable("dt_update") Long dtUpdate) {
        if (Boolean.TRUE.equals(profileCheck(idProfile,idFood))) {
            try {
                journalDto.setUpdateDate(timeUtil.microsecondsToLocalDateTime(dtUpdate));
                journalService.delete(journalDto, idFood);
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


    private Boolean profileCheck(Long idProfile, Long idFood) {
        try {
            User currentUser = userHolder.getUser();
            if (currentUser.getRole().equals(ERole.ROLE_ADMIN)) {
                return true;
            }
            long userHolderId = currentUser.getId();
            long userProfileId = profileService.findById(idProfile).getUser().getId();
            Long journalProfileId = journalService.get(idFood).getProfile().getId();
            return userHolderId == userProfileId && Objects.equals(journalProfileId, idProfile);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

}
