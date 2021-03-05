package com.ifox.admin.security.util;

import com.google.common.base.Strings;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.nimbusds.jwt.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.time.Instant;
import java.util.Date;

/**
 * @author zhangxl
 * @version v1.0
 * @date 2021/3/5 1:08 下午
 */
@Component
@Slf4j
public class JoseJwtTokenUtil {

    @Value("${security.jwt.secret}")
    private String secret;
    @Value("${security.jwt.expiration}")
    private Long expiration;
    @Value("${security.jwt.tokenHead}")
    private String tokenHead;
    @Value("${security.jwt.issuer}")
    private String issuer;

    public String getUsernameFromToken(String token) {
        return getClaimsSetByToken(token).getSubject();
    }

    private JWTClaimsSet getClaimsSetByToken(String token) {
        JWTClaimsSet claimsSet = null;
        try {
            JWSVerifier verifier = new MACVerifier(secret);
            SignedJWT signedJWT = SignedJWT.parse(token);
            signedJWT.verify(verifier);
            claimsSet = signedJWT.getJWTClaimsSet();
        } catch (ParseException e) {
            log.error("Payload of JWS object is not a valid JSON object" + e.getErrorOffset());
        } catch (JOSEException e) {
            log.error("JOSE exception for token:" + e.getCause());
        }
        return claimsSet;
    }

    public String generateJwtToken(String username) throws Exception {
        try {
            JWSSigner signer = new MACSigner(this.secret);
            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                    .subject(username)
                    .issuer(this.issuer)
                    .expirationTime(new Date(Instant.now().toEpochMilli() + this.expiration))
                    .audience("ifoxhub.com")
                    .build();
            SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);
            signedJWT.sign(signer);
            return signedJWT.serialize();
        } catch (Exception e) {
            throw new Exception("JWT Signing has failed: ", e.getCause());
        }
    }

    public String generateJwtToken(UserDetails userDetails) throws Exception {
        return generateJwtToken(userDetails.getUsername());
    }

    public Boolean validateTokenTimestamp(String token, UserDetails userDetails) {
        final Date expiration = getClaimsSetByToken(token).getExpirationTime();
        boolean hasExpired = expiration.before(new Date());
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !hasExpired);
    }

    /**
     * 当原来的token没过期时是可以刷新的
     *
     * @param oldToken 带tokenHead的token
     */
    public String refreshToken(String oldToken) throws Exception {
        if(Strings.isNullOrEmpty(oldToken)){
            return null;
        }
        String token = oldToken.substring(tokenHead.length());
        if(Strings.isNullOrEmpty(token)){
            return null;
        }
        //token校验不通过
        JWTClaimsSet claimsSet = getClaimsSetByToken(token);
        if(claimsSet == null) {
            return null;
        }
        //如果token已经过期，不支持刷新
        if(claimsSet.getExpirationTime().before(new Date())){
            return null;
        }
        //如果token在30分钟之内刚刷新过，返回原token
        if (DateUtils.isAfter(claimsSet.getIssueTime(), new Date(), 30 * 60)) {
            return token;
        }
        return generateJwtToken(claimsSet.getSubject());
    }
}
