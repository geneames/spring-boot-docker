FROM gradle:jdk17-focal

WORKDIR /home/gradle

COPY --chown=gradle:gradle settings.gradle $WORKDIR
COPY --chown=gradle:gradle ./app $WORKDIR

RUN gradle build --no-daemon

EXPOSE 8080

RUN mkdir -p /app/libs

COPY $WORKDIR/app/build/libs/app.jar /app/app.jar

RUN tar -xvf build/distributions/spring-docker.tar -C /app/libs/

ENV CLASSPATH="/app/libs/spring-docker/lib/*:/app/app.jar"

ENTRYPOINT java -cp "$CLASSPATH" "io.sema.shuffle.App"
