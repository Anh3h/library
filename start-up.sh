#!/bin/sh
echo "Setting up environment..."

echo "Creating application network..."
docker network create --driver bridge --subnet=172.16.238.0/16 library_net

echo "Creating MySQL volume."
docker volume create --name mysql_vol

echo "Starting MySQL server."
docker run -d --network library_net --ip 172.16.238.2 --volume mysql_vol:/var/lib/mysql --name mariadb --env MYSQL_ROOT_PASSWORD=mysql mariadb:latest
while ! nc -z 172.16.238.2 3306; do
  echo "MySQL is unavailable - sleeping"
  sleep 1
done

if ! mysql -uroot -pmysql -h172.16.238.2 -e 'use library'; then
  echo "MySQL is ready, initializing database..."
  mysql -uroot -pmysql -h172.16.238.2 < "library.sql"
fi

echo "Starting entire e-library system"
docker-compose up --build