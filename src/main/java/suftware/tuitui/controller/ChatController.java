package suftware.tuitui.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import suftware.tuitui.dto.request.MessageRequestDto;
import suftware.tuitui.service.ChatService;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("chat/")
public class ChatController {
    private final ChatService chatService;

    @MessageMapping("message")
    public void message(MessageRequestDto messageRequestDto){
        log.info("Received message: {}", messageRequestDto);
        chatService.sendMessage(messageRequestDto);

    }
}
