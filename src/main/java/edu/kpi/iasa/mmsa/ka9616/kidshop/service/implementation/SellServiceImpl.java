package edu.kpi.iasa.mmsa.ka9616.kidshop.service.implementation;

import edu.kpi.iasa.mmsa.ka9616.kidshop.model.*;
import edu.kpi.iasa.mmsa.ka9616.kidshop.repo.ProductRepo;
import edu.kpi.iasa.mmsa.ka9616.kidshop.repo.SellsRepo;
import edu.kpi.iasa.mmsa.ka9616.kidshop.service.SellService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SellServiceImpl implements SellService {
    @Autowired
    private SellsRepo sellsRepo;
    @Autowired
    private ProductRepo productRepo;

    @Override
    public int FindProductQuantity(Product product) throws IllegalArgumentException{
        Optional <Sells> sells = sellsRepo.findByProduct(product);
        if(sells.isEmpty()) {
            throw new RuntimeException("No sells yet");
        }

        return sells.get().getQuantity();
    }
    @Override
    public  Sells addNew(Product product, int quantity){
        Sells sells = new Sells(quantity,  product);
        sellsRepo.save(sells);
        return sells;
    }

    @Override
   public void addProduct(Product product, int quantity) {
        Optional <Sells> sellsOptional = sellsRepo.findByProduct(product);
        if(sellsRepo.findByProduct(product).isEmpty()) {
            Sells sells = new Sells(quantity,  product);
            sellsRepo.save(sells);
            }
        else{
            sellsRepo.delete(sellsOptional.get());
           int oldQuantity= sellsOptional.get().getQuantity();
           sellsOptional.get().setQuantity(oldQuantity+quantity);
           sellsRepo.save(sellsOptional.get());
        }


    }

    @Override
    public Sells findProduct(Product product)
    {
        return sellsRepo.findByProduct(product).get();
    }


    public void sellProduct(Product product, int sold) throws RuntimeException{
        Optional <Sells> sellsOptional = sellsRepo.findByProduct(product);
        int available = sellsOptional.get().getQuantity();
        if(available>=sold){
            sellsRepo.delete(sellsOptional.get());
            sellsOptional.get().setQuantity(available-sold);
            sellsRepo.save(sellsOptional.get()); }
        else throw new RuntimeException("There are not so many products available");
    }
    @Override
    public List<Sells> findAll() {
        return sellsRepo.findAll();
    }
    @Override
    public boolean isUnique(String articul){
        Product product = productRepo.findAllByArticul(articul);
        return (sellsRepo.findByProduct(product).isEmpty() );

    }



}
