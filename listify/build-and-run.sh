#!/bin/bash

# Navigate to the parent directory
cd ..

# Run Maven clean install
mvn clean install

# Check if the clean install was successful
if [ $? -ne 0 ]; then
	echo "Maven clean install failed. Exiting."
	exit 1
fi

# Navigate to the core directory
cd core

# Run the Spring Boot application
mvn spring-boot:run
