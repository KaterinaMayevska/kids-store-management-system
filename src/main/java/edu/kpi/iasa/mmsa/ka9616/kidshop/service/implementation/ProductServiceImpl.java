package edu.kpi.iasa.mmsa.ka9616.kidshop.service.implementation;

import edu.kpi.iasa.mmsa.ka9616.kidshop.model.Age;
import edu.kpi.iasa.mmsa.ka9616.kidshop.model.Gender;
import edu.kpi.iasa.mmsa.ka9616.kidshop.model.Product;
import edu.kpi.iasa.mmsa.ka9616.kidshop.repo.ProductRepo;
import edu.kpi.iasa.mmsa.ka9616.kidshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepo productRepo;

    @Override
    public Product add(String articul, String name, int price, String color,  Gender gender, Age age){
        Product product = new Product(articul, name, price, color,  gender, age);
        return productRepo.save(product);
    }

    @Override
    public List<Product> findAll() {
        return productRepo.findAll();
    }

    @Override
    public Product findById(Long id) {
        return productRepo.getById(id);
    }

    @Override
    public Product findByArticul(String articul) {
        return productRepo.findAllByArticul(articul);
    }

    @Override
    public void deleteById(Long id) {
        productRepo.deleteById(id);

    }
     @Override
     public boolean isUnique(String articul){
         return (productRepo.findAllByArticul(articul) == null );

     }
}
