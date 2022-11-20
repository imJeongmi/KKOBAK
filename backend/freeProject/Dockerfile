FROM openjdk:8-jdk

WORKDIR .

COPY build/libs/freeProject-0.0.1-SNAPSHOT.jar application.jar

EXPOSE 8080

CMD ["java","-Duser.timezone=Asia/Seoul", "-jar", "application.jar"]
