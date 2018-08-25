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
- Run `bash start-up.sh` to run the start up the entire library system

**N.B:** Make sure you build the admin panel and web app using docker.