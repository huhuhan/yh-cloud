package com.yh.cloud.web.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;

/**
 * @author yanghan
 * @date 2019/7/31
 */
@Slf4j
public class JwtUtil {
    /** token 的 key */
    public static final String AUTHORIZATION = "Authorization";
    /** token 的 key前缀 */
    public static final String BEARER = "Bearer";
    /** 签发者 */
    public static final String ISSUER = "auth";
    /** 秘钥 */
    public static String SECRET = "feiwei2019";
    /** 差距时长 单位秒 */
    public static long leewayTime = 3 * 60 * 60;

    /** token额外信息的key */
    public static final String INFO_KEY = "user";

    /**
     * 验证token
     *
     * @param token
     * @return jwt对象，为空表示无效token
     */
    public static DecodedJWT verifyToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(JwtUtil.SECRET);
            JWTVerifier verifier = JWT.require(algorithm)
                    //单位：秒，有效时长，无效依据：exp > (当前时间 - 有效时长)
                    .acceptExpiresAt(JwtUtil.leewayTime)
                    //单位：秒，有效时长，无效依据：iat < (当前时间 + 有效时长)
                    .acceptIssuedAt(JwtUtil.leewayTime)
                    //单位：秒，有效时长，无效依据：nbf < (当前时间 + 有效时长)
                    .acceptNotBefore(JwtUtil.leewayTime)
                    //单位：秒，通用有效时长, 同时给iat、exp、nbf添加时长，但不覆盖
                    .acceptLeeway(JwtUtil.leewayTime)
                    //验证签发者
                    .withIssuer(JwtUtil.ISSUER)
                    .build();
            return verifier.verify(token);
        } catch (JWTVerificationException exception) {
            log.error(exception.getMessage(), exception);
        }
        return null;
    }

/*
    public static String createToken() {
        List<String> roles = Lists.newArrayList("admin");
        SysUser sysUser = new SysUser();
        sysUser.setUserName("cs");
        sysUser.setUserId("csId");
        sysUser.setLoginName("csLoginName");
        sysUser.setRoles(roles);

        Calendar now = Calendar.getInstance();
        now.add(Calendar.DAY_OF_YEAR, -1);
        System.out.println("token time : " + now.getTime());

        Algorithm algorithm = Algorithm.HMAC256(JwtUtil.SECRET);
        return JWT.create()
                .withExpiresAt(now.getTime())
                .withIssuer(JwtUtil.ISSUER)
                .withIssuedAt(now.getTime())
                .withNotBefore(now.getTime())
                .withSubject(sysUser.getLoginName())
                .withClaim("user", JsonUtil.objectToJson(sysUser))
                .sign(algorithm);
    }

    public static void main(String[] args) {
        String token = createToken();
        System.out.println(token);

        DecodedJWT jwt = verifyToken(token);
        boolean flag = null != jwt;
        System.out.println("token is " + flag);
        if (flag) {
            System.out.println(jwt.getExpiresAt().after(new Date()));
        }
    }
*/
}
