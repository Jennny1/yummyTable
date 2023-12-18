package com.example.yummytable.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@EnableWebSecurity
@EnableMethodSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

  @Bean
  public static BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public WebSecurityCustomizer webSecurityCustomizer() {
    return (web) -> web.ignoring()
        .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
  }


  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    http
        .cors().disable()
        .csrf().disable()
        .formLogin().disable()
        .headers().frameOptions().disable()
        .and()
        .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
            .requestMatchers(new AntPathRequestMatcher("/**"),
                new AntPathRequestMatcher("/"),
                new AntPathRequestMatcher("/swagger-ui/**"),
                new AntPathRequestMatcher("/v3/api-docs/**"),
                new AntPathRequestMatcher("/demo-ui/**")
            ).permitAll()
/*                new AntPathRequestMatcher("/signin"),
                new AntPathRequestMatcher("/signup"),
                new AntPathRequestMatcher("/h2-console/**"),
                new AntPathRequestMatcher("/error/**")*/

            .anyRequest()
            .authenticated())
        .formLogin().permitAll()
        .loginPage("/signin");

    return http.build();
  }


  @Bean
  public OpenAPI openAPI() {
    return new OpenAPI()
        .components(new Components())
        .info(apiInfo());
  }


  private Info apiInfo() {
    return new Info()
        .title("Yummy table 맛집 공유 및 예약 커뮤니티")
        .description("회원가입/ 로그인/ 상점 등록/ 게시글 등록/ 댓글/ 찜하기/ 각 기능 CRUD를 할 수 있는 API")
        .version("1.0.0");
  }
}