# library
Library Management System

Back-end server for an e-library management application

## Requirements
- MySQL (Set MySQL password to `mysql` for user, root);
- Docker
- Kubernetes (Optional)

## Development environement
- Initialize the MySQL database with the library.sql file by running
	`mysql -uroot -pmysql -h172.16.238.2 < "library.sql"`
Run `./gradlew bootRun` to run the system on a developement environement.


## Production environment

### Using docker swarm
- Run `bash start-up.sh` to start up the entire library system
This script starts MySQL, the server and the User Interfaces using docker swarm

### Using Kubernetes
	> Run `kubectl apply -f <file>` Subtitude file for the file name.
- Run the mysql.yml file to start MySQL
- Run `kubectl get services` to get the ip address of the MySQL host. Then initialize the database by running
	> `mysql -uroot -pmysql -h<mysql host IP> < "library.sql"`

- Update the library-server.yml with the new MySQL ip address, then run the library-server.yml file.
- Run `kubectl get services` to get the ip address of the server.
- Use the server's ip address to update the  admin panel and web app. Then, rebuild the images and push to docker hub
- Finally run the library-app.yml and library-admin-app.yml files.

	**N.B**
		Make sure you have Kubernetes configured using minikube or you could use Google Container Engine

**N.B:**
In other to update the docker images, re-build the admin panel and web app using Docker.
Push the images to docker hub then, update the images in the docker-compose file.