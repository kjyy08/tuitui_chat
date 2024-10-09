package suftware.tuitui.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import suftware.tuitui.common.enumtype.TuiTuiMsgCode;
import suftware.tuitui.common.exception.TuiTuiException;
import suftware.tuitui.common.time.DateTimeUtil;
import suftware.tuitui.domain.ChatContent;
import suftware.tuitui.domain.ChatRoom;
import suftware.tuitui.domain.Profile;
import suftware.tuitui.dto.request.MessageRequestDto;
import suftware.tuitui.dto.response.ChatContentResponseDto;
import suftware.tuitui.dto.response.PageResponse;
import suftware.tuitui.redis.RedisPublisher;
import suftware.tuitui.repository.jpa.ChatContentRepository;
import suftware.tuitui.repository.jpa.ChatRoomRepository;
import suftware.tuitui.repository.jpa.ProfileRepository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ChatContentService {
    private final RedisPublisher redisPublisher;
    private final ChatContentRepository chatContentRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ProfileRepository profileRepository;

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

    //  메시지 전송 및 저장
    public void sendMessage(MessageRequestDto messageRequestDto){
        if (messageRequestDto.getContent().isEmpty()){
            throw new TuiTuiException(TuiTuiMsgCode.CHAT_CONTENT_NOT_FOUND);
        }

        redisPublisher.publish(messageRequestDto);
        createChatContent(messageRequestDto);
    }

    //  DB에 메시지 저장
    private void createChatContent(MessageRequestDto messageRequestDto){
        ChatRoom chatRoom = chatRoomRepository.findById(messageRequestDto.getRoomId())
                .orElseThrow(() -> new TuiTuiException(TuiTuiMsgCode.CHAT_ROOM_NOT_FOUND));

        if (!chatRoom.getHostProfile().getProfileId().equals(messageRequestDto.getSenderProfileId()) &&
                !chatRoom.getGuestProfile().getProfileId().equals(messageRequestDto.getReceiverProfileId())){
            throw new TuiTuiException(TuiTuiMsgCode.CHAT_ROOM_FORBIDDEN);
        }

        chatContentRepository.save(ChatContent.builder()
                .chatRoom(chatRoom)
                .sender(chatRoom.getHostProfile().getProfileId().equals(messageRequestDto.getSenderProfileId()) ?
                        chatRoom.getHostProfile() : chatRoom.getGuestProfile())
                .createdAt(DateTimeUtil.getSeoulTimestamp())
                .message(messageRequestDto.getContent())
                .build());
    }
}
