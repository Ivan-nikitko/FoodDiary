package by.it_academy.food_diary.service;

import by.it_academy.food_diary.controller.dto.LoginDto;
import by.it_academy.food_diary.controller.dto.UserDto;
import by.it_academy.food_diary.dao.api.IUserDao;
import by.it_academy.food_diary.models.User;
import by.it_academy.food_diary.service.api.IUserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {
    private final IUserDao userDao;
    private final PasswordEncoder passwordEncoder;

    public UserService(IUserDao userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }



    @Override
    public User get(Long id) {
        return userDao.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Recipe not found"));
    }

    @Override
    public void update(UserDto userDto, Long id) {

    }


    @Override
    public User findByLogin(String login) {
        return userDao.findByLogin(login);
    }

    @Override
    public void save(LoginDto loginDto) {
        User user = new User();
        user.setLogin(loginDto.getLogin());
        user.setPassword(passwordEncoder.encode(loginDto.getPassword()));
        userDao.save(user);
    }

    public void  save (User user){
        userDao.save(user);
    }

    @Override
    public Page<User> getAll(Pageable pageable) {
        userDao.findAll(pageable);
        return null;
    }
}
