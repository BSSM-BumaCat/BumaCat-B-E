package com.example.bumacat.domain.user.repository;

import com.example.bumacat.domain.user.model.User;
import com.example.bumacat.global.security.auth.AuthDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByEmail(String email);
}

