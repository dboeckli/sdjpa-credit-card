# Spring Data JPA Wordpress

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

### Deployment with Kubernetes

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

### Deployment with Helm

Be aware that we are using a different namespace here (not default).

Go to the directory where the tgz file has been created after 'mvn install'

```powershell
cd target/helm/repo
```

unpack

```powershell
$file = Get-ChildItem -Filter sdjpa-credit-card-chart-*.tgz | Select-Object -First 1
tar -xvf $file.Name
```

install

```powershell
$APPLICATION_NAME = Get-ChildItem -Directory | Where-Object { $_.LastWriteTime -ge $file.LastWriteTime } | Select-Object -ExpandProperty Name
helm upgrade --install $APPLICATION_NAME ./$APPLICATION_NAME --namespace sdjpa-credit-card --create-namespace --wait --timeout 5m --debug
```

show logs

```powershell
kubectl get pods -n sdjpa-credit-card
```

replace $POD with pods from the command above

```powershell
kubectl logs $POD -n sdjpa-credit-card --all-containers
```

Show Endpoints

```powershell
kubectl get endpoints -n sdjpa-credit-card
```

test

```powershell
helm test $APPLICATION_NAME --namespace sdjpa-credit-card --logs
```

status

```powershell
helm status $APPLICATION_NAME --namespace sdjpa-credit-card
```

uninstall

```powershell
helm uninstall $APPLICATION_NAME  --namespace sdjpa-credit-card
```

delete all

```powershell
kubectl delete all --all -n sdjpa-credit-card
```

create busybox sidecar

```powershell
kubectl run busybox-test --rm -it --image=busybox:1.36 --namespace=sdjpa-credit-card --command -- sh
```

You can use the actuator rest call to verify via port 30080

## Running the Application

1. Choose between h2 or mysql for database schema management. (you can use one of the preconfigured intellij runners)
2. Start the application with the appropriate profile and properties.
3. The application will use Docker Compose to start MySQL and apply the database schema changes.

## Sandbox (local dev environment)

The sandbox is provisioned by the opencode-sandbox-kit and runs as a Docker container. It mounts this
repo, starts the agent, and connects the IntelliJ MCP server. The app runs on port `8080`; `compose-mysql.yaml`
provides MySQL.

Allow the kit source (GitHub without cloning):

```powershell
sbx settings set kit.allowedSources --% "[\"docker.io/\",\"github.com/dboeckli/\"]"
```

Start a new sandbox:

```powershell
sbx run opencode `
    --kit "git+https://github.com/dboeckli/opencode-sandbox-kit.git#dir=opencode-agent" `
    --template docker/sandbox-templates:opencode-docker-0.5.0 `
    --skills=off `
    --static-mcp idea `
    . `
    "C:\development\maven-repo:ro"
```

Start the sandbox with Kubernetes support:

```powershell
sbx run opencode `
    --kit "git+https://github.com/dboeckli/opencode-sandbox-kit.git#dir=opencode-agent" `
    --template docker/sandbox-templates:opencode-docker-0.5.0 `
    --skills=off `
    --static-mcp idea `
    . `
    "C:\development\maven-repo:ro" `
    "$env:USERPROFILE\.kube:ro"
```

Claude Code (Home) and Mammouth Code variants:

```powershell
sbx run claude `
    --kit "git+https://github.com/dboeckli/opencode-sandbox-kit.git#dir=opencode-agent" `
    --template docker/sandbox-templates:claude-code-docker-0.5.0 `
    --skills=off `
    --static-mcp idea `
    . `
    "C:\development\maven-repo:ro"
```

```powershell
sbx run "git+https://github.com/dboeckli/opencode-sandbox-kit.git#dir=mammouth-agent" `
    --skills=off `
    --static-mcp idea `
    . `
    "C:\development\maven-repo:ro"
```

### Start the app

Start MySQL (H2 needs no Docker):

```shell
docker compose -f compose-mysql.yaml up
```

Then run one of the IntelliJ run configurations (`.run/Spring6Application h2.run.xml` or the MySQL
one) or start via `./mvnw spring-boot:run -Dspring-boot.run.profiles=h2`.

### Sandbox build quirk

The sandbox mounts the repo via filesystem passthrough, which blocks symlinks — Spotless's `npm install`
(prettier) would fail with `EPERM` unless npm skips bin links. The kit sets `npm_config_bin_links=false`
globally, so no manual export is needed.
