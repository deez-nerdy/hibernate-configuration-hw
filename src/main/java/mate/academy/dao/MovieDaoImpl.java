package mate.academy.dao;

import java.util.Optional;
import mate.academy.exception.DataProcessingException;
import mate.academy.lib.Dao;
import mate.academy.model.Movie;
import mate.academy.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

@Dao
public class MovieDaoImpl implements MovieDao {
    @Override
    public Movie add(Movie movie) {
        Transaction transaction = null;
        Session session = openSession();
        try {
            transaction = session.beginTransaction();
            session.persist(movie);
            transaction.commit();
            return movie;
        } catch (Exception e) {
            transactionNullCheck(transaction);
            throw new DataProcessingException("Could not add movie to DB", e);
        } finally {
            session.close();
        }
    }

    @Override
    public Optional<Movie> get(Long id) {
        try (Session session = openSession()) {
            Movie movie = session.get(Movie.class, id);
            return Optional.ofNullable(movie);
        } catch (Exception e) {
            throw new DataProcessingException("Could not find movie by id: " + id, e);
        }
    }

    @Override
    public Movie update(Movie movie) {
        Transaction transaction = null;
        Session session = openSession();
        try {
            transaction = session.beginTransaction();
            Movie update = session.merge(movie);
            transaction.commit();
            return update;
        } catch (Exception e) {
            transactionNullCheck(transaction);
            throw new DataProcessingException("Could not update movie: " + movie.getTitle(), e);
        } finally {
            session.close();
        }
    }

    @Override
    public boolean deleteById(Long id) {
        Transaction transaction = null;
        Session session = openSession();
        try {
            transaction = session.beginTransaction();

            Movie movie = session.get(Movie.class, id);
            if (movie == null) {
                return false;
            }

            session.delete(movie);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transactionNullCheck(transaction);
            throw new DataProcessingException("Could not delete movie with id: " + id, e);
        } finally {
            session.close();
        }
    }

    private void transactionNullCheck(Transaction transaction) {
        if (transaction != null) {
            transaction.rollback();
        }
    }

    private Session openSession() {
        return HibernateUtil.getSessionFactory().openSession();
    }
}
