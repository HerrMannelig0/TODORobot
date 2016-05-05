About database:
it is MySQL database, project uses version 5.1.38 of mySQL server connector and it is adviced to not change it due to possiblility
of throwing exception.
Before running program (linux users):
1. Open terminal, assuming that mysql-server and my-sql client are installed,
2. login into database:
    mysql -u USERNAME -pPASSWORD (or mysql -u USERNAME -p if you don't want to show your password)
    Default configuration is : USERNAME=root password is empty.
3. Create database and select it by typing in mysql window:
    create database BookRobot;
    use database BookRobot;

Now you can use program. GUI provides you with showing README and logging files. Features are simple: you can choose bookstores and categories of books you want to find.