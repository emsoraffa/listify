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

# Ask the user if they want to run in development mode
echo "Do you want to run in development mode? (yes/no)"
read dev_mode

# Check user input and set the Spring profile accordingly
if [ "$dev_mode" == "yes" ]; then
  echo "Starting the application in development mode..."
  mvn spring-boot:run -Dspring-boot.run.profiles=dev
else
  echo "Starting the application in default mode..."
  mvn spring-boot:run
fi
