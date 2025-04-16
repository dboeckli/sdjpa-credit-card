# Spring Data JPA Wordpress

This repository contains source code examples to support my course Spring Data JPA and Hibernate Beginner to Guru

## JPA Interceptors, Listener, Callbacks

This project demonstrates the functionality of JPA/Hibernate Interceptors, Listeners, and Callbacks:

1. **JPA Entity Listeners**: These allow the execution of callback methods at specific lifecycle events of an entity.
   Example: `@EntityListeners(AuditTrailListener.class)`

2. **JPA Callbacks**: Methods defined directly in the entity class that are called on certain events.
   Examples: `@PrePersist`, `@PostLoad`, `@PreUpdate`

3. **Hibernate Interceptors**: Offer more comprehensive possibilities for manipulating entities during various database operations.
   Example: Implementation of the `Interceptor` interface

4. **JPA Converters**: Allow for custom conversion between database column values and entity attribute values.
   Example: Implementation of the `AttributeConverter` interface

These mechanisms are used in the project to:
- Automatically set timestamps for creation and updates
- Create audit trails
- Validate or transform data before saving
- Execute additional logic during database operations

For more information please refer to the following documents in the `doc` folder:

- [ListenersAndInterceptors](doc/ListenersAndInterceptors.pdf): This document provides a comprehensive overview of database transactions.


## Flyway

To enable Flyway in the MySQL profile, override the following properties when starting the application:
- `spring.flyway.enabled = true`
- `spring.docker.compose.file = compose-mysql.yaml`

This profile starts MySQL on port 3306 using the Docker Compose file `compose-mysql-.yaml`.

## Docker

Docker Compose file initially use the startup script located in `src/scripts`. These scripts create the database and users.

## Kubernetes

### Generate Config Map for mysql init script

When updating 'src/scripts/init-mysql-mysql.sql', apply the changes to the Kubernetes ConfigMap:
```bash
kubectl create configmap mysql-init-script --from-file=init.sql=src/scripts/init-mysql.sql --dry-run=client -o yaml | Out-File -Encoding utf8 k8s/mysql-init-script-configmap.yaml
```

### Deployment

To deploy all resources:
```bash
kubectl apply -f k8s/
```

To remove all resources:
```bash
kubectl delete -f k8s/
```

Check
```bash
kubectl get deployments -o wide
kubectl get pods -o wide
```

## Running the Application
1. Choose between h2 or mysql for database schema management. (you can use one of the preconfigured intellij runners)
2. Start the application with the appropriate profile and properties.
3. The application will use Docker Compose to start MySQL and apply the database schema changes.