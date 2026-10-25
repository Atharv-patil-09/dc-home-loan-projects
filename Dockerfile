# BANKCORE Distributed Banking Platform - Docker Deployment
FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

# Install python for web server
RUN apk add --no-cache python3 bash

# Copy project files
COPY . .

# Expose Web (3000) and Socket RPC (5000)
EXPOSE 3000 5000 6000 1099

# Default entrypoint starts the web console
CMD ["python3", "-m", "http.server", "3000", "--directory", "frontend"]
