package by.it_academy.food_diary.service;

import by.it_academy.food_diary.controller.dto.ProfileDto;
import by.it_academy.food_diary.dao.api.IProfileDao;
import by.it_academy.food_diary.models.Profile;
import by.it_academy.food_diary.service.api.IProfileService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProfileService implements IProfileService {

    private IProfileDao profileDao;
    private final ModelMapper modelMapper;

    public ProfileService(IProfileDao profileDao, ModelMapper modelMapper) {
        this.profileDao = profileDao;
        this.modelMapper = modelMapper;
    }

    @Override
    public void save(ProfileDto profileDto) {
        Profile profile = modelMapper.map(profileDto, Profile.class);
        profileDao.save(profile);

    }

    @Override
    public Page<Profile> getAll(Pageable pageable) {
        return null;
    }

    @Override
    public Profile findById(Long id) {
        return profileDao.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Profile not found")
        );
    }

    @Override
    public void update(ProfileDto profileDto, Long id) {

    }

}
