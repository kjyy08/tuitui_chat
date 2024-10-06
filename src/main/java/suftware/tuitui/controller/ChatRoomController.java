package suftware.tuitui.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import suftware.tuitui.common.enumtype.TuiTuiMsgCode;
import suftware.tuitui.common.http.HttpResponseDto;
import suftware.tuitui.dto.request.ChatRoomRequestDto;
import suftware.tuitui.dto.response.ChatRoomResponseDto;
import suftware.tuitui.service.ChatRoomService;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/chat/")
public class ChatRoomController {
    private final ChatRoomService chatRoomService;

    @GetMapping("rooms/{profileId}")
    public ResponseEntity<HttpResponseDto> readChatRoom(@PathVariable(name = "profileId") Integer profileId){
        List<ChatRoomResponseDto> chatRoomResponseDtoList = chatRoomService.readChatRoomByProfile(profileId);

        return ResponseEntity.status(TuiTuiMsgCode.CHAT_ROOM_READ_SUCCESS.getHttpStatus()).body(HttpResponseDto.builder()
                .status(TuiTuiMsgCode.CHAT_ROOM_READ_SUCCESS.getHttpStatus())
                .message(TuiTuiMsgCode.CHAT_ROOM_READ_SUCCESS.getMsg())
                .data(chatRoomResponseDtoList)
                .build());
    }

    @PostMapping("rooms")
    public ResponseEntity<HttpResponseDto> createChatRoom(@RequestBody ChatRoomRequestDto chatRoomRequestDto){
        //  채팅방이 있으면 그대로 반환, 없으면 생성
        Optional<ChatRoomResponseDto> chatRoomResponseDto = chatRoomService.createChatRoom(chatRoomRequestDto.getHostProfileId(), chatRoomRequestDto.getGuestProfileId());

        return ResponseEntity.status(TuiTuiMsgCode.CHAT_ROOM_CREATE_SUCCESS.getHttpStatus()).body(HttpResponseDto.builder()
                .status(TuiTuiMsgCode.CHAT_ROOM_CREATE_SUCCESS.getHttpStatus())
                .message(TuiTuiMsgCode.CHAT_ROOM_CREATE_SUCCESS.getMsg())
                .data(chatRoomResponseDto)
                .build());
    }
}
