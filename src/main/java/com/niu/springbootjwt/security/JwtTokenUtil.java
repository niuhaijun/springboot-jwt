package com.niu.springbootjwt.security;

import static io.jsonwebtoken.SignatureAlgorithm.HS512;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Clock;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.DefaultClock;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenUtil implements Serializable {

  private static final String CLAIM_KEY_USERNAME = "sub";

  private static final String CLAIM_KEY_CREATED = "iat";

  private static final long serialVersionUID = -3301605591108950415L;

  private Clock clock = DefaultClock.INSTANCE;

  @Value("${jwt.secret}")
  private String secret;

  @Value("${jwt.expiration}")
  private Long expiration;

  /**
   * 从token中获取用户名
   */
  public String getUsernameFromToken(String token) {

    return getClaimFromToken(token, Claims::getSubject);
  }

  /**
   * 从token中获取token生成日期
   */
  public Date getIssuedAtDateFromToken(String token) {

    return getClaimFromToken(token, Claims::getIssuedAt);
  }

  /**
   * 从token中获取token失效日期
   */
  public Date getExpirationDateFromToken(String token) {

    return getClaimFromToken(token, Claims::getExpiration);
  }

  /**
   * 从token中获取 Claim
   */
  public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {

    final Claims claims = getAllClaimsFromToken(token);
    return claimsResolver.apply(claims);
  }

  /**
   * 从token中获取 所有Claim
   */
  private Claims getAllClaimsFromToken(String token) {

    return Jwts.parser()
        .setSigningKey(secret)
        .parseClaimsJws(token)
        .getBody();
  }

  /**
   * 判断token是否已过期
   *
   *  true 已过期
   *  false 未过期
   */
  private Boolean isTokenExpired(String token) {

    final Date expiration = getExpirationDateFromToken(token);
    return expiration.before(clock.now());
  }

  /**
   * 判断token的生成时间是否早于密码最后一次修改时间
   *
   * true created 早于 lastPasswordReset
   * false created 晚于 lastPasswordReset
   */
  private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {

    return (lastPasswordReset != null && created.before(lastPasswordReset));
  }

  /**
   * 判断指定的token是否有过期时间
   *
   * true 没有过期时间
   * false 有过期时间
   */
  private Boolean ignoreTokenExpiration(String... token) {

    // here you specify tokens, for that the expiration is ignored
    return false;
  }

  /**
   * 生成token
   */
  public String generateToken(UserDetails userDetails) {

    final Map<String, Object> claims = new HashMap<>();
    final String subject = userDetails.getUsername();
    final Date createdDate = clock.now();
    final Date expirationDate = calculateExpirationDate(createdDate);

    return Jwts.builder()
        .setClaims(claims)
        .setSubject(subject)
        .setIssuedAt(createdDate)
        .setExpiration(expirationDate)
        .signWith(HS512, secret)
        .compact();
  }

  /**
   * 可否刷新token
   *
   * true  token创建时间晚于最后一次重设密码时间，而且token未过期
   * false 其他
   */
  public Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {

    final Date created = getIssuedAtDateFromToken(token);
    return !isCreatedBeforeLastPasswordReset(created, lastPasswordReset)
        && (!isTokenExpired(token) || ignoreTokenExpiration(token));
  }

  /**
   * 刷新token
   */
  public String refreshToken(String token) {

    final Date createdDate = clock.now();
    final Date expirationDate = calculateExpirationDate(createdDate);

    final Claims claims = getAllClaimsFromToken(token);
    claims.setIssuedAt(createdDate);
    claims.setExpiration(expirationDate);

    return Jwts.builder()
        .setClaims(claims)
        .signWith(HS512, secret)
        .compact();
  }

  /**
   * 校验token
   *
   *  true 有效
   *  false 失效
   *
   * 1、校验token中的用户名与UserDetails中的用户名是否相等
   * 2、校验token是否过期
   * 3、校验token生成时间是否晚于最近一次的密码修改时间
   */
  public Boolean validateToken(String token, UserDetails userDetails) {

    JwtUser user = (JwtUser) userDetails;
    final String username = getUsernameFromToken(token);
    final Date created = getIssuedAtDateFromToken(token);
    return (username.equals(user.getUsername())
        && !isTokenExpired(token)
        && !isCreatedBeforeLastPasswordReset(created, user.getLastPasswordResetDate())
    );
  }

  /**
   * 获取失效时间
   */
  private Date calculateExpirationDate(Date createdDate) {

    return new Date(createdDate.getTime() + expiration * 1000);
  }

}
