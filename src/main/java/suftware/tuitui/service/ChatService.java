package suftware.tuitui.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import suftware.tuitui.dto.request.MessageRequestDto;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ChatService {
    private final RedisPublisher redisPublisher;

    public void sendMessage(MessageRequestDto messageRequestDto){
        redisPublisher.publish(messageRequestDto);

    }
}
