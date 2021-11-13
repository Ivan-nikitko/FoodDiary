package by.it_academy.food_diary.service;

import by.it_academy.food_diary.dao.api.IUserDao;
import by.it_academy.food_diary.models.User;
import by.it_academy.food_diary.service.api.IUserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {
    private final IUserDao userDao;

    public UserService(IUserDao userDao) {
        this.userDao = userDao;
    }


    public void save(User user) {
        userDao.save(user);
    }

    @Override
    public Page<User> getAll(Pageable pageable) {
        return null;
    }

    public List<User> getAll() {
        return null;
    }

    @Override
    public User get(Long id) {
        return null;
    }


    @Override
    public void update(User updatedUser, Long id) {

    }

    @Override
    public void delete(User user,Long id) {

    }

    @Override
    public User findByLogin(String login) {
        return userDao.findByLogin(login);
    }
}
