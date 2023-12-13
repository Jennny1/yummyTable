package com.example.yummytable.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.security.Key;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

  SecureRandom secureRandom = new SecureRandom();
  byte[] key = new byte[32]; // 256비트 크기의 키
  /*secureRandom.nextBytes(key);*/
  String secretKey = Base64.getEncoder().encodeToString(key);


  /**
   * 토큰 생성
   *
   * @param subject
   * @param expTime
   * @return
   */
  public String createToken(String subject, long expTime) {
    if (expTime <= 0) {
      throw new RuntimeException("만료시간이 0보다 커야합니다.");
    }

    SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
    byte[] secretkeyBytes = DatatypeConverter.parseBase64Binary(secretKey);
    Key signingkey = new SecretKeySpec(secretkeyBytes, signatureAlgorithm.getJcaName());

    return Jwts.builder()
        .setSubject(subject)
        .signWith(signingkey, signatureAlgorithm)
        .setExpiration(new Date(System.currentTimeMillis() + expTime))
        .compact();

  }

  /**
   * 토큰 검증
   *
   * @param token
   * @return
   */
  public String getSubject(String token) {
    Claims claims = Jwts.parserBuilder()
        .setSigningKey(DatatypeConverter.parseBase64Binary(secretKey))
        .build()
        .parseClaimsJws(token)
        .getBody();

    return claims.getSubject();

  }


}
