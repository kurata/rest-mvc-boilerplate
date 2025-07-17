#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "bpuser" --dbname "bp_database" <<-EOSQL
	CREATE SCHEMA IF NOT EXISTS bp_database;
	ALTER USER bpuser SET search_path TO bp_database;
	GRANT ALL PRIVILEGES ON DATABASE bp_database TO bpuser;
	GRANT ALL PRIVILEGES ON SCHEMA bp_database TO bpuser;
EOSQL
