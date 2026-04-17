#!/usr/bin/env bash
set -euo pipefail

QMGR_NAME="${MQ_QMGR_NAME:-BRD3}"
MQ_PRINCIPAL="${IBM_MQ_USERNAME:-SLCEBD01}"
MQSC_SCRIPT="/etc/mqm/10-create-silce-queues.mqsc"

runmqdevserver &
mq_pid=$!

cleanup() {
  kill -TERM "$mq_pid" 2>/dev/null || true
  wait "$mq_pid" 2>/dev/null || true
}

trap cleanup TERM INT

for _ in $(seq 1 120); do
  if dspmq | grep -q "QMNAME(${QMGR_NAME}).*STATUS(Running)"; then
    break
  fi
  sleep 2
done

if ! dspmq | grep -q "QMNAME(${QMGR_NAME}).*STATUS(Running)"; then
  echo "Queue manager ${QMGR_NAME} did not reach Running state in time" >&2
  exit 1
fi

runmqsc "${QMGR_NAME}" < "${MQSC_SCRIPT}"

printf "ALTER QMGR CONNAUTH(' ')\nREFRESH SECURITY TYPE(CONNAUTH)\n" | runmqsc "${QMGR_NAME}"

/opt/mqm/bin/setmqaut -m "${QMGR_NAME}" -t qmgr -p "${MQ_PRINCIPAL}" +connect +inq +dsp

while read -r queue_name; do
  [ -n "${queue_name}" ] || continue
  /opt/mqm/bin/setmqaut -m "${QMGR_NAME}" -t queue -n "${queue_name}" -p "${MQ_PRINCIPAL}" +get +put +browse +inq +dsp
done < <(printf "DIS QLOCAL(*)\nEND\n" | runmqsc "${QMGR_NAME}" | awk -F"[()]" '/QUEUE\(/ {print $2}' | sort -u)

/opt/mqm/bin/setmqaut -m "${QMGR_NAME}" -t queue -n SYSTEM.DEFAULT.MODEL.QUEUE -p "${MQ_PRINCIPAL}" +get +put +browse +inq +dsp

wait "${mq_pid}"
