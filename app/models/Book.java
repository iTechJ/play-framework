package models;


import play.data.validation.Constraints;
import play.db.jpa.*;
import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created by Anton.Nekrasov
 * 8/12/2014 16:05
 */

@Entity
public class Book {
    @Id
    @GeneratedValue
    public Long id;

    @Constraints.Required
    public String name;

    @Constraints.Required
    public Integer price;

    public static Book findById(Long id) {
        try{
            return JPA.em().find(Book.class, id);
        } catch (NoResultException nre){
            return null;
        }
    }

    public static Long total() {
        EntityManager em = JPA.em();
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        countQuery.select(criteriaBuilder.count(countQuery.from(Book.class)));
        return em.createQuery(countQuery).getSingleResult();
    }

    public static List<Book> list(Integer pageNumber, Integer pageSize) {
        EntityManager em = JPA.em();
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Book> criteriaQuery = criteriaBuilder.createQuery(Book.class);
        Root<Book> from = criteriaQuery.from(Book.class);

        CriteriaQuery<Book> select = criteriaQuery.select(from);
        TypedQuery<Book> q = em.createQuery(select);
        q.setFirstResult((pageNumber - 1) * pageSize);
        q.setMaxResults(pageSize);

        return q.getResultList();
    }

    public static void update(Book book) {
        JPA.em().persist(book);
    }

    public static void delete(Long id) {
        EntityManager em = JPA.em();
        Book book = em.find(Book.class, id);
        if(book != null) {
            em.remove(book);
        }
    }
}
