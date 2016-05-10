package com.epam.DBDAO;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

import com.epam.DB.entities.BookToDB;

/**
 * Class for performing operation on DB regarding to books
 */
public class BookDAOimpl {
    SessionFactory sessionFactory;
    Session session;
    Criteria criteria;

    public BookDAOimpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void addAllBooks(List <BookToDB> books) {
        startTransactions();
        for (BookToDB book: books){
            session.save(book);
        }
        session.flush(); //read why those two are needed
        session.clear();
        endTransactions();

    }

    public BookToDB getBookByID(int id) {
        startTransactions();
        criteria.add(Restrictions.eq("id", id));
        BookToDB book = (BookToDB) criteria.list();
        endTransactions();
        return book;
    }

    public List<BookToDB> listByCategory (String category){
        		startTransactions();
		criteria.setProjection(Projections.distinct(Projections.projectionList()
				.add(Projections.property("title"), "title").add(Projections.property("author"), "author")
				.add(Projections.property("bookstore"), "bookstore"))).add(Restrictions.eq("category.name", category))
				.setResultTransformer(Transformers.aliasToBean(BookToDB.class));
		List<BookToDB> books = criteria.list();
		endTransactions();
		return books;

    }

    public void deleteBook(BookToDB book) {
        startTransactions();
        session.delete(book);
        endTransactions();
    }

    public void addBook(BookToDB book) {
        startTransactions();
        session.save(book);
        endTransactions();
    }

    private void startTransactions() {
        session = sessionFactory.openSession();
        session.beginTransaction();
        criteria = session.createCriteria(BookToDB.class);
    }

    private void endTransactions() {
        session.getTransaction().commit();
        session.close();
    }
}
