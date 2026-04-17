#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
ROOT_DIR="$(cd "${SCRIPT_DIR}/.." && pwd)"
MQ_CONTAINER="${1:-silce-mq}"
SCRIPT="${ROOT_DIR}/docker/was/mq/10-create-silce-queues.mqsc"
MQ_QMGR="${IBM_MQ_QUEUE_MANAGER:-BRD3}"
MQ_PRINCIPAL="${IBM_MQ_USERNAME:-SLCEBD01}"

docker exec -i "$MQ_CONTAINER" bash -lc "runmqsc ${MQ_QMGR}" < "$SCRIPT"
docker exec "$MQ_CONTAINER" bash -lc "printf \"STOP LISTENER('BRD3.LISTENER.TCP')\nDELETE LISTENER('BRD3.LISTENER.TCP')\nALTER QMGR CONNAUTH(' ')\nREFRESH SECURITY TYPE(CONNAUTH)\nEND\n\" | runmqsc ${MQ_QMGR}"
docker exec "$MQ_CONTAINER" bash -lc '
  set -e
  /opt/mqm/bin/setmqaut -m "'"${MQ_QMGR}"'" -t qmgr -p "'"${MQ_PRINCIPAL}"'" +connect +inq +dsp
  for q in $(printf '"'"'DIS QLOCAL(*)\nEND\n'"'"' | runmqsc "'"${MQ_QMGR}"'" | awk -F"[()]" '"'"'/QUEUE\(/ {print $2}'"'"' | sort -u); do
    /opt/mqm/bin/setmqaut -m "'"${MQ_QMGR}"'" -t queue -n "$q" -p "'"${MQ_PRINCIPAL}"'" +get +put +browse +inq +dsp
  done
  /opt/mqm/bin/setmqaut -m "'"${MQ_QMGR}"'" -t queue -n SYSTEM.DEFAULT.MODEL.QUEUE -p "'"${MQ_PRINCIPAL}"'" +get +put +browse +inq +dsp
'

echo "MQ queue definitions applied to ${MQ_CONTAINER}."
