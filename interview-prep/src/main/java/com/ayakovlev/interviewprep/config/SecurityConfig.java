package com.ayakovlev.interviewprep.config;

import com.ayakovlev.interviewprep.entity.Role;
import com.ayakovlev.interviewprep.entity.Student;
import com.ayakovlev.interviewprep.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Spring Security configuration.
 *
 * @EnableWebSecurity - activates Spring Security for web requests.
 * @EnableMethodSecurity - enables @PreAuthorize annotations on service methods.
 *
 * @Configuration
 * Говорит Spring что этот класс является источником бинов — то есть методы помеченные @Bean внутри этого класса будут
 * зарегистрированы в Spring-контексте. Это стандартная аннотация Spring, не связанная с Security.
 *
 * @EnableWebSecurity
 * Говорит Spring: активируй Spring Security для веб-запросов. Без этой аннотации Security не будет перехватывать
 * HTTP-запросы и проверять авторизацию. Именно она включает механизм SecurityFilterChain — цепочку фильтров, которая
 * проверяет каждый входящий запрос.
 *
 * @EnableMethodSecurity
 * Говорит Spring: разреши использование аннотаций безопасности на методах. После этого можно писать на сервисных методах:
 * @PreAuthorize("hasRole('ADMIN')")
 * public void deleteStudent(Long id) { ... }
 * @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
 * public void updateAnswer(Long id, String text) { ... }
 * Без этой аннотации @PreAuthorize просто игнорируется — метод будет доступен всем.
 */

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final StudentService studentService;
    private final PasswordEncoder passwordEncoder;

    /**
     * Defines which URLs are accessible without authentication
     * and configures login/logout behaviour.
     *
     * Когда браузер отправляет запрос к вашему приложению — он не попадает сразу в контроллер. Сначала он проходит
     * через цепочку фильтров по очереди:
     * Браузер → Запрос
     *               ↓
     *          Фильтр 1: является ли пользователь залогиненным?
     *               ↓
     *          Фильтр 2: есть ли у него права на этот URL?
     *               ↓
     *          Фильтр 3: не истекла ли сессия?
     *               ↓
     *          Контроллер
     * Если какой-то фильтр решает что запрос не должен пройти дальше — он перенаправляет пользователя на страницу
     * логина или возвращает ошибку 403.
     *
     * @return
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authenticationProvider(authenticationProvider())
                // Функциональный интерфейс: Customizer<AuthorizeHttpRequestsConfigurer.AuthorizationManagerRequestMatcherRegistry>
                // SAM: void customize(C configurer);
                .authorizeHttpRequests(auth -> auth // фильтр проверки прав доступа
                        .requestMatchers(
                                "/login",
                                "/register",
                                "/demo/login", // Разрешить доступ к /demo/login без аутентификации
                                "/css/**",
                                "/js/**"
                        ).permitAll() // доступны всем без авторизации
                        .requestMatchers("/admin/**").hasRole("ADMIN") // /admin/** — только пользователям с ролью ADMIN
                        .anyRequest().authenticated() // все остальные URL — только залогиненным пользователям
                )
                // Функциональный интерфейс: Customizer<FormLoginConfigurer<HttpSecurity>>
                //SAM: void customize(C configurer);
                .formLogin(form -> form.loginPage("/login") // фильтр обработки логина // страница логина находится по адресу /login
                        .defaultSuccessUrl("/", true) // после успешного логина — редирект на /
                        .permitAll()) // сама страница /login доступна всем
                // Функциональный интерфейса: Customizer<LogoutConfigurer<HttpSecurity>>
                //SAM: void customize(C configurer);
                .logout(logout -> logout // фильтр обработки логаута
                        .logoutSuccessUrl("/login?logout") // после выхода — редирект на /login?logout
                        // параметр ?logout можно использовать в Thymeleaf, чтобы показать сообщение "Вы вышли из системы"
                        .permitAll()
                );
        return httpSecurity.build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(studentService);
        provider.setPasswordEncoder(passwordEncoder);
        // FI: UserDetailsChecker
        // SAM: void check(UserDetails userDetails)
        provider.setPreAuthenticationChecks(userDetails -> {
            Student student = (Student) userDetails;
            if (student.getRole() == Role.DEMO_TEMPLATE) {
                throw new DisabledException("DEMO_TEMPLATE accounts cannot log in");
            }
        });
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
        return config.getAuthenticationManager();
    }

}
