package ch.dboeckli.guru.jpa.creditcard.repository;

import ch.dboeckli.guru.jpa.creditcard.domain.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {
}
