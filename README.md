# sispl-busca-parametros-mock

Quarkus project that behaves like a very small SISPL mock for the `BUSCA_PARAMETRO_JOGOS` flow.

It consumes requests from MQ and replies with a `SERVICO_SAIDA` payload on the response queue. The reply is sent with the original request `MsgId`, which is what SILCE uses to match `BUSCA_PARAMETRO_JOGOS` responses.

## What It Does

- Connects to IBM MQ in client mode.
- Reads messages from `LQ.REQ.SISPLC.BUSCA_PARAMETROS`.
- Picks the reply queue from `ReplyToQ` when present, otherwise uses `LQ.RSP.SISPL.BUSCA_PARAMETROS`.
- Sends a SISPL XML `SERVICO_SAIDA` with:
  - `messageId = original request messageId`
  - `correlationId = original request messageId`
  - `CCSID = 37`
  - `Encoding = 785`

## Payload Options

You can drive the reply in two ways:

1. `SISPL_JSON_FILE=/path/to/parametros.json`
   The app will convert the cart/simulation JSON into SISPL MQ XML.

2. `SISPL_RESPONSE_FILE=/path/to/response.xml`
   The app will send that XML as-is.

If neither is set, it falls back to the bundled `src/main/resources/sample-response.xml`.

## Important Limitation

`Mais Milionária` is lossy in this old MQ contract. The SISPL XML type only exposes `VALOR_APOSTA` with `NU_PALPITES` and `VALOR`, so the JSON detail that varies by `numeroTrevos` cannot be represented 1:1.

The generator still emits a valid XML reply, but trevo-specific pricing is flattened.

## Docker Setup

These defaults match `/Users/cabutchei/Documents/vs_code/caixa/docker-was-silce/compose.yaml`:

- Queue manager: `BRD3`
- Host: `localhost`
- Port: `1414`
- Channel: `BRD3.SVRCONN.SILCE`
- Request queue: `LQ.REQ.SISPLC.BUSCA_PARAMETROS`
- Response queue: `LQ.RSP.SISPL.BUSCA_PARAMETROS`

## Run

```bash
cd /Users/cabutchei/Documents/vs_code/caixa/src/silce/utils/sispl-busca-parametros-mock
mvn package
```

Run the packaged Quarkus app:

```bash
cd /Users/cabutchei/Documents/vs_code/caixa/src/silce/utils/sispl-busca-parametros-mock
java -jar target/quarkus-app/quarkus-run.jar
```

Using the JSON file you already have:

```bash
cd /Users/cabutchei/Documents/vs_code/caixa/src/silce/utils/sispl-busca-parametros-mock
SISPL_JSON_FILE=/Users/cabutchei/Downloads/parametros.json \
java -jar target/quarkus-app/quarkus-run.jar
```

Using a fixed XML file instead:

```bash
cd /Users/cabutchei/Documents/vs_code/caixa/src/silce/utils/sispl-busca-parametros-mock
SISPL_RESPONSE_FILE=/absolute/path/response.xml \
java -jar target/quarkus-app/quarkus-run.jar
```

Run one message and exit:

```bash
SISPL_RUN_ONCE=true java -jar target/quarkus-app/quarkus-run.jar
```

Run in dev mode:

```bash
cd /Users/cabutchei/Documents/vs_code/caixa/src/silce/utils/sispl-busca-parametros-mock
mvn quarkus:dev
```

## Send XML Directly To MQ

If you want to inject the `SERVICO_SAIDA` XML directly onto MQ, use:

```bash
cd /Users/cabutchei/Documents/vs_code/caixa/src/silce/utils/sispl-busca-parametros-mock
scripts/send-response-xml.sh
```

Useful variants:

```bash
scripts/send-response-xml.sh --xml-file /absolute/path/resposta.xml
scripts/send-response-xml.sh --json-file /absolute/path/parametros.json
scripts/send-response-xml.sh --message-id 414D5120425244332020202020202020DAEFE069094F0040
scripts/send-response-xml.sh --queue LQ.RSP.SISPL.BUSCA_PARAMETROS
```

When `--message-id` is supplied and `--correlation-id` is omitted, the script uses the same hex value for both fields. This is the useful mode when SILCE is waiting for a `BUSCA_PARAMETRO_JOGOS` response tied to an existing request.

## Main Environment Variables

- `SISPL_MQ_HOST`
- `SISPL_MQ_PORT`
- `SISPL_MQ_CHANNEL`
- `SISPL_MQ_QUEUE_MANAGER`
- `SISPL_MQ_USER`
- `SISPL_MQ_PASSWORD`
- `SISPL_REQUEST_QUEUE`
- `SISPL_RESPONSE_QUEUE`
- `SISPL_JSON_FILE`
- `SISPL_RESPONSE_FILE`
- `SISPL_RUN_ONCE`
- `SISPL_PREFER_REPLY_TO`
- `SISPL_INCLUDE_PROXIMO_CONCURSO`

## Notes

- The JSON-to-XML generator intentionally ignores non-MQ fields such as `valorLimiteDiario`, `modalidadesDisponiveisCotas`, and other cart-only metadata.
- `INSTANTANEA` is skipped because it does not fit the `busca_parametros_jogos` SISPL MQ schema used by SILCE.
- The application loads `.env` from the project root automatically. Exported process environment variables still take precedence over `.env`.
- If startup fails with IBM MQ reason code `2035`, the MQ channel credentials can connect but do not have authority to open the configured queues. Fix the local MQ permissions or use a user with queue access.
