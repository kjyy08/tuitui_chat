package suftware.tuitui.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.sql.Timestamp;

@Entity
@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_host_id", referencedColumnName = "profile_id", nullable = false)
    private Profile hostProfile;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_guest_id", referencedColumnName = "profile_id", nullable = false)
    private Profile guestProfile;

    @Column(name = "create_at")
    private Timestamp createdAt;

    @Column(name = "lastly")
    private Timestamp updateAt;
}
