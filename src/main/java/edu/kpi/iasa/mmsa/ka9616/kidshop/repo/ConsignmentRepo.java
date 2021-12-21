package edu.kpi.iasa.mmsa.ka9616.kidshop.repo;

import edu.kpi.iasa.mmsa.ka9616.kidshop.model.Consignment;
import edu.kpi.iasa.mmsa.ka9616.kidshop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsignmentRepo extends JpaRepository<Consignment, Long> {
    List<Consignment> findAllByProduct(Product product);

}
