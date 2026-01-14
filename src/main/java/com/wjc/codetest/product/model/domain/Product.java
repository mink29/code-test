package com.wjc.codetest.product.model.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Product {

    @Id
    @Column(name = "product_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "category")
    private String category;

    @Column(name = "name")
    private String name;

    protected Product() {
    }

    public Product(String category, String name) {
        this.category = category;
        this.name = name;
    }

    /*
        1. 문제 : 중복 코드
        2. 원인 : 클래스의 @Getter와 get메서드가 중복됨
        3. 개선안 : get메서드 삭제
     */
//    public String getCategory() {
//        return category;
//    }
//
//    public String getName() {
//        return name;
//    }
}
