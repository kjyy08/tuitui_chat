package suftware.tuitui;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

import java.util.TimeZone;

@SpringBootApplication(exclude = {RedisRepositoriesAutoConfiguration.class})
public class TuituiChatApplication {

	@PostConstruct
	void started(){
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
	}

	public static void main(String[] args) {
		SpringApplication.run(TuituiChatApplication.class, args);
	}
}
