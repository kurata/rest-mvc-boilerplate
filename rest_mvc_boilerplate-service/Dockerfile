FROM openjdk:11

ARG JAR_FILE
ARG PROFILE=stand-alone

WORKDIR /app

ENV spring_profiles_active=${PROFILE}
ENV jar_file=${JAR_FILE}

ADD ${JAR_FILE} application.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "application.jar" ]
