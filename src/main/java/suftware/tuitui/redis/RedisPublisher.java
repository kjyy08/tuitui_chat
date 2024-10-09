package suftware.tuitui.redis;

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
    //  단일 토픽 사용을 위해 bean 주입
    private final ChannelTopic channelTopic;
    private final RedisTemplate<String, Object> redisTemplate;

    public void publish(MessageRequestDto messageRequestDto){
        log.info("sender: {}, message: {}", messageRequestDto.getSenderProfileId(), messageRequestDto.getContent());
        redisTemplate.convertAndSend(channelTopic.getTopic(), messageRequestDto);
    }
}
