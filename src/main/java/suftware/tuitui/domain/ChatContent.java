package suftware.tuitui.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import suftware.tuitui.common.time.DateTimeUtil;

import java.sql.Timestamp;

@Entity
@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder(access = AccessLevel.PRIVATE)
@DynamicUpdate
@Table(name = "chat_content")
public class ChatContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_content_id")
    private Integer chatContentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id", referencedColumnName = "chat_room_id")
    private ChatRoom chatRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_sender_id", referencedColumnName = "profile_id")
    private Profile sender;

    @Column(name = "create_at")
    private Timestamp createdAt;

    @Column(name = "message")
    private String message;

    public static ChatContent of(ChatRoom chatRoom, Profile sender, String message){
        return ChatContent.builder()
                .chatRoom(chatRoom)
                .sender(sender)
                .createdAt(DateTimeUtil.getSeoulTimestamp())
                .message(message)
                .build();
    }
}
