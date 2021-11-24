package by.it_academy.food_diary.controller.validation;

import by.it_academy.food_diary.models.User;
import by.it_academy.food_diary.models.api.ERole;
import by.it_academy.food_diary.security.UserHolder;
import by.it_academy.food_diary.service.ProfileService;
import org.springframework.stereotype.Component;

@Component
public class ProfileValidator {

    private final UserHolder userHolder;
    private final ProfileService profileService;

    public ProfileValidator(UserHolder userHolder, ProfileService profileService) {
        this.userHolder = userHolder;
        this.profileService = profileService;
    }

    public Boolean profileCheck(Long idProfile) {
        try {
            User currentUser = userHolder.getUser();
            if (currentUser.getRole().equals(ERole.ROLE_ADMIN)) {
                return true;
            }
            return currentUser.getId() == profileService.findById(idProfile).getUser().getId();
        } catch (IllegalArgumentException e) {
            return false;
        }
    }


}
