package com.example.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;


public class JwtUtil {

    private static final String JWT_ISSUER = "user";
    private static final String JWT_SECRET_KEY = "demo";


    /**
     * Jwt加密
     *
     * @return
     * @throws Exception
     */
    public static String encrypt(Long userId) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET_KEY);
            Calendar instance = Calendar.getInstance();
            instance.add(Calendar.DATE, 7);
            return JWT.create()
                    .withIssuer(JWT_ISSUER)
                    .withExpiresAt(instance.getTime())
                    .withClaim("id", userId)
                    .sign(algorithm);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Jwt解密
     *
     * @param token
     * @throws Exception
     */
    public static DecodedJWT decrypt(String token) throws Exception {
        Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET_KEY);
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(JWT_ISSUER)
                .build();
        return verifier.verify(token);
    }
}
