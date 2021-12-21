package edu.kpi.iasa.mmsa.ka9616.kidshop.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;


import javax.persistence.*;
import java.util.List;


@EnableAutoConfiguration
@Data
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product {
    @Id
    @Column(name="id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

   // @UniqueElements
    @Column(name = "articul", length = 20, nullable = false)
    private String articul;

    @Column(name = "name", length = 255, nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private int price;

    @Column(name = "color", length = 200)
    private String color;


    @Column(name="gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Column(name="age")
    @Enumerated(EnumType.STRING)
    private Age age;

    @JsonBackReference
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Orders> orders;
    @JsonBackReference
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Consignment> consignment;
    @JsonBackReference
    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Sells sells;

    public Product(String articul, String name, int price, String color, Gender gender, Age age) {
        this.articul = articul;
        this.name = name;
        this.price = price;
        this.color = color;
        this.gender = gender;
        this.age = age;
    }

}
