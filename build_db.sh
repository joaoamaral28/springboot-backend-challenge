#!/bin/shell
#set -x #echo on

# Script assumes mysql is already present as an environment variable

echo "Installing missing python dependences..."
pip install -r client_requirements.txt

echo "Setting up MySQL database..."

db_host="localhost"
main_db="db_challenge"
root_user="root"
root_pw="root"
spring_user="springuser"
spring_pw="springpw"

BIN_MYSQL=$(where mysql)
#echo $BIN_MYSQL

"$BIN_MYSQL" -h localhost -u "$root_user" -p"$root_pw" -e "CREATE DATABASE $main_db;
CREATE USER '${spring_user}'@'%' IDENTIFIED BY '${springpw}';
GRANT ALL ON ${main_db}.* to '${spring_user}'@'%';"

echo "Done!"

$SHELL