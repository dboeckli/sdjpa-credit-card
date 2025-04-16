package ch.dboeckli.guru.jpa.creditcard.domain;

import ch.dboeckli.guru.jpa.creditcard.util.EncryptionUtil;
import jakarta.persistence.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CreditCardJPACallback {

    @PrePersist
    @PreUpdate
    public void beforeInsertOrUpdate(CreditCard creditCard) {
        log.info("before update was called for CreditCard: {}", creditCard);
        creditCard.setExpirationDate(EncryptionUtil.encrypt(creditCard.getExpirationDate()));
    }

    @PostPersist
    @PostLoad
    @PostUpdate
    public void postLoad(CreditCard creditCard) {
        log.info("Post Load was called for CreditCard: {}", creditCard);
        creditCard.setExpirationDate(EncryptionUtil.decrypt(creditCard.getExpirationDate()));
    }
}
