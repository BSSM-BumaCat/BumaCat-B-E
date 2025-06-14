package com.example.bumacat.domain.user.repository;

import com.example.bumacat.domain.user.dto.response.UserResponse;
import com.example.bumacat.domain.user.model.User;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByLoginId(String loginId);

  @Query("SELECT u FROM User u where u.role = 'ROLE_SELLER' order by u.userId desc")
  Slice<User> findSellers(Pageable pageable);

  @Query("SELECT u FROM User u where u.role = 'ROLE_SELLER' and u.userId <= :cursorId order by u.userId desc")
  Slice<User> findSellersByCursorId(@Param("cursorId") Long cursorId, Pageable pageable);


}

