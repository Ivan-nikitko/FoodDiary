package by.it_academy.food_diary.service;


import by.it_academy.food_diary.controller.dto.LoginDto;
import by.it_academy.food_diary.controller.dto.ProfileDto;
import by.it_academy.food_diary.controller.dto.UserDto;
import by.it_academy.food_diary.dao.api.IProfileDao;
import by.it_academy.food_diary.dao.api.IUserDao;
import by.it_academy.food_diary.models.Profile;
import by.it_academy.food_diary.models.User;
import by.it_academy.food_diary.models.api.ERole;
import by.it_academy.food_diary.models.api.EStatus;
import by.it_academy.food_diary.service.api.IProfileService;
import by.it_academy.food_diary.service.api.IUserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.OptimisticLockException;
import java.time.LocalDateTime;

@Service
public class UserService implements IUserService {
    private final IUserDao userDao;
    private final IProfileService profileService;
    private final PasswordEncoder passwordEncoder;

    public UserService(IUserDao userDao, IProfileService profileService, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.profileService = profileService;
        this.passwordEncoder = passwordEncoder;
    }


    public void save(LoginDto loginDto) {
        User user = new User();
        user.setLogin(loginDto.getLogin());
        user.setRole(ERole.ROLE_USER);
        user.setStatus(EStatus.INACTIVE);
        user.setPassword(passwordEncoder.encode(loginDto.getPassword()));
        User savedUser = userDao.save(user);
        ProfileDto profileDto = new ProfileDto();
        profileDto.setUser(savedUser);
        profileService.save(profileDto);
    }

    public void update(UserDto userDto, Long id) {
        User userToUpdate = findById(id);
        if (userToUpdate == null) {
            throw new IllegalArgumentException("User not found");
        }
        if (userDto.getUpdateDate().isEqual(userToUpdate.getUpdateDate())) {
            userToUpdate.setName(userDto.getName());
            userToUpdate.setLogin(userDto.getLogin());
            userToUpdate.setPassword(passwordEncoder.encode(userDto.getPassword()));
            userToUpdate.setRole(userDto.getRole());
            userToUpdate.setStatus((userDto.getStatus()));
            userDao.saveAndFlush(userToUpdate);
            userDto.setId(id);
        } else {
            throw new OptimisticLockException("User has already been changed");
        }
    }


    public User findByLogin(String login) {
        return userDao.findByLogin(login);
    }


    public User findByLoginAndPassword(String login, String password) {
        User user = userDao.findByLogin(login);
        if (user != null) {
            if (passwordEncoder.matches(password, user.getPassword())) {
                return user;
            }
        }
        return null;
    }

    public Page<User> getAll(Pageable pageable) {
        return userDao.findAll(pageable);
    }

    @Override
    public User findById(Long id) {
        return userDao.findById(id).orElseThrow(
                () -> new IllegalArgumentException("User not found")
        );
    }

    public void activateUser(Long id){
        User user = findById(id);
        user.setStatus(EStatus.ACTIVE);
        user.setUpdateDate(LocalDateTime.now());
        userDao.saveAndFlush(user);
    }

}
