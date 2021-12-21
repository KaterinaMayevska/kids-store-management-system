package edu.kpi.iasa.mmsa.ka9616.kidshop.controller;


import edu.kpi.iasa.mmsa.ka9616.kidshop.model.Product;
import edu.kpi.iasa.mmsa.ka9616.kidshop.model.Sells;
import edu.kpi.iasa.mmsa.ka9616.kidshop.model.User;
import edu.kpi.iasa.mmsa.ka9616.kidshop.service.ProductService;
import edu.kpi.iasa.mmsa.ka9616.kidshop.service.SellService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class SellsController {
    @Autowired
    private SellService sellService;

    @Autowired
    private ProductService productService;

    @GetMapping("sells")
    public String all( Model model,@AuthenticationPrincipal User user) {
        checkUser(user, model);
        model.addAttribute("sells", sellService.findAll());
        return "sells";}
    @GetMapping("sells/{product}")
    public int FindProductQuantity(Model model, Product product) {
        int quantity = sellService.FindProductQuantity(product);
        model.addAttribute("product", product);
        model.addAttribute("quantity", quantity);
        return quantity;
    }

    @PostMapping("add/sell")
    public String addNew(@RequestParam int quantity,
                                       @PathVariable long id) {
        final Product product = productService.findById(id);
        final Sells sells = sellService.addNew(product, quantity);
        final String location = String.format("/sells/%d", sells.getId());
        return "redirect:/sells";
    }

    @PatchMapping("sells/{id}")
    public String addProduct(@PathVariable Long id, @RequestParam int quantity) {
        final Product product = productService.findById(id);
        sellService.addProduct(product, quantity);
        return "redirect:/sells";
    }

    @PatchMapping("make/sell")
    public String SellProduct(@PathVariable Long id, @RequestParam int quantity) {
        final Product product = productService.findById(id);
        try {
            sellService.sellProduct(product, quantity);
            return "redirect:/sells";
        } catch (IllegalArgumentException e) {
            return "redirect:/sells";
        }
    }
    @GetMapping("sells/{id}")
    public Sells findProduct(@PathVariable long id) {
            final Product product = productService.findById(id);
            final Sells sells = sellService.findProduct(product);
            return sells;

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