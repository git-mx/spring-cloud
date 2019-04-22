FROM hub.c.163.com/library/java:8-alpine

ADD target/*.jar app.jar

#这里最好与application.yml里面写的server.port一致
EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]