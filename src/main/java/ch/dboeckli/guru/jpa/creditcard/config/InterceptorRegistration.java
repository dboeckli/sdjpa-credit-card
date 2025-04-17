package ch.dboeckli.guru.jpa.creditcard.config;

import ch.dboeckli.guru.jpa.creditcard.interceptor.EncryptionInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class InterceptorRegistration implements HibernatePropertiesCustomizer {

    private final EncryptionInterceptor interceptor;

    @Override
    public void customize(Map<String, Object> hibernateProperties) {
        hibernateProperties.put("hibernate.session_factory.interceptor", interceptor);
    }
}