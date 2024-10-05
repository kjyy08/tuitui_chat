package suftware.tuitui.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import suftware.tuitui.common.jwt.JwtAuthFilter;
import suftware.tuitui.common.jwt.JwtFilter;
import suftware.tuitui.common.jwt.JwtUtil;
import suftware.tuitui.repository.jpa.UserRepository;

@Configuration
@EnableWebSecurity(debug = false)
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers(
                                new AntPathRequestMatcher("/error"),
                                new AntPathRequestMatcher("/ws-stomp/**")).permitAll()
                        .anyRequest().authenticated())

                .addFilterBefore(new JwtFilter(jwtUtil, userRepository), LogoutFilter.class)
                .addFilterBefore(new JwtAuthFilter(jwtUtil), JwtFilter.class)

                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }
}
