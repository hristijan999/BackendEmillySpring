package com.example.emilly_ecomercev2._securityConfig;
import com.example.emilly_ecomercev2.Service.Impl.CustomOAuth2UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;

    // Го чита FRONTEND_URL дефиниран во application.properties / Docker
    @Value("${app.cors.allowed-origins}")
    private String allowedOrigins;

    public SecurityConfig(CustomOAuth2UserService customOAuth2UserService) {
        this.customOAuth2UserService = customOAuth2UserService;
    }

    // Помошен метод кој го зема првиот URL од листата за пренасочување (Firebase или localhost)
    private String getPrimaryFrontendUrl() {
//        if (allowedOrigins != null && !allowedOrigins.isBlank()) {
//            return allowedOrigins.split(",")[0].trim();
//        }
        return "http://localhost:5173";
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // ✅ Вредноста се доделува само еднаш — променливата е ефективно final
        String rawUrl = getPrimaryFrontendUrl();
        final String baseUrl = rawUrl.endsWith("/")
                ? rawUrl.substring(0, rawUrl.length() - 1)
                : rawUrl;

        http
                .cors(Customizer.withDefaults())
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/Admin/**").hasAuthority("ADMIN")
                        .anyRequest().permitAll()
                )

                .formLogin(form -> form
                        .loginProcessingUrl("/auth/login")
                        .successHandler((request, response, authentication) -> {
                            request.getSession(true);
                            response.setStatus(HttpServletResponse.SC_OK);
                        })
                        .failureHandler((request, response, exception) -> {
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        })
                )

                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(customOAuth2UserService)
                        )
                        .successHandler((request, response, authentication) -> {
                            response.sendRedirect(baseUrl + "/Eshop");
                        })
                        .failureHandler((request, response, exception) -> {
                            response.sendRedirect(baseUrl + "/Login?error");
                        })
                )

                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID")
                        .logoutSuccessHandler((request, response, authentication) -> {
                            response.setStatus(HttpServletResponse.SC_OK);
                        })
                )

                .sessionManagement(session -> session
                        .maximumSessions(1)
                );

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        // 1. Ги чистиме празните места И ги отстрануваме косите црти (/) од крајот на секое URL
        List<String> origins = Arrays.stream(allowedOrigins.split(","))
                .map(String::trim)
                .map(url -> url.replaceAll("/+$", "")) // Ја отстранува косата црта на крајот ако постои
                .filter(url -> !url.isBlank())
                .toList();

        // 2. Користиме setAllowedOriginPatterns наместо setAllowedOrigins
        config.setAllowedOriginPatterns(origins);
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}