#!/usr/bin/env bash

PROJECT_NAME="rest_mvc_boilerplate"
CONTAINER_NAME="rest_mvc_boilerplate_postgres_local"
USER_NAME="rest_mvc_boilerplate_user"
DB_NAME="rest_mvc_boilerplate"
SCHEMA="rest_mvc_boilerplate"
script_dir=$(cd "$(dirname "${BASH_SOURCE[0]}")" &>/dev/null && pwd -P)

create_user()
{
	RESULT=$(docker exec -it $CONTAINER_NAME psql -U postgres $DB_NAME -c "SELECT 1 FROM pg_roles WHERE rolname='$USER_NAME'" | grep -a "1 row" )
	if [ -z "$RESULT" ]
	then
			echo "$USER_NAME not found. Creating: $USER_NAME"
			docker exec -it $CONTAINER_NAME psql -U postgres $DB_NAME -c "CREATE USER $USER_NAME WITH ENCRYPTED PASSWORD 'password'"
			docker exec -it $CONTAINER_NAME psql -U postgres $DB_NAME -c "GRANT ALL PRIVILEGES ON DATABASE $DB_NAME TO $USER_NAME"
			docker exec -it $CONTAINER_NAME psql -U $USER_NAME $DB_NAME -c "CREATE SCHEMA IF NOT EXISTS $SCHEMA"
	else
			echo "$USER_NAME user exists. It's all good!"
	fi
}

docker-compose --project-name $PROJECT_NAME -f ./docker/docker-compose-stand-alone.yml up -d
echo "docker instance up ..."
sleep 20 # time up docker
create_user
echo "docker instance finish"
