package by.it_academy.food_diary.service.api;


import by.it_academy.food_diary.controller.dto.ProfileDto;
import by.it_academy.food_diary.models.Profile;



import java.util.List;

public interface IProfileService  {


    void save(ProfileDto profileDto);
    List<Profile> getAll ();
    Profile findById (Long id);
    List<Profile> getAllByUserId(Long id);
    void update(ProfileDto profileDto, Long id);
}
