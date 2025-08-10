#!/bin/bash

echo "Starting Municipality Complaint Management System..."

# Start Eureka Server
echo "Starting Eureka Server on port 8761..."
cd eureka-server
nohup mvn spring-boot:run > eureka-server.log 2>&1 &
EUREKA_PID=$!
echo "Eureka Server started with PID: $EUREKA_PID"

# Wait for Eureka to start
echo "Waiting for Eureka Server to start..."
sleep 30

# Start Complaint Service
echo "Starting Complaint Service on port 8082..."
cd ../complaint-service
nohup mvn spring-boot:run > complaint-service.log 2>&1 &
COMPLAINT_PID=$!
echo "Complaint Service started with PID: $COMPLAINT_PID"

echo "All services started successfully!"
echo "Eureka Dashboard: http://localhost:8761"
echo "Complaint Service Health: http://localhost:8082/health"
echo ""
echo "Process IDs:"
echo "Eureka Server: $EUREKA_PID"
echo "Complaint Service: $COMPLAINT_PID"
echo ""
echo "To stop all services, run: ./stop-all-services.sh"