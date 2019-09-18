package com.tech11.business.users.entity;

import static com.tech11.business.users.entity.User.FIND_ALL;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import static org.hamcrest.CoreMatchers.is;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;


/**
 *
 * @author olatunji oniyide
 */
public class UserIT {
        
    private static EntityManagerFactory factory = Persistence.createEntityManagerFactory("integration-test");
    private EntityManager manager;
    private EntityTransaction transaction;
    
    @Before
    public void setup() {
        this.manager = factory.createEntityManager();
        this.transaction = manager.getTransaction();
        
    }
    
    @Test
    public void validateORM() {
        this.transaction.begin();
        final User user = new User("Olatunji", "Oniyide", "Olatunji4you@gmail.com", "igotthis", LocalDate.of(1995, Month.AUGUST, 1));
        this.manager.persist(user);
        this.transaction.commit();
        assertThat(user.getId(), is(1L));
        
        this.transaction.begin();
        final User retrievedUser = this.manager.find(User.class, 1L);
        this.transaction.commit();
        assertNotNull(retrievedUser);
        assertThat(retrievedUser.getLastname(), is("Oniyide"));
        
        this.transaction.begin();
        final List<User> allusers = this.manager.createNamedQuery(FIND_ALL, User.class).getResultList();
        this.transaction.commit();
        assertNotNull(allusers);
        assertEquals(1, allusers.size());
        
    }
    
    @After
    public void tearDown() {
        this.transaction.begin();
        this.manager.createQuery("DELETE FROM User u").executeUpdate();
        this.transaction.commit();
    }
}
