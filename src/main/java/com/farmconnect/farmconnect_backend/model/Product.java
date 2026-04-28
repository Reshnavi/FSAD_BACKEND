package com.farmconnect.farmconnect_backend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "products")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Product {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
    private String description;
    private Double price;
    private String unit;
    private String category;
    private String image;
    private Integer stock;
    private Double rating;
    private Integer reviews;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "farmer_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "password"})
    private User farmer;
}
