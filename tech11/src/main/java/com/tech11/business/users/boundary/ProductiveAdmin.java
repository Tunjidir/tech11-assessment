package com.tech11.business.users.boundary;

import com.tech11.business.users.entity.User;
import static com.tech11.business.users.entity.User.FIND_ALL;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *Facade to the {@link UsersResource} which performs basic CRUD operations
 * on User objects. This class is annotated {@link Stateless} which signifies that it's an EJB.
 * 
 * @author olatunji oniyide
 */
@Stateless
public class ProductiveAdmin {

    @PersistenceContext
    EntityManager manager;

    public User addUser(User user) {
        return this.manager.merge(user);
    }

    public List<User> allUsers() {
        return this.manager.createNamedQuery(FIND_ALL, User.class).getResultList();
    }

    public User findUser(Long id) {
        final User user = search(id);
        if (user == null) {
            throw new UserNotFoundException("user id does not exist");
        }
        return user;
    }

    private User search(Long id) {
        return this.manager.find(User.class, id);
    }

    public User updateUser(Long id, User user) {
        final User retrieved = search(id);
        if (user == null) {
            throw new UserNotFoundException("Invalid User ID");
        }
        retrieved.setFirstname(user.getFirstname());
        retrieved.setLastname(user.getLastname());
        retrieved.setEmail(user.getEmail());
        retrieved.setPassword(user.getPassword());
        retrieved.setBirthday(user.getBirthday());
        return this.manager.merge(retrieved);
    }

}
