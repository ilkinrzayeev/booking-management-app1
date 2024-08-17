FROM openjdk:11
COPY target/booking-management-system-1.0-SNAPSHOT.jar booking-app.jar
ENTRYPOINT ["java", "-jar", "booking-app.jar"]