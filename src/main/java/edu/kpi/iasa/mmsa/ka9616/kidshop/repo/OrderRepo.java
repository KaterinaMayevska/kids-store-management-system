package edu.kpi.iasa.mmsa.ka9616.kidshop.repo;

import edu.kpi.iasa.mmsa.ka9616.kidshop.model.Orders;
import edu.kpi.iasa.mmsa.ka9616.kidshop.model.Product;
import edu.kpi.iasa.mmsa.ka9616.kidshop.model.User;
import org.hibernate.criterion.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OrderRepo extends JpaRepository<Orders, Long> {
    List<Orders> findAllByUser(User user);
    List<Orders> findAllByProduct (Product product);
}
