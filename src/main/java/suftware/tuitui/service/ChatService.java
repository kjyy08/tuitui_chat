package suftware.tuitui.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import suftware.tuitui.dto.request.MessageRequestDto;
import suftware.tuitui.redis.RedisPublisher;

@RequiredArgsConstructor
@Service
public class ChatService {
    private final RedisPublisher redisPublisher;

    public void sendMessage(MessageRequestDto messageRequestDto){
        redisPublisher.publish(messageRequestDto);
    }
}
