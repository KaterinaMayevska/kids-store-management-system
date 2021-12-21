package edu.kpi.iasa.mmsa.ka9616.kidshop.service;

import edu.kpi.iasa.mmsa.ka9616.kidshop.model.Age;
import edu.kpi.iasa.mmsa.ka9616.kidshop.model.Gender;
import edu.kpi.iasa.mmsa.ka9616.kidshop.model.Product;

import java.util.Date;
import java.util.List;

public interface ProductService {
    Product add(String articul, String name, int price, String color, Gender gender, Age age);

    List<Product> findAll();

    Product findById(Long id);

    Product findByArticul(String articul);

    void deleteById(Long id);
    boolean isUnique(String articul);


}
