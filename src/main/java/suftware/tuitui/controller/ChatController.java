package suftware.tuitui.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import suftware.tuitui.common.enumtype.TuiTuiMsgCode;
import suftware.tuitui.common.http.HttpResponseDto;
import suftware.tuitui.dto.request.MessageRequestDto;
import suftware.tuitui.dto.response.PageResponse;
import suftware.tuitui.service.ChatContentService;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ChatController {
    private final ChatContentService chatContentService;

    @GetMapping("/api/chat/rooms/{roomId}/messages")
    public ResponseEntity<HttpResponseDto> readMessages(@PathVariable(name = "roomId") Integer roomId,
                                                        @RequestParam(name = "pageNo", defaultValue = "0") Integer pageNo,
                                                        @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                        @RequestParam(name = "sortBy", defaultValue = "createdAt") String sortBy){
        Optional<PageResponse> chatPages = chatContentService.readChatContents(roomId, pageNo, pageSize, sortBy);

        return ResponseEntity.status(TuiTuiMsgCode.CHAT_CONTENT_READ_SUCCESS.getHttpStatus()).body(HttpResponseDto.builder()
                .status(TuiTuiMsgCode.CHAT_CONTENT_READ_SUCCESS.getHttpStatus())
                .message(TuiTuiMsgCode.CHAT_CONTENT_READ_SUCCESS.getMsg())
                .data(chatPages)
                .build());
    }

    //  메시지 발행
    @MessageMapping("/chat/message")
    public void sendMessage(@Valid MessageRequestDto messageRequestDto){
        log.info("ChatController.sendMessage() -> sender: {}, receiver: {}, message: {}",
                messageRequestDto.getSenderProfileId(), messageRequestDto.getReceiverProfileId(),messageRequestDto.getContent());
        chatContentService.sendMessage(messageRequestDto);
    }
}
