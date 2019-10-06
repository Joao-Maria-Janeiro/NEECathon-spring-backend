package com.joaomariajaneiro.neechathon.model;

import javax.persistence.*;

@Entity
@Table(name = "PRODUCT")
public class Product {

    @Id
    @Column(name = "PRODUCT_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "PRODUCT_NAME")
    private String name;

    @Column(name = "PRODUCT_PRICE")
    private Long price;

    @Column(name = "PRODUCT_DESCRIPTION")
    private String description;

    @Column(name = "PRODUCT_QUANTITY")
    private Integer quantity;

    @Column(name = "PRODUCT_IMAGE_PATH")
    private String image_path;

    public Product() {
    }

    public Product(String name, Long price, String description, Integer quantity, String image_path) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.quantity = quantity;
        this.image_path = image_path;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Product setDescription(String description) {
        this.description = description;
        return this;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Product setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public String getImage_path() {
        return image_path;
    }

    public Product setImage_path(String image_path) {
        this.image_path = image_path;
        return this;
    }

    public Product setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Product setName(String name) {
        this.name = name;
        return this;
    }

    public Long getPrice() {
        return price;
    }

    public Product setPrice(Long price) {
        this.price = price;
        return this;
    }
}
