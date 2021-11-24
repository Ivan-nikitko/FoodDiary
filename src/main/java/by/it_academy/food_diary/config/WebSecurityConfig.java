package by.it_academy.food_diary.config;


import by.it_academy.food_diary.controller.filters.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtFilter jwtFilter;


    WebSecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        final String API_PRODUCT_PATH = "/api/product/**";
        final String API_RECIPE_PATH = "/api/recipe/**";
        final String API_PROFILE_PATH = "/api/profile/**";
        final String API_USER_PATH = "/api/user/**";
        final String ADMIN = "ADMIN";
        final String USER = "USER";

        http
                .httpBasic().disable()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, API_PRODUCT_PATH, API_RECIPE_PATH, API_PROFILE_PATH).hasAnyRole(ADMIN, USER)
                .antMatchers(HttpMethod.POST, API_PRODUCT_PATH).hasRole(ADMIN)
                .antMatchers(API_USER_PATH).hasRole(ADMIN)
                .antMatchers(HttpMethod.PUT, API_PRODUCT_PATH).hasRole(ADMIN)
                .antMatchers(HttpMethod.DELETE, API_PRODUCT_PATH).hasRole(ADMIN)
                .antMatchers(HttpMethod.POST, API_RECIPE_PATH).hasAnyRole(ADMIN, USER)
                .antMatchers(HttpMethod.PUT, API_RECIPE_PATH, API_USER_PATH).hasAnyRole(ADMIN, USER)
                .antMatchers(HttpMethod.DELETE, API_RECIPE_PATH).hasAnyRole(ADMIN, USER)
                .antMatchers("/register", "/auth", "/activate/**").anonymous()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}