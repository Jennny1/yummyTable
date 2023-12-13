package com.example.yummytable.security;

import com.example.yummytable.service.MemberService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@RequiredArgsConstructor
public class TokenProvider {

  private static final String KEY_ROLES = "roles";
  private static final long TOKEN_EXPIRE_TIME = 1000 * 60 * 60; // 1시간
  private final MemberService memberService;

  @Value("{spring.jwt.secret}")
  private String secretKey;

  /**
   * 토큰 생성 (발급)
   *
   * @param username
   * @param roles
   * @return
   */
  public String generateToken(String username, List<String> roles) {
    Claims claims = Jwts.claims().setSubject(username);
    claims.put(KEY_ROLES, roles);

    Date now = new Date(); // 토큰 생성시간
    Date expiredDate = new Date(now.getTime() + TOKEN_EXPIRE_TIME); // 토큰 만료시간

    return Jwts.builder()
        .setClaims(claims)
        .setIssuedAt(now)
        .setExpiration(expiredDate)
        .signWith(SignatureAlgorithm.HS512, this.secretKey) // 암호화 알고리즘, 비밀키
        .compact();
  }

  public Authentication getAuthentication(String jwt) {
    UserDetails userDetails = this.memberService.loadUserByUsername(this.getUsername(jwt));
    // 사용자의 정보, 권한정보 가져오기
    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
  }

  public String getUsername(String token) {
    return this.parseClaims(token).getSubject();
  }

  public boolean validateToken(String token) {
    if (!StringUtils.hasText(token)) {
      // 토큰이 빈값일 때
      return false;
    }

    Claims claims = this.parseClaims(token);
    // 현재시간과 비교하여 유효한지
    return !claims.getExpiration().before(new Date());

  }

  // 토큰에서 클레임 정보를 가져옴
  private Claims parseClaims(String token) {
    try {
      return Jwts.parser().setSigningKey(this.secretKey).parseClaimsJws(token).getBody();
    } catch (ExpiredJwtException e) {
      return e.getClaims();
    }

  }
}
