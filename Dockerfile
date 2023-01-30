FROM openjdk:11
MAINTAINER Sergiy Bichurin
EXPOSE 8080
ADD target/ip-ranges-docker.jar ip-ranges-docker.jar
ENTRYPOINT ["java", "-jar", "/ip-ranges-docker.jar"]