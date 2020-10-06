FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY build/libs/ectosense-0.0.1-SNAPSHOT.jar app.jar
RUN sh -c 'touch /app.jar'
EXPOSE 9443
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/urandom -jar /app.jar" ]