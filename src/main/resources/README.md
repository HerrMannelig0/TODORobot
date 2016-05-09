About database:
Program is running with MySQL database, project uses version 5.1.38 of mySQL server connector and it is adviced to not change it due to possiblility
of throwing exception.
Before running program (linux users):
1. Open terminal, assuming that mysql-server and my-sql client are installed:
2. login into database:
    mysql -u USERNAME -pPASSWORD (or mysql -u USERNAME -p if you don't want to show your password)
    Default configuration is : USERNAME=root password is empty.
3. Create database and select it by typing in mysql window:
    create database BookRobot;
    use database BookRobot;

Now you can use program. GUI provides you with showing README and logging files. Features are simple: you can choose bookstores and categories of books you want to find.
Pressing Show Books lists all books with given settings (bookstores, categories).

About categories:
No Category means that websides provides no information about the type of its books.
Other Category means that given book is not one of the three main categories (IT, Romance or Fantasy). If you choose bookstore and category where there are no such a book, you will be informed about that as well.

About bookstores:
You can add your own bookstore, but there is no chance to work proprly unless you add the URL to the FreeBooksAddressSite.txt along with tags the crawler should read. So, this option is more suitable for ones who have basic knowledge of HTML.

