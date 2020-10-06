FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp
ADD fwcm-config-0.1*.jar app.jar
RUN sh -c 'touch /app.jar'
EXPOSE 9443
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/urandom -jar /app.jar" ]