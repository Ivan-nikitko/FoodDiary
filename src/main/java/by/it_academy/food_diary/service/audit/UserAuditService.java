
package by.it_academy.food_diary.service.audit;

import by.it_academy.food_diary.controller.dto.LoginDto;
import by.it_academy.food_diary.controller.dto.UserDto;
import by.it_academy.food_diary.models.Audit;
import by.it_academy.food_diary.models.User;
import by.it_academy.food_diary.security.UserHolder;
import by.it_academy.food_diary.service.api.IAuditService;
import by.it_academy.food_diary.service.api.IUserService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Aspect
@Service
public class UserAuditService {

    private final IAuditService auditService;
    private final IUserService userService;
    private final UserHolder userHolder;

    public UserAuditService(IAuditService auditService, IUserService userService, UserHolder userHolder) {
        this.auditService = auditService;
        this.userService = userService;
        this.userHolder = userHolder;
    }

    @AfterReturning("execution(* by.it_academy.food_diary.service.UserService.save(..))")
    public void save(JoinPoint joinPoint) {
     createLoginAudit(joinPoint);
    }

    @AfterReturning("execution(* by.it_academy.food_diary.service.UserService.update(..))")
    public void update(JoinPoint joinPoint) {
       createUpdateAudit(joinPoint);
    }

    private void createLoginAudit(JoinPoint joinPoint) {
        try {
            Object[] args = joinPoint.getArgs();
            LoginDto loginDto = (LoginDto) args[0];
            Audit audit = new Audit();
            audit.setCreationDate(LocalDateTime.now());
            User user = userService.findByLogin(loginDto.getLogin());
            audit.setDescription("Create user " + user.getId());
            audit.setUser(user);
            audit.setEssenceName("User");
            audit.setEssenceId(user.getId());
            auditService.save(audit);
        } catch (Throwable e) {
            throw new RuntimeException("error with audit");
        }
    }

    private void createUpdateAudit(JoinPoint joinPoint) {
        try {
            Object[] args = joinPoint.getArgs();
            UserDto userDto = (UserDto) args[0];
            Audit audit = new Audit();
            audit.setCreationDate(LocalDateTime.now());
            audit.setDescription("Update user " + userDto.getId());
            String login = userHolder.getAuthentication().getName();
            User user = userService.findByLogin(login);
            audit.setUser(user);
            audit.setEssenceName("User");
            audit.setEssenceId(userDto.getId());
            auditService.save(audit);
        } catch (Throwable e) {
            throw new RuntimeException("error with audit");
        }
    }
}


