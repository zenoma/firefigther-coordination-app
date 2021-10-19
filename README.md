# PA Project 

## Requirements

- Node 14.15.0+.
- Java 11 (tested with AdoptOpenJDK 11).
- Maven 3.6+.
- MySQL 8+.

## Database creation

```
Start Mysql server if not running (e.g. mysqld).

mysqladmin -u root create fireproject -p
mysqladmin -u root create fireprojecttest -p

mysql -u root -p
    CREATE USER 'fireuser'@'localhost' IDENTIFIED BY 'fireuser';
    GRANT ALL PRIVILEGES ON fireproject.* to 'fireuser'@'localhost' WITH GRANT OPTION;
    GRANT ALL PRIVILEGES ON fireprojecttest.* to 'fireuser'@'localhost' WITH GRANT OPTION;
    exit
```

## Run

```
cd backend
mvn sql:execute (only first time to create tables)
mvn spring-boot:run

cd frontend
npm install (only first time to download libraries)
npm start
```
