package suftware.tuitui.dto.request;

import lombok.*;
import suftware.tuitui.common.enumtype.MessageType;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MessageRequestDto {
    private MessageType messageType;
    private String roomId;
    private Integer senderProfileId;
    private String content;
}
