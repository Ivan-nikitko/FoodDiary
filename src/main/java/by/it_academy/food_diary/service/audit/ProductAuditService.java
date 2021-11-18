package by.it_academy.food_diary.service.audit;

import by.it_academy.food_diary.models.Audit;
import by.it_academy.food_diary.models.Product;
import by.it_academy.food_diary.models.User;
import by.it_academy.food_diary.security.UserHolder;
import by.it_academy.food_diary.service.api.IAuditService;
import by.it_academy.food_diary.service.api.IUserService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Service;

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

    @After("execution(* by.it_academy.food_diary.service.ProductService.save(..))")
    public void save(JoinPoint joinPoint) {
        try {
            Object[] args = joinPoint.getArgs();

            Product arg = (Product) args[0];

            Audit audit = new Audit();
            audit.setCreationDate(arg.getUpdateDate());
            audit.setDescription("Creation product  " + arg.getId());
            String login = userHolder.getAuthentication().getName();
            User user = userService.findByLogin(login);
            audit.setUser(user);
            audit.setEssenceName("Product");
            audit.setEssenceId(arg.getId());
            auditService.save(audit);

        } catch (Throwable e) {
            throw new RuntimeException("error with audit");
        }
    }

    @After("execution(* by.it_academy.food_diary.service.ProductService.update(..))")
    public void update(JoinPoint joinPoint) {
        try {
            Object[] args = joinPoint.getArgs();

            Product arg = (Product) args[0];

            Audit audit = new Audit();
            audit.setCreationDate(arg.getUpdateDate());
            audit.setDescription("Update product  " + arg.getId());
            String login = userHolder.getAuthentication().getName();
            User user = userService.findByLogin(login);
            audit.setUser(user);
            audit.setEssenceName("Product");
            audit.setEssenceId(arg.getId());
            auditService.save(audit);

        } catch (Throwable e) {
            throw new RuntimeException("error with audit");
        }
    }


    @After("execution(* by.it_academy.food_diary.service.ProductService.delete(..))")
    public void delete(JoinPoint joinPoint) {
        try {
            Object[] args = joinPoint.getArgs();

            Product arg = (Product) args[0];

            Audit audit = new Audit();
            audit.setCreationDate(arg.getUpdateDate());
            audit.setDescription("Delete product  " + arg.getId());
            String login = userHolder.getAuthentication().getName();
            User user = userService.findByLogin(login);
            audit.setUser(user);
            audit.setEssenceName("Product");
            audit.setEssenceId(arg.getId());
            auditService.save(audit);

        } catch (Throwable e) {
            throw new RuntimeException("error with audit");
        }
    }
}
