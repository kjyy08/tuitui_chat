package suftware.tuitui.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import suftware.tuitui.common.enumtype.Gender;

import java.time.LocalDate;
import java.util.List;

@Entity
@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@Table(name = "profile")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_id", nullable = false, unique = true)
    Integer profileId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false, unique = true)
    User user;

    @Column(name = "name", nullable = false, length = 16)
    String name;

    @Column(name = "phone", nullable = false, unique = true, length = 25)
    String phone;

    @Column(name = "nickname", nullable = false, length = 45, unique = true)
    String nickname;

    @Column(name = "describe_self", length = 100)
    String describeSelf;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    Gender gender;

    @Column(name = "birth")
    LocalDate birth;

    @OneToOne(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true)
    private ProfileImage profileImage;

    @OneToMany(mappedBy = "hostProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChatRoom> hostChatRoom;

    @OneToMany(mappedBy = "guestProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChatRoom> guestChatRoom;

    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChatContent> chatContent;
}
