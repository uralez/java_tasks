package com.ayakovlev.interviewprep.config;

import com.ayakovlev.interviewprep.repository.StudentRepository;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.util.Locale;

@Configuration
public class LocaleConfig implements WebMvcConfigurer {

    private final StudentRepository studentRepository;

    public LocaleConfig(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }

    /**
     Defines HOW the app remembers the user's chosen language between requests.
     SessionLocaleResolver stores it in the HTTP session (server-side memory
     tied to the browser session) — so it persists across pages, but is lost
     if the session ends (browser closed, session expired, etc).

     Определяет, КАК приложение запоминает выбранный язык между запросами.
     SessionLocaleResolver хранит его в HTTP-сессии (серверная память,
     привязанная к сессии браузера) — сохраняется между страницами,
     но теряется при завершении сессии (закрытие браузера, истечение сессии и т.д.)
    */
    @Bean
    public LocaleResolver localeResolver() {
        CookieLocaleResolver resolver = new CookieLocaleResolver();
        resolver.setDefaultLocale(Locale.ENGLISH);
        return resolver;
    }

    /**
     Watches incoming requests for a URL parameter named "lang"
     (e.g. ?lang=ru). When found, it tells the LocaleResolver above
     to switch the current locale to that value.

     Отслеживает входящие запросы на наличие параметра "lang" в URL
     (например, ?lang=ru). Когда находит его — указывает LocaleResolver
     выше переключить текущую локаль на это значение.
     */
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
        interceptor.setParamName("lang");
        return interceptor;
    }

    /**
     Registers the interceptor above so Spring MVC actually runs it
     on every incoming request. Without this, the interceptor would
     exist as a bean but never be triggered.

     Регистрирует interceptor выше, чтобы Spring MVC реально запускал
     его на каждом входящем запросе. Без этого interceptor существовал
     бы как bean, но никогда бы не срабатывал.
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(localeChangeInterceptor());
        registry.addInterceptor(new PersistLocaleInterceptor(studentRepository));
    }

    /**
     Configures Bean Validation (e.g. @NotNull, @Size on your entities/DTOs)
     to pull its error messages from the same messages.properties files
     used for UI translations — so validation errors are also localized
     (shown in the user's chosen language), not just hardcoded in English.

     Настраивает Bean Validation (например, @NotNull, @Size на сущностях/DTO)
     брать сообщения об ошибках из тех же файлов messages.properties,
     что используются для перевода интерфейса — так ошибки валидации
     тоже локализуются (показываются на выбранном языке), а не захардкожены
     на английском.
     */
    @Bean
    public LocalValidatorFactoryBean validator(MessageSource messageSource){
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource);
        return bean;
    }
}
