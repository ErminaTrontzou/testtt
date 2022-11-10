FROM gradle:jdk17

WORKDIR /app

COPY ./ ./

# RUN gradle build

CMD ["gradle", "--no-daemon", "bootRun"]

