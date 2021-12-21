package edu.kpi.iasa.mmsa.ka9616.kidshop.controller;

import edu.kpi.iasa.mmsa.ka9616.kidshop.model.Age;
import edu.kpi.iasa.mmsa.ka9616.kidshop.model.Gender;
import edu.kpi.iasa.mmsa.ka9616.kidshop.model.Product;
import edu.kpi.iasa.mmsa.ka9616.kidshop.model.User;
import edu.kpi.iasa.mmsa.ka9616.kidshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("products")
    public String all(Model model, @AuthenticationPrincipal User user) {
        checkUser(user, model);
        List<Product> products = productService.findAll();
        model.addAttribute("products", products);
        return "products";
    }

    @GetMapping("product/{id}")
    public String byId(@PathVariable Long id, Model model, @AuthenticationPrincipal User user) {
        checkUser(user, model);
        Product product = productService.findById(id);
        model.addAttribute("product", product);
        return "product-info";
    }

    @GetMapping("product/find-by-articul")
    public Product byName(@RequestParam String articul, Model model, @AuthenticationPrincipal User user) {
        Product products = productService.findByArticul(articul);
        model.addAttribute("products", products);
        return productService.findByArticul(articul);
    }

    @GetMapping("add/product")
    public String addPage(Model model) {
        model.addAttribute("isAuthenticated", true);
        model.addAttribute("isAdmin", true);
        return "add-product";
    }

    @PostMapping("add/product")
    public String add(@RequestParam(value = "articul") String articul,
                      @RequestParam(value = "name") String name,
                      @RequestParam(value = "price") int price,
                      @RequestParam(value = "color") String color,
                      @RequestParam(value = "image", required = false) byte[] image,
                      @RequestParam(value = "gender") Gender gender,
                      @RequestParam(value="age") Age age,
                      Model model
    ) {
       if(productService.isUnique(articul)) {
           productService.add(articul, name, price, color,  gender, age);
        return "redirect:/products";}
       else{ model.addAttribute("articulMessage", "Articul must be unique");
        return "add-product";}
    }
/*
    @GetMapping("delete/product/{product}")
    public String delete(@PathVariable Product product,
                         Model model) {
        model.addAttribute("isAuthenticated", true);
        model.addAttribute("isAdmin", true);
        productService.deleteById(product.getId());
        return "redirect:/products";
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
