package com.kinvinjin.shoppingcart.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name="inventory")
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = false)
    private Integer id;

    @Column(name="sku")
    private String sku;

    @Column(name="name")
    private String name;

    @Column(name="price")
    private BigDecimal price;

    @Column(name="qty")
    private BigDecimal qty;
}
