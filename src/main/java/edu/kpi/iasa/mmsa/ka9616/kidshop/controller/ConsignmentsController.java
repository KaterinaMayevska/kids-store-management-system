package edu.kpi.iasa.mmsa.ka9616.kidshop.controller;

import edu.kpi.iasa.mmsa.ka9616.kidshop.model.Consignment;
import edu.kpi.iasa.mmsa.ka9616.kidshop.model.User;
import edu.kpi.iasa.mmsa.ka9616.kidshop.service.ConsignmentService;
import edu.kpi.iasa.mmsa.ka9616.kidshop.service.ProductService;
import edu.kpi.iasa.mmsa.ka9616.kidshop.service.SellService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class ConsignmentsController {
    @Autowired
    private ConsignmentService consignmentService;
    @Autowired
    private ProductService productService;
    @Autowired
    private SellService sellService;
    @GetMapping("consignments")
    public String all( Model model,@AuthenticationPrincipal User user) {
          checkUser(user, model);
          model.addAttribute("consignment", consignmentService.findAll());
          return "consignments";

    }

    @GetMapping("consignments/delete/{id}")
    public String deleteConsignment(@PathVariable Long id, @AuthenticationPrincipal User user, Model model){
        checkUser(user, model);
        Consignment consignment = consignmentService.findById(id);
        if(consignment.equals(null)) {
            model.addAttribute("SearchMessage", "No such consignment");
        return "redirect:/consignments";}
        else {
            Date date = new Date();
            long diff = date.getTime() - consignment.getDate().getTime();
            long diffDays = diff / (24 * 60 * 60 * 1000);
            if(diffDays <= 14){
                sellService.sellProduct(consignment.getProduct(), consignment.getQuantity());
                consignmentService.deleteById(id);
                return "redirect:/consignments";}
            else{model.addAttribute("TimeMessage", "14 days have expired");
            return "redirect:/consignments";}
    }}


    @GetMapping("add/consignment")
    public String addPage(Model model) {
        model.addAttribute("isAuthenticated", true);
        model.addAttribute("isAdmin", true);
        return "add-consignments";
    }
    @PostMapping("add/consignment")
    public String add(@RequestParam(value = "articul") String articul,
                      @RequestParam(value = "quantity") int quantity,
                      @RequestParam(value = "date") String arrivalDate,
                      Model model
    ) {
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm a");
        Date parsed = null;
        try {
            parsed = formatter.parse(arrivalDate);
        } catch (ParseException e) {
            model.addAttribute("message", "Wrong date format");
            model.addAttribute("isAuthenticated", true);
            model.addAttribute("isAdmin", true);
            return "add-consignments";
        }
        if(productService.findByArticul(articul) ==null) {
            model.addAttribute("ProductMessage", "No such product");
            model.addAttribute("isAuthenticated", true);
            model.addAttribute("isAdmin", true);
            return "add-consignments";
        }
        else{
        consignmentService.add(articul, quantity, parsed);
        sellService.addProduct(productService.findByArticul(articul), quantity);
        return "redirect:/consignments";}
    }

/*
    @GetMapping("find-by-product/consignment")
    public List<Consignment> byName(@RequestParam String articul, Model model, @AuthenticationPrincipal User user) {
        List<Consignment> consignmentList = consignmentService.findByProduct(articul);
        model.addAttribute("consignments:", consignmentList);
        return consignmentList;
    }
*/




    private void checkUser(@AuthenticationPrincipal User user, Model model) {
        boolean isAuthenticated = false;
        boolean isAdmin = false;
        if(user != null) {
            isAuthenticated = true;
            if (user.isAdmin())
                isAdmin = true;
        }
        model.addAttribute("isAuthenticated", isAuthenticated);
        model.addAttribute("isAdmin", isAdmin);
    }


    }
