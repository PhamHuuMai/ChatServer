package mta.is.maiph;

import mta.is.maiph.repository.ConversationRepository;
import mta.is.maiph.repository.UserRepository;
import mta.is.maiph.worker.DirectMessageWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChatServerApplication {

    @Autowired
    static ConversationRepository conversationRepository;
    @Autowired
    static UserRepository userRepository;
    
    public static void main(String[] args) {
        SpringApplication.run(ChatServerApplication.class, args);
        (new DirectMessageWorker(conversationRepository,userRepository)).start();
    }
}
