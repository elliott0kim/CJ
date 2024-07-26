package CJ.CJ.config.jwt;

import io.jsonwebtoken.*;


import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import CJ.CJ.config.message.MessageComponent;
import CJ.CJ.dto.client.user.UserInfoDto;
import CJ.CJ.dto.client.MessageCodeAndResDto;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Base64;

import org.json.JSONObject;


@Slf4j
@Component
public class JwtUtil {
    private final SecretKey key;
    private final long accessTokenExpTime;

//    private final long refreshTokenExpTime;

    @Autowired
    private MessageComponent messageComponent;

    @Autowired
    private MessageCodeAndResDto messageCodeAndResDto;

    public JwtUtil(@Value("${jwt.private_secret_key}") String secretKey,
                   @Value("${jwt.expiration_time_one_year}") long accessTokenExpTime
                   /*@Value("${jwt.expiration_time_one_year}") long refreshTokenExpTime*/)
    {
        byte[] keyBytes = Base64.getDecoder().decode(secretKey);
        key = new SecretKeySpec(keyBytes, "HmacSHA256");
        this.accessTokenExpTime = accessTokenExpTime;
//        this.refreshTokenExpTime = refreshTokenExpTime;
    }

    /**
     * Access Token 생성
     * @param userId
     * @return Access Token String
     */
    public String createAccessToken(String userId)
    {
        return createToken(userId, accessTokenExpTime);
    }

//    public String createRefreshToken(UserInfoDto userInfoDto)
//    {
//        return createToken(userInfoDto, refreshTokenExpTime);
//    }


    /**
     * JWT 생성
     * @param userId
     * @param expireTime
     * @return JWT String
     */
    private String createToken(String userId, long expireTime)
    {
        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime tokenValidity = now.plusSeconds(expireTime);
        return Jwts.builder()
                .signWith(key, SignatureAlgorithm.HS256)
                .setSubject(userId)
                .setIssuer("elliott_kim")
                .setExpiration(Date.from(tokenValidity.toInstant()))
                .setIssuedAt(Date.from(now.toInstant()))
                .compact();
    }


    /**
     * Token에서 User ID 추출
     * @param token
     * @return User ID
     */
    public String getUserId(String token)
    {
        return parseSub(token).get("sub", String.class);
    }


    /**
     * JWT 검증
     * @param token
     * @return IsValidate
     */
    public String validateToken(String token)
    {
        Jws<Claims> header = Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
//            Date exp = header.getPayload().getExpiration();
//            Date iat = header.getPayload().getIssuedAt();
//            if (exp.compareTo(iat) == -1)
//            {
//
//            }

        System.out.println(Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token));
        return messageComponent.getSUCCESS();

    }


    /**
     * JWT Claims 추출
     * @param accessToken
     * @return JWT Claims
     */
    public Claims parseSub(String accessToken)
    {
        try {
            return Jwts.parser().
                    setSigningKey(key).
                    build().
                    parseClaimsJws(accessToken).
                    getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    public static String parseUserId(String accessToken)
    {
        try {
            // JWT는 Header.Payload.Signature로 구성되므로, '.'로 분리
            String[] parts = accessToken.split("\\.");
            if (parts.length != 3) {
                throw new IllegalArgumentException("JWT should have 3 parts.");
            }

            // Base64Url로 인코딩된 페이로드 부분을 디코딩
            String payload = parts[1];
            byte[] decodedBytes = Base64.getUrlDecoder().decode(payload);
            String decodedPayload = new String(decodedBytes);

            // JSON 객체로 변환
            JSONObject json = new JSONObject(decodedPayload);

            // 발급자(issuer) 클레임 추출
            return json.optString("sub", ""); // "iss"는 발급자 클레임
        }
        catch (ExpiredJwtException e)
        {
            return "";
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
