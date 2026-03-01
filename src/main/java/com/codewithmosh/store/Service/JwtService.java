package com.codewithmosh.store.Service;

import com.codewithmosh.store.Config.JwtConfig;
import com.codewithmosh.store.entities.Role;
import com.codewithmosh.store.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@AllArgsConstructor
@Service
public class JwtService {
    private final JwtConfig jwtConfig;
    public Jwt generateAccessToken(User user)
    {

       return generateToken(user, jwtConfig.getAccessTokenExpiration());
    }
    public Jwt generateRefreshToken(User user)
    {
        return generateToken(user, jwtConfig.getRefreshTokenExpiration());
    }
    private Jwt generateToken(User user, long tokenExpiration) {

      var claim =  Jwts.claims()
                .subject(user.getId().toString())
                .add("name",user.getName())
                .add("email",user.getEmail())
                .add("role",user.getRole())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+1000*tokenExpiration))
                .build();
        return new Jwt(claim, jwtConfig.getSecretKey());
    }
    public Jwt parseToken(String Token)
    {
        try{
            Claims claim = getClaims(Token);
            return new Jwt(claim, jwtConfig.getSecretKey());
        }catch (JwtException ex)
        {
            return null;
        }
    }

//    public boolean validateToken(String token)
//    {
//        try{
//            Claims claims = getClaims(token);
//            return claims.getExpiration().after(new Date());
//        }
//      catch (JwtException exception)
//      {
//          return false;
//      }

//    }
//    public Long getUserIdFromToken(String token)
//    {
//        return Long.valueOf(getClaims(token).getSubject());
//    }

//    public Role getRoleFromToken(String Token)
//    {
//        return Role.valueOf(getClaims(Token).get("role",String.class));
//    }
    private Claims getClaims(String token) {
        var claims =  Jwts.parser()
                .verifyWith(jwtConfig.getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims;
    }

}
