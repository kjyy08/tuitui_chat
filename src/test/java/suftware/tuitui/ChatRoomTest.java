package suftware.tuitui;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import suftware.tuitui.repository.jpa.ChatRoomRepository;

@Slf4j
@SpringBootTest
public class ChatRoomTest {
    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Test
    void readChatRoomListTest(){
        for (int i = 0; i < 20; i++){
            Long before = System.currentTimeMillis();
            chatRoomRepository.findById(8);
            Long after = System.currentTimeMillis();
            log.info("time: {}", after - before);
        }
    }


}
