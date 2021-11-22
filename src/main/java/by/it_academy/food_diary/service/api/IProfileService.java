package by.it_academy.food_diary.service.api;


import by.it_academy.food_diary.controller.dto.ProfileDto;
import by.it_academy.food_diary.models.Profile;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IProfileService  {


    void save(ProfileDto profileDto);
    Page<Profile> getAll (Pageable pageable);
    Profile findById (Long id);
    void update(ProfileDto profileDto, Long id);
}
