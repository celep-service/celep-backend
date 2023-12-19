package com.celeb.security.jwt;

import java.io.Serializable;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
// import jakarta.persistence.Id;

// @Entity
@NoArgsConstructor
@Getter
@RedisHash(value = "token", timeToLive = 60 * 60 * 24 * 14)// TTL 14일
// (TTL이란 Time To Live의 약자로, 캐시의 유효기간을 의미한다. 캐시의 유효기간이 지나면 캐시는 삭제된다. 단위는 초이다.)
public class Token implements Serializable {

    // serialVersionId는 직렬화를 위한 고유 아이디이다.
    private static final long serialVersionUID = 1L;

    //    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;

    // @Column(nullable = false)
    // jakarta.persistence.Id 가 아닌 org.springframework.data.annotation.Id 임에 주의.
    @Id
    private String refreshToken;

    //    @OneToOne
//    @JoinColumn
//    private User user;

    // TODO: userId만을 이용해서 @Id를 설정하면, 한 유저가 여러 기기에서 로그인할 경우, 기존에 로그인한 기기의 토큰이 사라져 로그아웃되는 문제가 발생할 수 있다.
    private Integer userId;

    private Long expiredAt;

    @Builder
    public Token(Integer userId, String refreshToken, long expiredAt) {
        this.userId = userId;
        this.refreshToken = refreshToken;
        this.expiredAt = expiredAt;
    }
}
