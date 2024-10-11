package suftware.tuitui.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import suftware.tuitui.common.time.DateTimeUtil;

import java.sql.Timestamp;
import java.util.List;

@Entity
@ToString
@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
@Table(name = "chat_room", uniqueConstraints = {
        @UniqueConstraint(name = "uk_chat_room_id", columnNames = {"chat_room_host_id", "chat_room_guest_id"})
})
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_room_id", nullable = false, unique = true)
    private Integer chatRoomId;

    @Column(name = "chat_room_name")
    private String chatRoomName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_host_id", referencedColumnName = "profile_id", nullable = false)
    private Profile hostProfile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_guest_id", referencedColumnName = "profile_id", nullable = false)
    private Profile guestProfile;

    @Column(name = "create_at")
    private Timestamp createdAt;

    @Column(name = "lastly")
    private Timestamp updateAt;

    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChatContent> chatContents;

    public static ChatRoom of(Profile hostProfile, Profile guestProfile){
        return ChatRoom.builder()
                .hostProfile(hostProfile)
                .guestProfile(guestProfile)
                .createdAt(DateTimeUtil.getSeoulTimestamp())
                .updateAt(DateTimeUtil.getSeoulTimestamp())
                .build();
    }

    public void updateLastly(){
        this.updateAt = DateTimeUtil.getSeoulTimestamp();
    }
}
