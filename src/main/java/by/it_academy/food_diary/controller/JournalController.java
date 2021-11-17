package by.it_academy.food_diary.controller;

import by.it_academy.food_diary.controller.dto.JournalByDateDto;
import by.it_academy.food_diary.models.Journal;

import by.it_academy.food_diary.models.Profile;
import by.it_academy.food_diary.service.api.IJournalService;
import by.it_academy.food_diary.service.api.IProfileService;
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
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoField;


@RestController
@RequestMapping("/api/profile")
public class JournalController {

    private final IJournalService journalService;
    private final IProfileService profileService;
    private final int MILLISECOND_IN_DAY = 86400000;

    public JournalController(IJournalService journalService, IProfileService profileService) {
        this.journalService = journalService;
        this.profileService = profileService;
    }

    @GetMapping("/{id_profile}/journal/food/")
    public ResponseEntity<Page<Journal>> index(@PathVariable("id_profile") Long id_profile,
                                               @RequestParam(value = "page", defaultValue = "0") int page,
                                               @RequestParam(value = "size", defaultValue = "2") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        //TODO create check by idProfile
        Page<Journal> journals = journalService.getAllByProfileId(pageable, id_profile);
        return new ResponseEntity<>(journals, HttpStatus.OK);
    }

    @GetMapping("/{id_profile}/journal/food/byDay/{day}")
    public ResponseEntity<?> showDay(@PathVariable("id_profile") Long idProfile,
                                     @PathVariable("day") Long day) {
        try {
            Long dayInMilliseconds = day*MILLISECOND_IN_DAY;
            LocalDateTime date =
                    LocalDateTime.ofInstant(Instant.ofEpochMilli(dayInMilliseconds), ZoneId.systemDefault());
            LocalDateTime endOfDate = date.with(ChronoField.NANO_OF_DAY, LocalTime.MAX.toNanoOfDay());
            LocalDateTime startOfDate = endOfDate.minusDays(1L);

            JournalByDateDto journalByDateDto = journalService.findAllByProfileIdAndCreationDate(startOfDate, endOfDate, idProfile);
            return new ResponseEntity<>(journalByDateDto, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id_profile}/journal/food/{id_food}")
    public ResponseEntity<?> show(@PathVariable("id_profile") Long id_profile,
                                  @PathVariable("id_food") Long id_food,
                                  @PathVariable("day") Integer day) {
        try {
            Journal journal = journalService.get(id_food);
            return new ResponseEntity<>(journal, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{id_profile}/journal/food")
    public ResponseEntity<?> save(@RequestBody Journal journal,
                                  @PathVariable("id_profile") Long id_profile) {
        Profile profile = profileService.get(id_profile);
        journal.setProfile(profile);
        journal.setCreationDate(LocalDateTime.now());
        journal.setUpdateDate(journal.getCreationDate());
        journalService.save(journal);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id_profile}/journal/food/{id_food}/dt_update/{dt_update}")
    public ResponseEntity<?> update(@RequestBody Journal journal,
                                    @PathVariable("id_profile") Long id_profile,
                                    @PathVariable("id_food") Long id_food,
                                    @PathVariable("dt_update") String dt_update) {
        try {
            journal.setUpdateDate(LocalDateTime.parse(dt_update));
            journalService.update(journal, id_food);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (OptimisticLockException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/{id_profile}/journal/food/{id_food}/dt_update/{dt_update}")
    public ResponseEntity<?> delete(@RequestBody Journal journal,
                                    @PathVariable("id_profile") Long id_profile,
                                    @PathVariable("id_food") Long id_food,
                                    @PathVariable("dt_update") String dt_update) {
        try {
            journal.setUpdateDate(LocalDateTime.parse(dt_update));
            journalService.delete(journal, id_food);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
