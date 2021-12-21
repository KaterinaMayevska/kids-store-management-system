package edu.kpi.iasa.mmsa.ka9616.kidshop.repo;

import edu.kpi.iasa.mmsa.ka9616.kidshop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepo extends JpaRepository<User, Long> {
      User findByLogin(String login);
      User findByEmail(String email);
      Optional<User> findById(Long id);
}
