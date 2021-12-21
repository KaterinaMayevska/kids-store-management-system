package edu.kpi.iasa.mmsa.ka9616.kidshop.service;

import edu.kpi.iasa.mmsa.ka9616.kidshop.model.Orders;
import edu.kpi.iasa.mmsa.ka9616.kidshop.model.Product;
import edu.kpi.iasa.mmsa.ka9616.kidshop.model.User;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    Orders makeOrder(User user, Orders order);
    List<Orders> findByUser(User user);
   // void deleteById(Long id);
    void returnOrder(Long id);
    List<Orders> findByProduct(Product product);
    List<Orders> findAll();
    Optional<Orders> findById(Long id);
}
