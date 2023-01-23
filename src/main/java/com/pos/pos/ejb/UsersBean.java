package com.pos.pos.ejb;

import com.pos.pos.common.UserDto;
import com.pos.pos.entities.User;
import com.pos.pos.entities.UserGroup;
import jakarta.ejb.EJBException;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

@Stateless
public class UsersBean {

    private static final Logger LOG = Logger.getLogger(UsersBean.class.getName());

    @PersistenceContext
    EntityManager entityManager;

    @Inject
    PasswordBean passwordBean;


    public List<UserDto> findAllUsers() {
        LOG.info("findAllUsers");
        try{
            TypedQuery<User> typedQuery = entityManager.createQuery("SELECT u FROM User u", User.class);
            List<User> users = typedQuery.getResultList();
            return copyUsersToDto(users);
        }
        catch(Exception ex){
            throw new EJBException(ex);

        }
    }

    private List<UserDto> copyUsersToDto(List<User> users){
        List<UserDto> userDto = new ArrayList<>();
        UserDto var;
        for(User us : users){
            var = new UserDto(us.getId(), us.getUsername(), us.getEmail(), us.getPassword());
            userDto.add(var);
        }
        return userDto;
    }

    public void createUser(String username, String email, String password, Collection<String> groups) {
        LOG.info("createUser");
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setEmail(email);
        newUser.setPassword(passwordBean.convertToSha256(password));
        entityManager.persist(newUser);
        assignGroupsToUser(username, groups);
    }
    private void assignGroupsToUser(String username, Collection<String> groups) {
        LOG.info("assignGroupsToUser");
        for (String group : groups) {
            UserGroup userGroup = new UserGroup();
            userGroup.setUsername(username);
            userGroup.setUserGroup(group);
            entityManager.persist(userGroup);
        }
    }

    public Collection<String> findUsernamesByUserIds(Collection<Long> userIds) {
        List <String> usernames = entityManager.createQuery("SELECT u.username FROM User u WHERE u.id IN :userIds",String.class)
                .setParameter("userIds",userIds)
                .getResultList();
        return usernames;
    }
}
