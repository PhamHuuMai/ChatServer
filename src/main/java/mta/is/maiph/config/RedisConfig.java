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
public class RedisConfig {
    
//    @Value("${spring.data.mongodb.uri}")
//    public static String URI; 
    
//    @Value("${spring.data.mongodb.host}")
    public static final String HOST = "localhost"; 

//    @Value("${spring.data.mongodb.port}")
    public static final int PORT = 6379; 
//    @Value("${spring.data.mongodb.database}")
  
}
