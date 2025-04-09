FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY . /app
RUN ./mvnw clean install -DskipTests
EXPOSE 8080
ENV SPRING_DEVTOOLS_REMOTE_SECRET=mysecret
ENV SPRING_DEVTOOLS_RESTART_ENABLED=true
CMD ["./mvnw", "spring-boot:run", "-Dspring-boot.run.profiles=dev", "-Dspring-boot.run.jvmArguments='-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005'"]