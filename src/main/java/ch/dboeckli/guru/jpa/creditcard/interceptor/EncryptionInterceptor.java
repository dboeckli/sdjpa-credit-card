package ch.dboeckli.guru.jpa.creditcard.interceptor;

import ch.dboeckli.guru.jpa.creditcard.domain.CreditCard;
import ch.dboeckli.guru.jpa.creditcard.util.EncryptionUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.CallbackException;
import org.hibernate.Interceptor;
import org.hibernate.type.Type;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EncryptionInterceptor implements Interceptor {

    @Override
    public boolean onLoad(Object entity, Object id, Object[] state, String[] propertyNames, Type[] types) {
        if (entity instanceof CreditCard) {
            for (int i = 0; i < propertyNames.length; i++) {
                if ("creditCardNumber".equals(propertyNames[i])) {
                    String encryptedValue = (String) state[i];
                    if (encryptedValue != null && !encryptedValue.isEmpty()) {
                        log.info("Decrypting credit card number: {}", encryptedValue);
                        state[i] = EncryptionUtil.decrypt(encryptedValue);
                        return true;
                    }
                    break;
                }
            }
        }
        return false;
    }

    @Override
    public boolean onPersist(Object entity, Object id, Object[] state, String[] propertyNames, Type[] types) throws CallbackException {
        if (entity instanceof CreditCard) {
            for (int i = 0; i < propertyNames.length; i++) {
                if ("creditCardNumber".equals(propertyNames[i])) {
                    String currentValue = (String) state[i];
                    if (currentValue != null && !currentValue.isEmpty()) {
                        log.info("Encrypting credit card number: {}", currentValue);
                        state[i] = EncryptionUtil.encrypt(currentValue);
                        return true;
                    }
                    break;
                }
            }
        }
        return false;
    }

    @Override
    public boolean onFlushDirty(Object entity, Object id, Object[] currentState, Object[] previousState, String[] propertyNames, Type[] types) {
        if (entity instanceof CreditCard) {
            boolean modified = false;
            for (int i = 0; i < propertyNames.length; i++) {
                if ("creditCardNumber".equals(propertyNames[i])) {
                    String currentValue = (String) currentState[i];
                    if (currentValue != null && !currentValue.isEmpty()) {
                        log.info("Encrypting credit card number: {}", currentValue);
                        currentState[i] = EncryptionUtil.encrypt(currentValue);
                        modified = true;
                    }
                    break;
                }
            }
            return modified;
        }
        return false;
    }

}
