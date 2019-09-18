#!/bin/sh
mvn clean package && docker build -t com.tech11/tech11 .
docker rm -f tech11 || true && docker run -d -p 8080:8080 -p 4848:4848 --name tech11 com.tech11/tech11 
