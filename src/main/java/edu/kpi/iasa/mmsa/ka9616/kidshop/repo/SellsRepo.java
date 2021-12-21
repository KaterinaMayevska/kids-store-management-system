package edu.kpi.iasa.mmsa.ka9616.kidshop.repo;

import edu.kpi.iasa.mmsa.ka9616.kidshop.model.Product;
import edu.kpi.iasa.mmsa.ka9616.kidshop.model.Sells;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SellsRepo extends JpaRepository<Sells, Long> {

    Optional <Sells> findByProduct(Product product);



}
