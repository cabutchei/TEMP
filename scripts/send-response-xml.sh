#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
cd "$ROOT_DIR"

usage() {
  cat <<'EOF'
Usage:
  scripts/send-response-xml.sh [--queue QUEUE] [--xml-file FILE] [--json-file FILE] [--message-id HEX] [--correlation-id HEX]

Defaults:
  - queue: value from SISPL_RESPONSE_QUEUE in .env
  - payload: generated from current .env settings, or sample-response.xml when no file is configured
  - correlation id: same as message id when message id is provided and correlation id is omitted

Examples:
  scripts/send-response-xml.sh
  scripts/send-response-xml.sh --xml-file /tmp/resposta.xml
  scripts/send-response-xml.sh --message-id 414D5120425244332020202020202020DAEFE069094F0040
  scripts/send-response-xml.sh --queue LQ.RSP.SISPL.BUSCA_PARAMETROS --json-file /tmp/parametros.json
EOF
}

while [[ $# -gt 0 ]]; do
  case "$1" in
    --queue)
      export SISPL_SEND_QUEUE="$2"
      shift 2
      ;;
    --xml-file)
      export SISPL_RESPONSE_FILE="$2"
      unset SISPL_JSON_FILE || true
      shift 2
      ;;
    --json-file)
      export SISPL_JSON_FILE="$2"
      unset SISPL_RESPONSE_FILE || true
      shift 2
      ;;
    --message-id)
      export SISPL_SEND_MESSAGE_ID_HEX="$2"
      shift 2
      ;;
    --correlation-id)
      export SISPL_SEND_CORRELATION_ID_HEX="$2"
      shift 2
      ;;
    --help|-h)
      usage
      exit 0
      ;;
    *)
      echo "Unknown option: $1" >&2
      usage >&2
      exit 1
      ;;
  esac
done

mvn -q -DskipTests compile exec:java -Dexec.mainClass=br.gov.caixa.mock.sispl.buscaparametros.ResponseXmlSender
