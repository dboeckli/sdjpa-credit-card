package ch.dboeckli.guru.jpa.creditcard.listener;

import ch.dboeckli.guru.jpa.creditcard.domain.CreditCard;
import ch.dboeckli.guru.jpa.creditcard.service.EncryptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.event.spi.PostLoadEvent;
import org.hibernate.event.spi.PostLoadEventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PostLoadListener implements PostLoadEventListener {

    private final EncryptionService encryptionService;

    @Override
    public void onPostLoad(PostLoadEvent event) {
        log.info("Post-load event for entity: {}", event.getEntity());
        if (event.getEntity() instanceof CreditCard creditCard) {
            decryptCvv(creditCard);
        }
    }

    private void decryptCvv(CreditCard creditCard) {
        try {
            String encryptedCvv = creditCard.getCvv();
            if (encryptedCvv != null && !encryptedCvv.isEmpty()) {
                String decryptedCvv = encryptionService.decrypt(encryptedCvv);
                creditCard.setCvv(decryptedCvv);
                log.info("Successfully decrypted CVV for credit card: {}", creditCard.getId());
            }
        } catch (Exception e) {
            log.error("Error decrypting CVV for credit card: {}", creditCard.getId(), e);
        }
    }
}
