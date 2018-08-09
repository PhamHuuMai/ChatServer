package mta.is.maiph.config;

import mta.is.maiph.websocket.handler.MessageReceiver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 *
 * @author MaiPH
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer{

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        System.out.println("======================== registry ");
        registry.addHandler(new MessageReceiver(),"/chat").setAllowedOrigins("*");
    }
    
}
