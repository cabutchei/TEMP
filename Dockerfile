FROM icr.io/appcafe/websphere-traditional:8.5.5.27-ubi8-amd64

ENV UPDATE_HOSTNAME=true
ENV ENABLE_BASIC_LOGGING=true

COPY --chown=was:root dist/silce.ear /work/app/silce.ear
COPY --chown=was:root docker/was/config/10-install-silce.py /work/config/10-install-silce.py

EXPOSE 9080 9043 9443 8880 9633 2809

RUN /work/configure.sh
