#!/bin/bash
docker stop task-mysql
docker rm task-mysql
docker run --name task-mysql -e MYSQL_ROOT_PASSWORD=123-abc -p 3306:3306 -v task-mysql-data:/var/lib/mysql -d mysql
sleep 5
mysql --host=127.0.0.1 --port=3306 -u root -p
