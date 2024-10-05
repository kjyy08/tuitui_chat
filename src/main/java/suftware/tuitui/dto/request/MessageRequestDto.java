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
    private Integer roomId;
    private Integer senderProfileId;
    private Integer receiverProfileId;
    private String content;
}
