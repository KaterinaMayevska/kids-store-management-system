package edu.kpi.iasa.mmsa.ka9616.kidshop.service;


import edu.kpi.iasa.mmsa.ka9616.kidshop.model.User;
import java.util.List;

public interface UserService {
    List<User> findAll();

    User add(User user);

    User add(String login, String password, String email);

    User addAdmin(String login, String password, String email);

    void deleteById(Long id);

    User changeRoles(Long id);

    boolean isUnique(String login, String email);

    User findByLogin(String login);
}