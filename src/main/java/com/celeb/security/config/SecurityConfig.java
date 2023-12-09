package com.celeb.security.config;

import com.celeb.security.jwt.JwtExceptionFilter;
import com.celeb.security.jwt.JwtTokenFilter;
import com.celeb.security.jwt.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenUtil jwtTokenUtil;
    private final JwtExceptionFilter jwtExceptionFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement((sessionManagement) ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .addFilterBefore(new JwtTokenFilter(jwtTokenUtil),
                UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(jwtExceptionFilter, JwtTokenFilter.class)
            .authorizeHttpRequests((authorizeRequests) ->
                authorizeRequests
                    .requestMatchers(HttpMethod.POST, "/posts/**",
                        "/clothes/**", "/comments/**").authenticated()
                    .requestMatchers("/bookmarks/**").authenticated()
                    .requestMatchers(HttpMethod.PATCH, "/posts/**",
                        "/clothes/**", "/comments/**").authenticated()
                    .anyRequest().permitAll()
            )
            .cors((cors) -> cors.configurationSource(corsConfigurationSource()));

        return http.build();
    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("https://celep.shop");
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}