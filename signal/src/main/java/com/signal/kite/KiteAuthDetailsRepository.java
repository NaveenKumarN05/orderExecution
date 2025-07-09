package com.signal.kite;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KiteAuthDetailsRepository extends JpaRepository<KiteAuthDetails, Long> {
    Optional<KiteAuthDetails> findByAuthDate(LocalDate authDate);

}
