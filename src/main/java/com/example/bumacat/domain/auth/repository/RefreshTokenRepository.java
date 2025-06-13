package com.example.bumacat.domain.auth.repository;

import com.example.bumacat.domain.auth.model.RefreshToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
  Optional<RefreshToken> findByRefreshToken(String token);
}
