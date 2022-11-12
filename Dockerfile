FROM gradle:jdk17 as build

WORKDIR /tourguide-back

COPY build.gradle ./
COPY settings.gradle ./
COPY src/ src/

RUN gradle bootJar



FROM openjdk:17-slim

WORKDIR /tourguide-back

COPY --from=build /tourguide-back/build/libs/tourguide-*.jar ./

CMD ["sh", "-c", "java -jar tourguide-*.jar"]

