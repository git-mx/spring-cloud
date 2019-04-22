FROM hub.c.163.com/library/java:8-alpine

ADD server/target/*.war app.war

EXPOSE 9001

ENTRYPOINT ["java", "-jar", "app.war"]