package edu.kpi.iasa.mmsa.ka9616.kidshop.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import javax.persistence.*;
import java.util.Date;

@EnableAutoConfiguration
@Entity
@Data
@NoArgsConstructor
@Table(name = "consignments")
public final class Consignment {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(name="quantity")
    private int quantity;
    @Column(name="date")
    private Date date;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "products_id")
    private Product product;

    public Consignment(int quantity, Date date, Product product) {
        this.quantity = quantity;
        this.date = date;
        this.product = product;
    }



    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Long getId() {
        return id;
    }



}
