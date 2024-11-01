package suftware.tuitui.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(
        basePackages = "suftware.tuitui.repository.jpa"
)
public class JpaConfig {
}
