package suftware.tuitui.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(
        basePackages = "suftware.tuitui.repository.jpa"
)
public class JpaConfig {
    // JPA-specific configuration, if any
}
