package com.epam.esm.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.Map;

public class JwtUtils {

    private static final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
    private static final String SECRET = "F0urthM0du13";

    public static String generate(String username, long expiresInMinutes, String type){
        Key key = generateKey();

        Date expires = new Date(System.currentTimeMillis() + expiresInMinutes * 60 * 1000);

        return Jwts.builder()
                .setIssuedAt(new Date())
                .setExpiration(expires)
                .setSubject(username)
                .setClaims(Map.of("type", type))
                .signWith(signatureAlgorithm, key)
                .compact();
    }

    public static Claims getUsername(String token){
        return Jwts.parser()
                .setSigningKey(generateKey())
                .parseClaimsJws(token)
                .getBody();
    }

    private static Key generateKey() {
        byte[] bytes = DatatypeConverter.parseBase64Binary(SECRET);
        return new SecretKeySpec(bytes, signatureAlgorithm.getJcaName());
    }
}
