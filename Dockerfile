FROM maven:3.3-jdk-8-onbuild
CMD ["java","-jar","target/json-validator-0.1.jar"]
EXPOSE 80