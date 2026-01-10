package com.wjc.codetest.product.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateProductRequest {
    private Long id;
    private String category;
    private String name;

    /*
    1. 문제 : 에러 발생 (cannot deserialize from object value (no delegate- or property-based creator))
    2. 원인 : 코드 (default 생성자 부재->jakson 라이브러리는 default 생성자를 필요로 함)
    3. 개선안 : default 생성자 추가
 */
    public UpdateProductRequest() {}

    public UpdateProductRequest(Long id) {
        this.id = id;
    }

    public UpdateProductRequest(Long id, String category) {
        this.id = id;
        this.category = category;
    }

    public UpdateProductRequest(Long id, String category, String name) {
        this.id = id;
        this.category = category;
        this.name = name;
    }
}

