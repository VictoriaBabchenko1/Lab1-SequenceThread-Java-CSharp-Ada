FROM debian:bullseye

RUN apt-get update && \
    apt-get install -y gnat && \
    apt-get clean

WORKDIR /app
COPY thread_demo.adb .

RUN gnatmake thread_demo.adb

CMD ["./thread_demo"]
