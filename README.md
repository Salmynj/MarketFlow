MarketFlow - simple marketplace sample

Quick start

1. Configure the database connection in `src/main/resources/META-INF/persistence.xml`:
   - URL: jdbc:mysql://localhost:3306/marketflow_db
   - user/password: currently 'root'/'root' (change for your environment)

2. Build with Maven:

```bash
mvn package
```

3. Deploy the generated WAR in `target/MarketFlow-0.0.1-SNAPSHOT.war` to a Jakarta EE compatible container (Tomcat 10+).

Endpoints
- /publicaciones [GET] - lista el catálogo
- /publicaciones [POST] - crea una publicación
- /mensaje [POST] - envía un mensaje

Notes
- The project uses EclipseLink as JPA provider and will create or extend tables on startup (eclipselink.ddl-generation=create-or-extend-tables).
- Update dependencies in `pom.xml` if you need specific versions for your runtime.
