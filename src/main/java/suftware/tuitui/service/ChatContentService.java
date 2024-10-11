package suftware.tuitui.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import suftware.tuitui.common.enumtype.MessageType;
import suftware.tuitui.common.enumtype.TuiTuiMsgCode;
import suftware.tuitui.common.exception.TuiTuiException;
import suftware.tuitui.domain.ChatContent;
import suftware.tuitui.domain.ChatRoom;
import suftware.tuitui.dto.request.MessageRequestDto;
import suftware.tuitui.dto.response.ChatContentResponseDto;
import suftware.tuitui.dto.response.PageResponse;
import suftware.tuitui.redis.RedisPublisher;
import suftware.tuitui.repository.jpa.ChatContentRepository;
import suftware.tuitui.repository.jpa.ChatRoomRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatContentService {
    private final RedisPublisher redisPublisher;
    private final ChatContentRepository chatContentRepository;
    private final ChatRoomRepository chatRoomRepository;

    //  채팅방에 있는 모든 채팅 조회
    public Optional<PageResponse> readChatContents(Integer roomId, Integer pageNo, Integer pageSize, String sortBy){
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
        Page<ChatContent> contentPage = chatContentRepository.findByChatRoomWithProfiles(roomId, pageable);

        List<ChatContent> chatContents = contentPage.getContent();

        if (chatContents.isEmpty())
            throw new TuiTuiException(TuiTuiMsgCode.CHAT_CONTENT_NOT_FOUND);

        // ChatContent에서 Profile 정보를 가져와서 DTO로 변환
        List<ChatContentResponseDto> chatContentResponseDtos = chatContents.stream()
                .map(chatContent -> ChatContentResponseDto.toDto(chatContent, chatContent.getSender()))
                .toList();

        return Optional.of(PageResponse.builder()
                .contents(chatContentResponseDtos)
                .pageNo(pageNo)
                .pageSize(pageSize)
                .totalElements(Long.valueOf(contentPage.getTotalElements()).intValue())
                .totalPages(contentPage.getTotalPages())
                .lastPage(contentPage.isLast())
                .build());
    }

    // 메시지 전송 및 저장
    public void sendMessage(MessageRequestDto messageRequestDto) {
        // publish 호출이 필요한 메시지 타입인지 확인
        if (isPublish(messageRequestDto.getMessageType())) {
            redisPublisher.publish(messageRequestDto);
            createChatContent(messageRequestDto);
        }

        // 그 외의 메시지는 타입에 따라 처리
        switch (messageRequestDto.getMessageType()) {
            case READ -> {
                //  채팅 읽음 처리
            }

            case QUIT -> {
                //  채팅방 나감 처리
            }

            case DELETE -> {
                //  채팅방 삭제 처리
            }
        }
    }

    // 채팅방 입장, 채팅 여부 체크
    private boolean isPublish(MessageType messageType) {
        return messageType == MessageType.ENTER || messageType == MessageType.CHAT;
    }

    //  DB에 메시지 저장
    private void createChatContent(MessageRequestDto messageRequestDto){
        ChatRoom chatRoom = chatRoomRepository.findById(messageRequestDto.getRoomId())
                .orElseThrow(() -> new TuiTuiException(TuiTuiMsgCode.CHAT_ROOM_NOT_FOUND));

        //  1대1 채팅방에 해당하지 않는 유저인 경우 403 반환
        if (!(chatRoom.getHostProfile().getProfileId().equals(messageRequestDto.getSenderProfileId())
                || chatRoom.getGuestProfile().getProfileId().equals(messageRequestDto.getSenderProfileId()))){
            throw new TuiTuiException(TuiTuiMsgCode.CHAT_ROOM_FORBIDDEN);
        }

        log.info("ChatContentService.createChatContent() -> save message, chatRoomId: {}, senderId: {}, message: {}",
                messageRequestDto.getRoomId(), messageRequestDto.getSenderProfileId(), messageRequestDto.getContent());

        chatContentRepository.save(ChatContent.of(chatRoom,
                chatRoom.getHostProfile().getProfileId().equals(messageRequestDto.getSenderProfileId()) ? chatRoom.getHostProfile() : chatRoom.getGuestProfile(),
                messageRequestDto.getContent()));
    }
}
