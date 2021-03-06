package by.it_academy.food_diary.service.audit;

import by.it_academy.food_diary.controller.dto.ProductDto;
import by.it_academy.food_diary.controller.dto.RecipeDto;
import by.it_academy.food_diary.models.Audit;
import by.it_academy.food_diary.models.Product;
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
public class ProductAuditService {

    private final IAuditService auditService;
    private final IUserService userService;
    private final UserHolder userHolder;

    public ProductAuditService(IAuditService auditService, IUserService userService, UserHolder userHolder) {
        this.auditService = auditService;
        this.userService = userService;
        this.userHolder = userHolder;
    }

    @AfterReturning("execution(* by.it_academy.food_diary.service.ProductService.save(..))")
    public void save(JoinPoint joinPoint) {
        createAudit(joinPoint,"Create");
    }

    @AfterReturning("execution(* by.it_academy.food_diary.service.ProductService.update(..))")
    public void update(JoinPoint joinPoint) {
        createAudit(joinPoint,"Update");
    }

   @AfterReturning("execution(* by.it_academy.food_diary.service.ProductService.delete(..))")
    public void delete(JoinPoint joinPoint) {
      createAudit(joinPoint,"Delete");
    }

    private void createAudit(JoinPoint joinPoint, String method) {
        try {
            Object[] args = joinPoint.getArgs();
            ProductDto productDto = (ProductDto) args[0];
            Audit audit = new Audit();
            audit.setCreationDate(LocalDateTime.now());
            audit.setDescription(method+" product " + productDto.getId());
            String login = userHolder.getAuthentication().getName();
            User user = userService.findByLogin(login);
            audit.setUser(user);
            audit.setEssenceName("Product");
            audit.setEssenceId(productDto.getId());
            auditService.save(audit);
        } catch (Throwable e) {
            throw new RuntimeException("error with audit");
        }
    }

}
