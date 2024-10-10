package suftware.tuitui.repository.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import suftware.tuitui.domain.ChatContent;

public interface ChatContentRepository extends JpaRepository<ChatContent, Integer> {
    @Query("SELECT cc " +
            "FROM ChatContent cc " +
            "JOIN cc.sender p " +
            "WHERE cc.chatRoom.id = :roomId")
    Page<ChatContent> findByChatRoomWithProfiles(@Param("roomId") Integer roomId, Pageable pageable);

}
