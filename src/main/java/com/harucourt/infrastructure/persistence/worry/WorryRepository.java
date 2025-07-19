package com.harucourt.infrastructure.persistence.worry;

import com.harucourt.domain.worry.domain.Worry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorryRepository extends JpaRepository<Worry, Long>, WorryRepositoryCustom {
}
