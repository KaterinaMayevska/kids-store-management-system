package edu.kpi.iasa.mmsa.ka9616.kidshop.service;


import edu.kpi.iasa.mmsa.ka9616.kidshop.model.Product;
import edu.kpi.iasa.mmsa.ka9616.kidshop.model.Sells;


import java.util.List;

public interface SellService  {
    int FindProductQuantity(Product product);
    Sells addNew(Product product, int quantity);
    void addProduct(Product product, int quantity);
    Sells findProduct(Product product);
    void sellProduct(Product product, int sold);
    boolean isUnique(String articul);
     List<Sells> findAll();

}
