#!/bin/bash

echo "shutting down docker instances..."

docker stop rest_mvc_boilerplate_postgres_local
docker stop rest_mvc_boilerplate_local
