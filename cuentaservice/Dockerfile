# Use the official Oracle JDK 17 image as a parent image
FROM oraclelinux:7-slim

# Install JDK 17
RUN yum -y install oracle-release-el7
RUN yum-config-manager --enable ol7_optional_latest
RUN yum -y install java-17-oracle

# Copy the application JAR file into the container
COPY target/cuenta-movements.jar /cuenta-movements.jar

# Set the entry point to run the application
ENTRYPOINT ["java", "-jar", "/cuenta-movements.jar"]
