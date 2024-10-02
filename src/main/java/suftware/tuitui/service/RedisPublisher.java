package suftware.tuitui.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;
import suftware.tuitui.dto.request.MessageRequestDto;

@RequiredArgsConstructor
@Service
@Slf4j
public class RedisPublisher {
    private final ChannelTopic channelTopic;
    private final RedisTemplate<String, Object> redisTemplate;

    public void publish(MessageRequestDto messageRequestDto){
        // 로그 추가: 발행할 메시지와 채널명
        log.info("Publishing message to Redis: {}", messageRequestDto);
        log.info("Channel: {}", channelTopic.getTopic());
        redisTemplate.convertAndSend(channelTopic.getTopic(), messageRequestDto);
    }
}
