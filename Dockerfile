FROM localhost:5000/openjdk:11
MAINTAINER "Afshin.Parhizkari@gmail.com"
VOLUME /tmp
ARG JAR_FILE
COPY target/issuetracker.war issuetracker.war
ENTRYPOINT ["java","-jar","-Duser.timezone=Asia/Tehran","-Dspring.profiles.active=opr","/issuetracker.war"]
EXPOSE 8080 7725