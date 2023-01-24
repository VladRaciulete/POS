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

    public void deleteUserRightsByIds(Collection<Long> userIds){
        LOG.info("deleteUserRightsByIds");

        Collection <String> usernames = findUsernamesByUserIds(userIds);
        Collection <Long> groupIds = findUserGroupIdsByUsernames(usernames);

        for(Long groupId : groupIds){
            UserGroup usergroup = entityManager.find(UserGroup.class,groupId);
            entityManager.remove(usergroup);
        }
    }

    public Collection<Long> findUserGroupIdsByUsernames(Collection<String> usernames) {
        List <Long> groupIds = entityManager.createQuery("SELECT g.id FROM UserGroup g WHERE g.username IN :usernames",Long.class)
                .setParameter("usernames",usernames)
                .getResultList();
        return groupIds;
    }


    public void updateUser(Long userId, String username, String email, String password, Collection<String> groups){
        LOG.info("updateUser");

        User user = entityManager.find(User.class,userId);
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);

        Collection <Long> userIds = new ArrayList<>();
        userIds.add(userId);
        deleteUserRightsByIds(userIds);

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

    public UserDto findById(Long userId) {
        User user = entityManager.find(User.class,userId);
        UserDto userDto = new UserDto(user.getId(), user.getUsername(), user.getEmail(), user.getPassword());

        return userDto;
    }

    public void deleteUsersByIds(Collection<Long> userIds){
        LOG.info("deleteUsersByIds");

        deleteUserRightsByIds(userIds);

        for(Long userId : userIds){
            User user = entityManager.find(User.class,userId);
            entityManager.remove(user);
        }
    }

}
