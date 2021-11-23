package by.it_academy.food_diary.service.audit;

import by.it_academy.food_diary.controller.dto.ProductDto;
import by.it_academy.food_diary.controller.dto.RecipeDto;
import by.it_academy.food_diary.models.Audit;
import by.it_academy.food_diary.models.User;
import by.it_academy.food_diary.security.UserHolder;
import by.it_academy.food_diary.service.api.IAuditService;
import by.it_academy.food_diary.service.api.IUserService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Aspect
@Service
public class RecipeAuditService {

    private final IAuditService auditService;
    private final IUserService userService;
    private final UserHolder userHolder;

    public RecipeAuditService(IAuditService auditService, IUserService userService, UserHolder userHolder) {
        this.auditService = auditService;
        this.userService = userService;
        this.userHolder = userHolder;
    }

    @AfterReturning("execution(* by.it_academy.food_diary.service.RecipeService.save(..))")
    public void save(JoinPoint joinPoint) {
     createAudit(joinPoint,"Create");
    }

    @AfterReturning("execution(* by.it_academy.food_diary.service.RecipeService.update(..))")
    public void update(JoinPoint joinPoint) {
       createAudit(joinPoint,"Update");
    }

   @AfterReturning("execution(* by.it_academy.food_diary.service.RecipeService.delete(..))")
    public void delete(JoinPoint joinPoint) {
      createAudit(joinPoint,"Delete");
    }


    private void createAudit(JoinPoint joinPoint, String method) {
        try {
            Object[] args = joinPoint.getArgs();
            RecipeDto recipeDto = (RecipeDto) args[0];
            Audit audit = new Audit();
            audit.setCreationDate(LocalDateTime.now());
            audit.setDescription(method+" recipe " + recipeDto.getId());
            String login = userHolder.getAuthentication().getName();
            User user = userService.findByLogin(login);
            audit.setUser(user);
            audit.setEssenceName("Recipe");
            audit.setEssenceId(recipeDto.getId());
            auditService.save(audit);
        } catch (Throwable e) {
            throw new RuntimeException("error with audit");
        }
    }
}