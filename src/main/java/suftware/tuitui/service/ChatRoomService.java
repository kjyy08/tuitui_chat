package suftware.tuitui.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import suftware.tuitui.common.enumtype.TuiTuiMsgCode;
import suftware.tuitui.common.exception.TuiTuiException;
import suftware.tuitui.domain.ChatRoom;
import suftware.tuitui.dto.response.ChatRoomResponseDto;
import suftware.tuitui.repository.jpa.ChatRoomRepository;
import suftware.tuitui.repository.jpa.ProfileRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final ProfileRepository profileRepository;

    //  채팅방 목록 전체 조회
    @Transactional(readOnly = true)
    public List<ChatRoomResponseDto> readChatRoomByProfile(Integer profileId){
        List<ChatRoom> chatRoomList = chatRoomRepository.findByProfile(profileId);

        if (chatRoomList.isEmpty()){
            throw new TuiTuiException(TuiTuiMsgCode.CHAT_ROOM_NOT_FOUND);
        }

        return chatRoomList.stream()
                .map(ChatRoomResponseDto::toDto)
                .toList();
    }

    //  채팅방 조회 및 생성
    public Optional<ChatRoomResponseDto> createChatRoom(Integer hostId, Integer guestId){
        //  db에 저장된 채팅방 조회
        Optional<ChatRoom> chatRoom  = chatRoomRepository.findByHostOrGuest(hostId, guestId);
        //  없으면 새로운 채팅방 생성
        if (chatRoom.isEmpty()){
            chatRoom = Optional.of(chatRoomRepository.save(ChatRoom.of(
                    profileRepository.findById(hostId).orElseThrow(() -> new TuiTuiException(TuiTuiMsgCode.PROFILE_NOT_FOUND)),
                    profileRepository.findById(guestId).orElseThrow(() -> new TuiTuiException(TuiTuiMsgCode.PROFILE_NOT_FOUND))))
            );
        }

        return Optional.of(ChatRoomResponseDto.toDto(chatRoom.get()));
    }
}
