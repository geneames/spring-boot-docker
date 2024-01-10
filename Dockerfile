FROM gradle:jdk17-focal

WORKDIR /home/gradle

COPY --chown=gradle:gradle settings.gradle $WORKDIR
COPY --chown=gradle:gradle ./app $WORKDIR

RUN gradle build --no-daemon

EXPOSE 8080

RUN mkdir /app

COPY $WORKDIR/app/build/libs/app.jar /app/app.jar

ENTRYPOINT ["java","-jar","/app/app.jar"]

