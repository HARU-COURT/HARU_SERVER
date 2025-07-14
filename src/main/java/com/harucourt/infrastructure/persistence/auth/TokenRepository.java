package com.harucourt.infrastructure.persistence.auth;

import com.harucourt.domain.auth.domain.Token;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface TokenRepository extends CrudRepository<Token, UUID> {
}
