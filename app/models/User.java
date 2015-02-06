package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import play.data.validation.Constraints;
import play.db.jpa.JPA;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * Created by Anton.Nekrasov
 * 8/14/2014 18:31
 */

@Entity
public class User {
    @Id
    public Long id;

    @Constraints.Required
    public String name;

    @Constraints.Required
    @JsonIgnore
    public String password;

    @Constraints.Required
    @Enumerated(EnumType.STRING)
    public Role role;

    public static void create(User user) {
        JPA.em().persist(user);
    }

    public static User findByName(String name) {
        try{
            EntityManager em = JPA.em();
            CriteriaBuilder builder = em.getCriteriaBuilder();
            CriteriaQuery<User> query = builder.createQuery(User.class);
            Root<User> u = query.from(User.class);
            query.select(u).where(builder.equal(u.get("name"), name));

            TypedQuery<User> q = em.createQuery(query);
            return q.getSingleResult();
        } catch (NoResultException nre){
            return null;
        }
    }
}
