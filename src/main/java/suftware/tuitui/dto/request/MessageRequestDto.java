package suftware.tuitui.dto.request;

import jakarta.validation.constraints.NotEmpty;
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

    @NotEmpty
    private String content;
}
