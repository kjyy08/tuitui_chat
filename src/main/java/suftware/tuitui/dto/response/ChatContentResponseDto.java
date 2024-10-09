package suftware.tuitui.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import suftware.tuitui.domain.ChatContent;
import suftware.tuitui.domain.Profile;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChatContentResponseDto {
    private Integer chatContentId;

    private Integer senderProfileId;
    private String nickname;
    private String profileImage;

    private String message;

    private Timestamp createdAt;

    public static ChatContentResponseDto toDto(ChatContent chatContent, Profile profile){
        return ChatContentResponseDto.builder()
                .chatContentId(chatContent.getChatContentId())
                .senderProfileId(profile.getProfileId())
                .nickname(profile.getNickname())
                .profileImage(profile.getProfileImage().getImgUrl())
                .message(chatContent.getMessage())
                .createdAt(chatContent.getCreatedAt())
                .build();
    }
}
