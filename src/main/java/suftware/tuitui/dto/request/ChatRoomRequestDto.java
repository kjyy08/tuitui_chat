package suftware.tuitui.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChatRoomRequestDto {
    private String chatRoomName;

    private Integer hostProfileId;

    private Integer guestProfileId;
}
