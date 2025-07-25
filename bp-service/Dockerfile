FROM amazoncorretto:17

ARG JAR_FILE
ENV PROFILE="stand-alone"

EXPOSE 8443

RUN mkdir bp-server
ADD ${JAR_FILE} /bp-server/bp-server.jar
WORKDIR /bp-server
ENTRYPOINT ["/usr/bin/java", "-jar", "bp-server.jar", "--spring.profiles.active=${PROFILE}"]
