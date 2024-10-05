package suftware.tuitui.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import suftware.tuitui.domain.ChatRoom;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChatRoomResponseDto {
    private Integer chatRoomId;

    private String chatRoomName;

    private Integer hostProfileId;

    private Integer guestProfileId;

    private Timestamp createdAt;

    private Timestamp updateAt;

    public static ChatRoomResponseDto toDto(ChatRoom chatRoom){
        return ChatRoomResponseDto.builder()
                .chatRoomId(chatRoom.getChatRoomId())
                .chatRoomName(chatRoom.getChatRoomName())
                .hostProfileId(chatRoom.getHostProfile().getProfileId())
                .guestProfileId(chatRoom.getGuestProfile().getProfileId())
                .createdAt(chatRoom.getCreatedAt())
                .updateAt(chatRoom.getUpdateAt())
                .build();
    }
}
