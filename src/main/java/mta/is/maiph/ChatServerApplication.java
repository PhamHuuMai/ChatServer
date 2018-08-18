package mta.is.maiph;

import mta.is.maiph.worker.DirectMessageWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChatServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChatServerApplication.class, args);
        (new DirectMessageWorker()).start();
    }
}
