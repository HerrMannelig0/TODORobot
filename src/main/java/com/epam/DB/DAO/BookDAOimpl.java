package com.epam.DB.DAO;

import com.epam.DB.entities.BookDB;
import com.epam.DB.entities.CategoryDB;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

import java.util.List;

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

    public void addAllBooks(List <BookDB> books) {
        startTransactions();
        for (BookDB book: books){
            session.save(book);
        }
        session.flush(); //read why those two are needed
        session.clear();
        endTransactions();

    }

    public BookDB getBookByID(int id) {
        startTransactions();
        criteria.add(Restrictions.eq("id", id));
        BookDB book = (BookDB) criteria.list();
        endTransactions();
        return book;
    }

    public List<BookDB> listByCategory (String category){
        		startTransactions();
		criteria.setProjection(Projections.distinct(Projections.projectionList()
				.add(Projections.property("title"), "title").add(Projections.property("author"), "author")
				.add(Projections.property("bookstore"), "bookstore"))).add(Restrictions.eq("category.name", category))
				.setResultTransformer(Transformers.aliasToBean(BookDB.class));
		List<BookDB> books = criteria.list();
		endTransactions();
		return books;

    }

    public void deleteBook(BookDB book) {
        startTransactions();
        session.delete(book);
        endTransactions();
    }

    public void addBook(BookDB book) {
        startTransactions();
        session.save(book);
        endTransactions();
    }

    private void startTransactions() {
        session = sessionFactory.openSession();
        session.beginTransaction();
        criteria = session.createCriteria(BookDB.class);
    }

    private void endTransactions() {
        session.getTransaction().commit();
        session.close();
    }
}
