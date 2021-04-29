package com.lepa.portal.repository;

import com.lepa.portal.model.portal.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UsersRepo extends JpaRepository<Users, Long> {

    Optional <Users> findUsersById(Long id);

    Optional<Users> findUsersByUsername(String username);

    Boolean existsByEmail(String email);

    Boolean existsByUsername(String username);

    Optional <Users> findUsersByEmail(String email);



}
