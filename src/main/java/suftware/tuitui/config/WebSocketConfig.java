package suftware.tuitui.config;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    private static final Logger log = LoggerFactory.getLogger(WebSocketConfig.class);

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config){
        //  구독 채널 설정
        config.enableSimpleBroker("/sub");
        //  메시지 전송 설정
        config.setApplicationDestinationPrefixes("/pub");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // WebSocket 엔드포인트 설정 및 SockJS 사용
        registry.addEndpoint("/ws-stomp").setAllowedOriginPatterns("*").withSockJS();

        log.info("WebSocket endpoint registered");
    }
}

