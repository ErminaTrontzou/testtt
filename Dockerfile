FROM gradle:jdk17 as build

WORKDIR /tourguide-back

COPY build.gradle ./
COPY settings.gradle ./
COPY src/ src/

RUN mv src/main/resources/application.properties this_doesnt_work_if_were_overwriting_the_file_were_reading_apparently && \
    sed 's/spring.datasource.url.*/spring.datasource.url=jdbc:mysql:\/\/tourguide-db:3306\/tourguide/' this_doesnt_work_if_were_overwriting_the_file_were_reading_apparently |\
    sed 's/spring.datasource.username.*/spring.datasource.username=ermina/' |\
    sed 's/spring.datasource.password.*/spring.datasource.password=hypers/'\
    > src/main/resources/application.properties &&\
    rm this_doesnt_work_if_were_overwriting_the_file_were_reading_apparently

RUN gradle bootJar



FROM openjdk:17-slim

WORKDIR /tourguide-back

COPY --from=build /tourguide-back/build/libs/tourguide-*.jar ./

CMD ["sh", "-c", "java -jar tourguide-*.jar"]

