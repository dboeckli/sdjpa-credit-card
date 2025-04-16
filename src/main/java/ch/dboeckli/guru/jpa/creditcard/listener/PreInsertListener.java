package ch.dboeckli.guru.jpa.creditcard.listener;

import ch.dboeckli.guru.jpa.creditcard.domain.CreditCard;
import ch.dboeckli.guru.jpa.creditcard.util.EncryptionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.event.spi.PreInsertEvent;
import org.hibernate.event.spi.PreInsertEventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PreInsertListener implements PreInsertEventListener {

    @Override
    public boolean onPreInsert(PreInsertEvent event) {
        log.info("Pre-insert event for entity: {}", event.getEntity());
        if (event.getEntity() instanceof CreditCard creditCard) {
            encryptCvv(creditCard, event);
        }
        return false;
    }

    private void encryptCvv(CreditCard creditCard, PreInsertEvent event) {
        try {
            String encryptedCvv = EncryptionUtil.encrypt(creditCard.getCvv());

            Object[] state = event.getState();
            int cvvIndex = getCvvIndex(event);

            if (cvvIndex != -1) {
                state[cvvIndex] = encryptedCvv;
                creditCard.setCvv(encryptedCvv);
                log.info("Successfully encrypted CVV for credit card: {}", creditCard);
            } else {
                log.warn("CVV property not found for credit card: {}", creditCard);
            }
        } catch (Exception e) {
            log.error("Error encrypting CVV for credit card: {}", creditCard, e);
        }
    }

    private int getCvvIndex(PreInsertEvent event) {
        return getPropertyIndex(event, "cvv");
    }

    private int getPropertyIndex(PreInsertEvent event, String propertyName) {
        String[] propertyNames = event.getPersister().getPropertyNames();
        for (int i = 0; i < propertyNames.length; i++) {
            if (propertyNames[i].equals(propertyName)) {
                return i;
            }
        }
        return -1;
    }
}
