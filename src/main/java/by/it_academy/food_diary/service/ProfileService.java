package by.it_academy.food_diary.service;

import by.it_academy.food_diary.controller.dto.ProfileDto;
import by.it_academy.food_diary.dao.api.IProfileDao;
import by.it_academy.food_diary.models.Audit;
import by.it_academy.food_diary.models.Profile;
import by.it_academy.food_diary.service.api.IProfileService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.OptimisticLockException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProfileService implements IProfileService {

    private final IProfileDao profileDao;
    private  ModelMapper modelMapper;

    public ProfileService(IProfileDao profileDao, ModelMapper modelMapper) {
        this.profileDao = profileDao;
        this.modelMapper = modelMapper;
    }

    @Override
    public void save(ProfileDto profileDto) {
        Profile profile = modelMapper.map(profileDto, Profile.class);
        profile.setCreationDate(LocalDateTime.now());
        profile.setUpdateDate(profile.getCreationDate());
        profileDao.save(profile);
    }

    @Override
    public List<Profile> getAll() {
        return profileDao.findAll();
    }

    public List<Profile> getAllByUserId(Long id) {
        return profileDao.findAllByUserId(id) ;
    }

    @Override
    public Profile findById(Long id) {
        return profileDao.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Profile not found")
        );
    }

    @Override
    public void update(ProfileDto profileDto, Long id) {
        Profile profileToUpdate = findById(id);
        if (profileDto.getUpdateDate().isEqual(profileToUpdate.getUpdateDate())) {
            profileToUpdate.setHeight(profileDto.getHeight());
            profileToUpdate.setWeight(profileDto.getWeight());
            profileToUpdate.setSex(profileDto.getSex());
            profileToUpdate.setActivity(profileDto.getActivity());
            profileToUpdate.setPurpose(profileDto.getPurpose());
            profileToUpdate.setDateOfBirth(profileDto.getDateOfBirth());
            profileToUpdate.setUpdateDate(LocalDateTime.now());
            profileDao.saveAndFlush(profileToUpdate);
        } else {
            throw new OptimisticLockException("Profile has already been changed");
        }
    }

}


