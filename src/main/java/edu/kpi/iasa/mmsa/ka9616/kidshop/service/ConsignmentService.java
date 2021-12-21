package edu.kpi.iasa.mmsa.ka9616.kidshop.service;

import edu.kpi.iasa.mmsa.ka9616.kidshop.model.Consignment;
import edu.kpi.iasa.mmsa.ka9616.kidshop.model.Orders;
import edu.kpi.iasa.mmsa.ka9616.kidshop.model.Product;
import edu.kpi.iasa.mmsa.ka9616.kidshop.model.User;

import java.util.Date;
import java.util.List;

public interface ConsignmentService {
    Consignment add(String articul, int quantity, Date date);
    void deleteById(Long id);
    List<Consignment> findByProduct(String articul);
    List<Consignment> findAll();
    Consignment findById(Long id);

}
