package com.joaomariajaneiro.neechathon.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.joaomariajaneiro.neechathon.model.User;
import com.joaomariajaneiro.neechathon.repository.UserRepository;
import com.joaomariajaneiro.neechathon.security.JwtProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User getUserFromToken( Map<String, String> headers) {
        String token;
        try {
            token = headers.get("authorization").replace(JwtProperties.TOKEN_PREFIX, "");
        } catch (Exception e) {
            return null;
        }

        String email;
        try {
            email = JWT.require(Algorithm.HMAC256(JwtProperties.SECRET.getBytes()))
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (Exception e) {
            return null;
        }

        User user;

        try {
            user = userRepository.findByEmail(email);
        } catch (Exception e) {
            return null;
        }

        return user;
    }
}
