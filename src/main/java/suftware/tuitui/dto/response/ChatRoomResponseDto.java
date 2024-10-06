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

    private String hostProfileNickname;

    private String hostProfileImage;

    private Integer guestProfileId;

    private String guestProfileNickname;

    private String guestProfileImage;

    private Timestamp createdAt;

    private Timestamp updateAt;

    public static ChatRoomResponseDto toDto(ChatRoom chatRoom){
        return ChatRoomResponseDto.builder()
                .chatRoomId(chatRoom.getChatRoomId())
                .chatRoomName(chatRoom.getChatRoomName())
                .hostProfileId(chatRoom.getHostProfile().getProfileId())
                .hostProfileNickname(chatRoom.getHostProfile().getNickname())
                .hostProfileImage(chatRoom.getHostProfile().getProfileImage().getImgUrl())
                .guestProfileId(chatRoom.getGuestProfile().getProfileId())
                .guestProfileNickname(chatRoom.getGuestProfile().getNickname())
                .guestProfileImage(chatRoom.getGuestProfile().getProfileImage().getImgUrl())
                .createdAt(chatRoom.getCreatedAt())
                .updateAt(chatRoom.getUpdateAt())
                .build();
    }
}
