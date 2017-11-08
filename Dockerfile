FROM maven:3.3-jdk-8-onbuild
FROM java:8
CMD ["java","-jar","target/json-validator-0.1.jar"]
EXPOSE 80