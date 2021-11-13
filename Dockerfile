FROM openjdk:8-jdk-alpine
VOLUME /tmp

#CMD gradle build
#CMD mkdir -p build/dependency2
#CMD cd build/dependency
#CMD jar -xf ../libs/*.jar

ARG DEPENDENCY=build/dependency
COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY ${DEPENDENCY}/META-INF /app/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /app

RUN apk add --update ttf-dejavu && rm -rf /var/cache/apk/*

EXPOSE 8100

ENTRYPOINT ["java","-cp","app:app/lib/*","it.vvfriva.VvfrivaApplication"]