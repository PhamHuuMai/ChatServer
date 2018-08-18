package mta.is.maiph.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author MaiPH
 */
@Configuration
public class MongoConfig {
    
    @Value("${spring.data.mongodb.uri}")
    public static String URI ; 
}
