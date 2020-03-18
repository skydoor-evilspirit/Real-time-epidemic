package xyz.egaz.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import xyz.egaz.component.MyLocalResolver;

@Configuration
public class MyConfig {
    @Bean("localeResolver")
    public LocaleResolver myLocalResolver(){
        return new MyLocalResolver();
    }

}
