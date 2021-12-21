package edu.kpi.iasa.mmsa.ka9616.kidshop.service.implementation;

import edu.kpi.iasa.mmsa.ka9616.kidshop.model.Consignment;
import edu.kpi.iasa.mmsa.ka9616.kidshop.model.Product;
import edu.kpi.iasa.mmsa.ka9616.kidshop.repo.ConsignmentRepo;
import edu.kpi.iasa.mmsa.ka9616.kidshop.repo.ProductRepo;
import edu.kpi.iasa.mmsa.ka9616.kidshop.service.ConsignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ConsignmentsServiceImpl implements ConsignmentService {
    @Autowired
    private ConsignmentRepo consignmentRepo;
    @Autowired
    private ProductRepo productRepo;
    @Override
    public Consignment add(String articul, int quantity, Date date){
        Product product = productRepo.findAllByArticul(articul);
        Consignment consignment = new Consignment(quantity, date, product);
        return consignmentRepo.save(consignment);
    }
    @Override
    public void deleteById(Long id) {
        consignmentRepo.deleteById(id);

    }

    @Override
    public Consignment findById(Long id){
        return consignmentRepo.getById(id);
    }
   @Override
    public List<Consignment> findByProduct(String articul) {
        Product product = productRepo.findAllByArticul(articul);
        return consignmentRepo.findAllByProduct(product);
    }

    @Override
    public List<Consignment> findAll() {
        return consignmentRepo.findAll();
    }

}
