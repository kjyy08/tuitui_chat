package suftware.tuitui.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import suftware.tuitui.dto.request.MessageRequestDto;

@Service
@RequiredArgsConstructor
@Slf4j
public class RedisSubscriber{
    private final ObjectMapper objectMapper;
    private final SimpMessageSendingOperations messageTemplate;

    //  redis pub에서 발행된 메시지를 가져옴
    public void handleMessage(String message){
        try{
            log.info("Received message from Redis: {}", message);
            MessageRequestDto messageRequestDto = objectMapper.readValue(message, MessageRequestDto.class);

            messageTemplate.convertAndSend("/sub/chat/room/" + messageRequestDto.getRoomId(), messageRequestDto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
