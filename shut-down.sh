echo "Cleaning up enviroment..."
echo "Shuting down docker swarm"
docker-compose down
echo "Removing MySQL database container..."
docker stop mariadb
docker rm mariadb
echo "Removing application network..."
docker network rm library_net
