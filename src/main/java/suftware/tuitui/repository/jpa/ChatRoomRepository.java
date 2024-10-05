package suftware.tuitui.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import suftware.tuitui.domain.ChatRoom;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Integer> {

    //  주로 채팅방 조회에 사용
    @Query("SELECT cr " +
            "FROM ChatRoom cr " +
            "WHERE cr.hostProfile.profileId = :profileId " +
            "OR cr.guestProfile.profileId = :profileId")
    List<ChatRoom> findByProfile(@Param("profileId") Integer profileId);

    //  채팅방 생성 시 사용
    @Query("SELECT cr " +
            "FROM ChatRoom cr " +
            "WHERE (cr.hostProfile.profileId = :hostId AND cr.guestProfile.profileId = :guestId) " +
            "OR (cr.hostProfile.profileId = :guestId AND cr.guestProfile.profileId = :hostId)")
    Optional<ChatRoom> findByHostOrGuest(@Param("hostId") Integer hostId, @Param("guestId") Integer guestId);


    List<ChatRoom> findByHostProfile_profileId(Integer profileId);
    List<ChatRoom> findByGuestProfile_profileId(Integer profileId);
}
