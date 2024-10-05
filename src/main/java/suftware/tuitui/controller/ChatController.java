package suftware.tuitui.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;
import suftware.tuitui.dto.request.MessageRequestDto;
import suftware.tuitui.dto.response.ChatRoomResponseDto;
import suftware.tuitui.service.ChatService;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ChatController {
    private final ChatService chatService;

    //  메시지 발행
    @MessageMapping("/chat/message")
    public void sendMessage(MessageRequestDto messageRequestDto){
        log.info("ChatController.sendMessage() -> sender: {}, receiver: {}, message: {}",
                messageRequestDto.getSenderProfileId(), messageRequestDto.getReceiverProfileId(),messageRequestDto.getContent());
        chatService.sendMessage(messageRequestDto);
    }
}
