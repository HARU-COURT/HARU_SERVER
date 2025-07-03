package com.harucourt.infrastructure.persistence.user;

import com.harucourt.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
