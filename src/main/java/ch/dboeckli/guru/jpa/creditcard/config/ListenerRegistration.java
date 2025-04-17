package ch.dboeckli.guru.jpa.creditcard.config;

import ch.dboeckli.guru.jpa.creditcard.listener.PostLoadListener;
import ch.dboeckli.guru.jpa.creditcard.listener.PreInsertListener;
import ch.dboeckli.guru.jpa.creditcard.listener.PreUpdateListener;
import lombok.RequiredArgsConstructor;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.internal.SessionFactoryImpl;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.lang.NonNull;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ListenerRegistration implements BeanPostProcessor {

    private final PostLoadListener postLoadListener;
    private final PreInsertListener preInsertListener;
    private final PreUpdateListener preUpdateListener;

    @Override
    public Object postProcessBeforeInitialization(@NonNull Object bean, @NonNull String beanName) throws BeansException {
        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }

    @Override
    public Object postProcessAfterInitialization(@NonNull Object bean, @NonNull String beanName) throws BeansException {

        if (bean instanceof LocalContainerEntityManagerFactoryBean lemf){
            SessionFactoryImpl sessionFactory = (SessionFactoryImpl) lemf.getNativeEntityManagerFactory();
            EventListenerRegistry registry = sessionFactory.getServiceRegistry()
                .getService(EventListenerRegistry.class);

            registry.appendListeners(EventType.POST_LOAD, postLoadListener);
            registry.appendListeners(EventType.PRE_INSERT, preInsertListener);
            registry.appendListeners(EventType.PRE_UPDATE, preUpdateListener);
        }

        return bean;
    }
}