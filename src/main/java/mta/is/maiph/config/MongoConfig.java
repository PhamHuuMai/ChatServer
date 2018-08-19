package mta.is.maiph.config;

import mta.is.maiph.repository.ConversationRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
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
