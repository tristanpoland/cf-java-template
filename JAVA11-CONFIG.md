# Alternative configuration for Java 11 environments
# If your Cloud Foundry doesn't support Java 17, copy these settings

## 1. Update pom.xml
Change:
```xml
<java.version>17</java.version>
```
To:
```xml
<java.version>11</java.version>
```

## 2. Update system.properties
Change:
```
java.runtime.version=17
```
To:
```
java.runtime.version=11
```

## 3. Update manifest.yml
Change:
```yaml
JBP_CONFIG_OPEN_JDK_JRE: '{jre: {version: 17.+}}'
```
To:
```yaml
JBP_CONFIG_OPEN_JDK_JRE: '{jre: {version: 11.+}}'
```

## 4. Rebuild and deploy
```bash
mvn clean package
cf push
```