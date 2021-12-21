package edu.kpi.iasa.mmsa.ka9616.kidshop.service.implementation;

import edu.kpi.iasa.mmsa.ka9616.kidshop.model.Orders;
import edu.kpi.iasa.mmsa.ka9616.kidshop.model.Product;
import edu.kpi.iasa.mmsa.ka9616.kidshop.model.User;
import edu.kpi.iasa.mmsa.ka9616.kidshop.repo.OrderRepo;
import edu.kpi.iasa.mmsa.ka9616.kidshop.service.OrderService;
import edu.kpi.iasa.mmsa.ka9616.kidshop.service.SellService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private SellService sellService;
    @Override
    public Orders makeOrder(User user, Orders orders)throws RuntimeException {
        Product product = orders.getProduct();
        int quantity = orders.getNumber();
        try {sellService.sellProduct(product, quantity);
        return orderRepo.save(orders);}
        catch (RuntimeException e){throw new RuntimeException("This quantity is not available");}

    }
    @Override
    public List<Orders> findByUser(User user) {
        return orderRepo.findAllByUser(user);
    }

    @Override
    public Optional<Orders> findById(Long id){
        if(orderRepo.existsById(id)){
        return orderRepo.findById(id);}
        else{ return null;}

    }
    @Override
    public List<Orders> findAll() {
        return orderRepo.findAll();
    }

    @Override
    public void returnOrder(Long id) {
        Orders orders = orderRepo.findById(id).get();
        sellService.addProduct(orders.getProduct(), orders.getNumber());
        orderRepo.delete(orders);
    }


    @Override
    public List<Orders> findByProduct(Product product) {
        return orderRepo.findAllByProduct(product);
    }





}
