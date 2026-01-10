package com.wjc.codetest.product.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateProductRequest {
    private String category;
    private String name;

    /*
        1. 문제 : 에러 발생 (cannot deserialize from object value (no delegate- or property-based creator))
        2. 원인 : 코드 (default 생성자 부재->jakson 라이브러리는 default 생성자를 필요로 함)
        3. 개선안 : default 생성자 추가
     */
    public CreateProductRequest() {}

    public CreateProductRequest(String category) {
        this.category = category;
    }

    public CreateProductRequest(String category, String name) {
        this.category = category;
        this.name = name;
    }
}

