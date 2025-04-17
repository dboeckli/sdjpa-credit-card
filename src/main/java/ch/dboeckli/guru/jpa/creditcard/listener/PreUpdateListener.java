package ch.dboeckli.guru.jpa.creditcard.listener;

import ch.dboeckli.guru.jpa.creditcard.domain.CreditCard;
import ch.dboeckli.guru.jpa.creditcard.util.EncryptionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.event.spi.PreUpdateEvent;
import org.hibernate.event.spi.PreUpdateEventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PreUpdateListener implements PreUpdateEventListener {

    @Override
    public boolean onPreUpdate(PreUpdateEvent event) {
        log.info("Pre-update event for entity: {}", event.getEntity());

        if (event.getEntity() instanceof CreditCard creditCard) {
            encryptCvvIfChanged(creditCard, event);
        }

        return false;
    }

    private void encryptCvvIfChanged(CreditCard creditCard, PreUpdateEvent event) {
        Object[] currentState = event.getState();
        String[] propertyNames = event.getPersister().getPropertyNames();

        int cvvIndex = getCvvIndex(propertyNames);
        if (cvvIndex != -1) {
            String currentCvv = (String) currentState[cvvIndex];
            if (currentCvv != null && !EncryptionUtil.isBase64Encoded(creditCard.getCvv())) {
                try {
                    String encryptedCvv = EncryptionUtil.encrypt(creditCard.getCvv());
                    creditCard.setCvv(encryptedCvv); // Update the credit card with the encrypted CVV
                    currentState[cvvIndex] = encryptedCvv;
                    log.info("Successfully encrypted updated CVV for credit card: {}", creditCard.getId());
                } catch (Exception e) {
                    log.error("Error encrypting updated CVV for credit card: {}", creditCard.getId(), e);
                }
            }
        } else {
            log.warn("CVV property not found for credit card: {}", creditCard.getId());
        }
    }

    private int getCvvIndex(String[] propertyNames) {
        for (int i = 0; i < propertyNames.length; i++) {
            if ("cvv".equals(propertyNames[i])) {
                return i;
            }
        }
        return -1;
    }
}