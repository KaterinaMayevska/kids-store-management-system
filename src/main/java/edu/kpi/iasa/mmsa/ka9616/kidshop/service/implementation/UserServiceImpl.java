package edu.kpi.iasa.mmsa.ka9616.kidshop.service.implementation;

import edu.kpi.iasa.mmsa.ka9616.kidshop.configuration.Encryption;
import edu.kpi.iasa.mmsa.ka9616.kidshop.model.Roles;
import edu.kpi.iasa.mmsa.ka9616.kidshop.model.User;
import edu.kpi.iasa.mmsa.ka9616.kidshop.repo.UserRepo;
import edu.kpi.iasa.mmsa.ka9616.kidshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {

    @Autowired
    private UserRepo userRepository;

    @Autowired
     private Encryption passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(login);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }


    @Override
    public User add(User user) {
        return userRepository.save(user);
    }

    @Override
    public User add(String login, String password, String email) {

        User user = new User(login, passwordEncoder.getPasswordEncoder().encode(password), email );
        return userRepository.save(user);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
    @Override
    public User addAdmin(String login, String password, String email) {

        User user = new User(login, passwordEncoder.getPasswordEncoder().encode(password), email );
        List<Roles> roles = user.getRoles();
        roles.clear();
        roles.add(Roles.ADMIN);
        user.setRoles(roles);
        return userRepository.save(user);
    }

    @Override
    public User changeRoles(Long id) throws RuntimeException{
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) throw new RuntimeException("Invalid login");
        else{User user1 = user.get();
            userRepository.delete(user1);
            user1.getRoles().add(Roles.ADMIN);
            userRepository.save(user1);
        return user1;}
    }

    @Override
    public boolean isUnique(String username, String email) {
        return (userRepository.findByLogin(username) == null && userRepository.findByEmail(email)==null);
    }

    @Override
    public User findByLogin(String login){
        return userRepository.findByLogin(login);
    }

}
