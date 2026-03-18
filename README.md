# silce-liberty-ear

EAR playground extracted from `silce` with focus on:
- Entities: `Compra`, `Aposta`, `ApostaComprada`
- DAOs: `CompraDAO`, `ApostaDAO`, `ApostaCompradaDAO` (+ local interfaces)
- Supporting domain/util/DAO infrastructure needed for those classes

## Build

```bash
mvn clean package
```

## Run on Liberty

Install the reactor first (so the EJB module is available to the EAR module):

```bash
mvn clean install -DskipTests
```

Then from `silce-liberty-ear/silce-liberty-ear`:

```bash
mvn liberty:run
```

Before running, configure datasource `silceDS` in:

- `silce-liberty-ear/src/main/liberty/config/server.xml`

A DB2 sample block is included and commented in `server.xml`.
