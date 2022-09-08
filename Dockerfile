FROM openjdk:8
EXPOSE 8080
RUN mkdir -p /app/
ADD build/libs/ejercicio-0.0.1-SNAPSHOT.war /app/ejercicio-0.0.1-SNAPSHOT.war
ENTRYPOINT ["java", "-jar", "/app/ejercicio-0.0.1-SNAPSHOT.war"]