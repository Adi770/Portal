package com.lepa.portal.repository;

import com.lepa.portal.model.portal.Token;
import com.lepa.portal.model.portal.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepo extends JpaRepository<Token,Long> {

    Token findByTokenValue(String tokenValue);

    Optional<Token> findByUsers(Users users);
}
