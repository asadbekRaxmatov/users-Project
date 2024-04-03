FROM ubuntu:latest
LABEL authors="asadb"

ENTRYPOINT ["top", "-b"]