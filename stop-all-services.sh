#!/bin/bash

echo "Stopping Municipality Complaint Management System..."

# Kill all Java processes running Spring Boot
echo "Stopping all Spring Boot services..."
pkill -f "spring-boot:run"

# Additional cleanup for specific ports
echo "Cleaning up processes on ports 8761, 8081, and 8082..."
lsof -ti:8761 | xargs -r kill -9
lsof -ti:8081 | xargs -r kill -9
lsof -ti:8082 | xargs -r kill -9

echo "All services stopped successfully!"