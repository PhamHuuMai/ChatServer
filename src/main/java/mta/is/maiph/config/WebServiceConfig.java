package mta.is.maiph.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 *
 * @author MaiPH
 */
@Configuration
@EnableWebMvc
public class WebServiceConfig implements WebMvcConfigurer {
 
    @Override
    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**").allowedOrigins("https://chat-static-server.herokuapp.com","http://127.0.0.1:8080",);
            registry.addMapping("/**").allowedOrigins("*");
    }
}  
