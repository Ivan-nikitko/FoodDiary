package by.it_academy.food_diary.service;

import by.it_academy.food_diary.dao.api.IProfileDao;
import by.it_academy.food_diary.models.Profile;
import by.it_academy.food_diary.service.api.IProfileService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProfileService implements IProfileService {

    private IProfileDao profileDao;

    public ProfileService(IProfileDao profileDao) {
        this.profileDao = profileDao;
    }

    @Override
    public void save(Profile item) {

    }

    @Override
    public Page<Profile> getAll(Pageable pageable) {
        return null;
    }

    @Override
    public Profile get(Long id) {
        return profileDao.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Profile not found")
        );
    }

    @Override
    public void update(Profile item, Long aLong) {

    }

    @Override
    public void delete(Profile profile,Long aLong) {

    }
}
