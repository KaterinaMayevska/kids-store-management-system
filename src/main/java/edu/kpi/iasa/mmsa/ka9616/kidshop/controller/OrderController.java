package edu.kpi.iasa.mmsa.ka9616.kidshop.controller;

import edu.kpi.iasa.mmsa.ka9616.kidshop.model.Orders;
import edu.kpi.iasa.mmsa.ka9616.kidshop.model.Product;
import edu.kpi.iasa.mmsa.ka9616.kidshop.model.User;
import edu.kpi.iasa.mmsa.ka9616.kidshop.service.OrderService;
import edu.kpi.iasa.mmsa.ka9616.kidshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductService productService;


    @GetMapping("orders/made")
    public String allMade(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("orders", orderService.findByUser(user));
        return "orders";

    }

    @GetMapping("orders")
    public String all(@AuthenticationPrincipal User user, Model model) {
        checkUser(user, model);
        if(user.isAdmin()) model.addAttribute("orders", orderService.findAll());
        else model.addAttribute("orders", orderService.findByUser(user));
        return "orders";

    }
    @GetMapping("order/{product}")
    public String AllProducts(@PathVariable Product product,
                             @AuthenticationPrincipal User user,
                             Model model) {
        checkUser(user, model);
        List<Orders> orders = orderService.findByProduct(product);
        model.addAttribute("product", product);
        model.addAttribute("orders", orders);
        return "orders";
    }


    @GetMapping("order/delete/{id}")
    public String deleteOrder(@PathVariable Long id, @AuthenticationPrincipal User user, Model model){
        checkUser(user, model);
        if (!orderService.findById(id).isPresent() ) {model.addAttribute("SearchMessage", "No such order");
            return "orders";}
        Optional<Orders> orders = orderService.findById(id);
            Date date = new Date();
            long diff = date.getTime() - orders.get().getDate().getTime();
            long diffDays = diff / (24 * 60 * 60 * 1000);
            if(diffDays <= 14){
                orderService.returnOrder(id);
                return "orders";}
            else{model.addAttribute("TimeMessage", "14 days have expired");
                return "orders";}
        }

    @GetMapping("order/make")
    public String addPage(@AuthenticationPrincipal User user,Model model) {
        model.addAttribute("isAuthenticated", true);

        return "make-order";
    }
    @PostMapping("order/make")
    public String buy(@RequestParam(value = "articul") String articul,
                      @RequestParam(value="quantity") int quantity,
                      @AuthenticationPrincipal User user, Model model) {
        if(user.isAdmin()) {model.addAttribute("orderMessage", "This quantity is not available");
            return"make-order";}
        Product product = productService.findByArticul(articul);
        int the_price = quantity* product.getPrice();
        Date date = new Date();
        try{
        Orders orders = new Orders(quantity, the_price, date , user, product);
            orderService.makeOrder(user, orders);}
        catch (RuntimeException e){ model.addAttribute("orderMessage", "This quantity is not available");
        return"make-order";}


        return "redirect:/products";
    }

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
