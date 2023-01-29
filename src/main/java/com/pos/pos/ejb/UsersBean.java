package com.pos.pos.ejb;

import com.pos.pos.common.UserDto;
import com.pos.pos.entities.ProductPhoto;
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
            //Cauta toti userii din baza de date
            TypedQuery<User> typedQuery = entityManager.createQuery("SELECT u FROM User u", User.class);
            List<User> users = typedQuery.getResultList();

            //Returneaza userii convertiti in data transfer objects
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
            //Pentru fiecare user

            //Creeaza un data transfer object cu proprietatile userului
            var = new UserDto(us.getId(), us.getUsername(), us.getEmail(), us.getPassword());

            //Adauga obiectul in lista de data transfer objects
            userDto.add(var);
        }
        //Returneaza lista de userDto
        return userDto;
    }

    public void createUser(String username, String email, String password, Collection<String> groups) {
        LOG.info("createUser");
        //Creaza un nou user
        User newUser = new User();

        //Seteaza proprietetile noului user
        newUser.setUsername(username);
        newUser.setEmail(email);
        newUser.setPassword(passwordBean.convertToSha256(password));

        //Da persist la noul user in baza de date
        entityManager.persist(newUser);

        //Adauga userului grupurile de acces
        assignGroupsToUser(username, groups);
    }

    public void deleteUserRightsByIds(Collection<Long> userIds){
        LOG.info("deleteUserRightsByIds");
        //Sterge grupurile de acces ale userilor cu id-urile primite

        //Gaseste numele userilor cu ajutorul id-urilor primite ca parametru
        Collection <String> usernames = findUsernamesByUserIds(userIds);

        //Gaseste id-urile grupurilor de acces pe care le are userul
        Collection <Long> groupIds = findUserGroupIdsByUsernames(usernames);

        for(Long groupId : groupIds){
            //Pentru fiecare groupId cauta usergroup-ul si ii da remove
            UserGroup usergroup = entityManager.find(UserGroup.class,groupId);
            entityManager.remove(usergroup);
        }
    }

    public Collection<Long> findUserGroupIdsByUsernames(Collection<String> usernames) {
        //Cauta id-urile userilor
        List <Long> groupIds = entityManager.createQuery("SELECT g.id FROM UserGroup g WHERE g.username IN :usernames",Long.class)
                .setParameter("usernames",usernames)
                .getResultList();
        return groupIds;
    }


    public void updateUser(Long userId, String username, String email, String password, Collection<String> groups){
        LOG.info("updateUser");
        //Updateaza un user

        //Cauta produsul dupa id
        User user = entityManager.find(User.class,userId);

        //Schimba proprietatile userului cu cele introduse
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);

        //Apeleaza functia care sterge grupurile de acces ale userului
        Collection <Long> userIds = new ArrayList<>();
        userIds.add(userId);
        deleteUserRightsByIds(userIds);

        //Seteaza noile grupuri de acces userului
        assignGroupsToUser(username, groups);
    }

    private void assignGroupsToUser(String username, Collection<String> groups) {
        LOG.info("assignGroupsToUser");
        //Seteaza grupurile de acces userului

        //Cauta userul
        List<User> users = entityManager
                .createQuery("SELECT u FROM User u WHERE u.username = :name", User.class)
                .setParameter("name", username)
                .getResultList();

        //Ia primul user gasit
        User newUser = users.get(0);
        Long userId = newUser.getId();

        for (String group : groups) {
            //Pentru ficare grup de acces seteaza proprietatile
            UserGroup userGroup = new UserGroup();
            userGroup.setUsername(username);
            userGroup.setUserGroup(group);
            userGroup.setUserId(userId);

            //Da persist la usergroup in baza de date
            entityManager.persist(userGroup);
        }
    }

    public Collection<String> findUsernamesByUserIds(Collection<Long> userIds) {
        LOG.info("findUsernamesByUserIds");
        //Gaseste numele userilor dupa id-uri
        List <String> usernames = entityManager.createQuery("SELECT u.username FROM User u WHERE u.id IN :userIds",String.class)
                .setParameter("userIds",userIds)
                .getResultList();
        return usernames;
    }

    public UserDto findById(Long userId) {
        LOG.info("findUserById");
        //Cauta user ul din baza de date care are id ul respectiv
        User user = entityManager.find(User.class,userId);

        //Converteste userul intr un data transfer object si ii da return
        UserDto userDto = new UserDto(user.getId(), user.getUsername(), user.getEmail(), user.getPassword());
        return userDto;
    }

    public void deleteUsersByIds(Collection<Long> userIds){
        LOG.info("deleteUsersByIds");

        //Sterge Grupurile de acces ale userilor respectivi
        deleteUserRightsByIds(userIds);

        for(Long userId : userIds){
            //Pentru fiecare userId
            //Caut userul respectiv in baza de date si ii dau remove
            User user = entityManager.find(User.class,userId);
            entityManager.remove(user);
        }
    }

    public List<UserDto> findAllInvalidCashiers() {
        LOG.info("findAllInvalidCashiers");
        try{
            //Cauta id-urile tuturor casierilor care au grupul de acces "CASHIER" (Adica casier nevalidat)
            List<Long> cashierIds = entityManager
                    .createQuery("SELECT u.userId FROM UserGroup u WHERE u.userGroup = :name", Long.class)
                    .setParameter("name", "CASHIER")
                    .getResultList();

            //Cauta toti userii din baza de date
            List<User> users = entityManager
                    .createQuery("SELECT u FROM User u", User.class)
                    .getResultList();


            List<User> invalidCashiers = new ArrayList<>();

            for(User user : users){
                //Pentru fiecare user gasit
                for(Long cashierId : cashierIds){
                    //Iau fiecare cashierId
                    if(cashierId.equals(user.getId())){
                        //Daca cashierId e egal cu cel al unui user
                        //Adaug userul la lista de invalidCashiers
                        invalidCashiers.add(user);
                    }
                }
            }
            //Returneaza casierii convertiti in data transfer objects
            return copyUsersToDto(invalidCashiers);
        }
        catch(Exception ex){
            throw new EJBException(ex);
        }
    }

    public List<UserDto> findAllValidCashiers() {
        LOG.info("findAllValidCashiers");
        try{

            //Cauta id-urile tuturor casierilor care au grupul de acces "VALID_CASHIER"
            List<Long> cashierIds = entityManager
                    .createQuery("SELECT u.userId FROM UserGroup u WHERE u.userGroup = :name", Long.class)
                    .setParameter("name", "VALID_CASHIER")
                    .getResultList();

            //Cauta toti userii din baza de date
            List<User> users = entityManager
                    .createQuery("SELECT c FROM User c", User.class)
                    .getResultList();


            List<User> validCashiers = new ArrayList<>();

            for(User user : users){
                //Pentru fiecare user gasit
            for(Long cashierId : cashierIds){
                //Iau fiecare cashierId
                    if(cashierId.equals(user.getId())){
                        //Daca cashierId e egal cu cel al unui user
                        //Adaug userul la lista de validCashiers
                        validCashiers.add(user);
                    }
                }
            }
            //Returneaza casierii convertiti in data transfer objects
            return copyUsersToDto(validCashiers);
        }
        catch(Exception ex){
            throw new EJBException(ex);
        }
    }
}
