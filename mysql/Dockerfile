# Use the official MySQL image as a parent image
FROM mysql:8.0

# Set environment variables for MySQL
ENV MYSQL_DATABASE=microservicios
ENV MYSQL_USER=user
ENV MYSQL_PASSWORD=root
ENV MYSQL_ROOT_PASSWORD=root

# Copy the initialization SQL script into the container
COPY init.sql /docker-entrypoint-initdb.d/
