package com.example.yummytable.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.security.Key;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {
  @Value("{spring.jwt.secret}")
  private String SECRET_KEY;

  public String createToken(String subject, long expTime) {
    if (expTime <= 0) {
      throw new RuntimeException("만료시간이 0보다 커야합니다.");
    }

    SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
    byte[] secretkeyBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
    Key signingkey = new SecretKeySpec(secretkeyBytes, signatureAlgorithm.getJcaName());

    return Jwts.builder()
        .setSubject(subject)
        .signWith()
  }
}
