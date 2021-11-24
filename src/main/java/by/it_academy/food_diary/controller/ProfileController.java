package by.it_academy.food_diary.controller;

import by.it_academy.food_diary.controller.dto.ProfileDto;
import by.it_academy.food_diary.controller.validation.ProfileValidator;
import by.it_academy.food_diary.models.Profile;
import by.it_academy.food_diary.models.User;
import by.it_academy.food_diary.models.api.ERole;
import by.it_academy.food_diary.security.UserHolder;
import by.it_academy.food_diary.service.api.IProfileService;
import by.it_academy.food_diary.utils.TimeUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.OptimisticLockException;
import java.util.List;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    private final IProfileService profileService;
    private final UserHolder userHolder;
    private final TimeUtil timeUtil;
    private final ProfileValidator profileValidator;

    public ProfileController(IProfileService profileService, UserHolder userHolder, TimeUtil timeUtil, ProfileValidator profileValidator) {
        this.profileService = profileService;
        this.userHolder = userHolder;
        this.timeUtil = timeUtil;
        this.profileValidator = profileValidator;
    }

    @PutMapping("/{id}/dt_update/{dt_update}")
    public ResponseEntity<?> update(@RequestBody ProfileDto profileDto,
                                    @PathVariable("id") Long id,
                                    @PathVariable("dt_update") Long dtUpdate) {
        if (profileValidator.profileCheck(id)) {
            try {
                profileDto.setUpdateDate(timeUtil.microsecondsToLocalDateTime(dtUpdate));
                profileService.update(profileDto, id);
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

    @GetMapping
    public ResponseEntity<?> getAll() {
        User user = userHolder.getUser();
        List<Profile> profiles;
        if (user.getRole().equals(ERole.ROLE_ADMIN)) {
            profiles = this.profileService.getAll();
        } else {
            profiles = profileService.getAllByUserId(user.getId());
        }
        return new ResponseEntity<>(profiles, HttpStatus.OK);
    }
}