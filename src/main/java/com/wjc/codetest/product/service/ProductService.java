package com.wjc.codetest.product.service;

import com.wjc.codetest.product.model.request.CreateProductRequest;
import com.wjc.codetest.product.model.request.GetProductListRequest;
import com.wjc.codetest.product.model.domain.Product;
import com.wjc.codetest.product.model.request.UpdateProductRequest;
import com.wjc.codetest.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Product create(CreateProductRequest dto) {
        Product product = new Product(dto.getCategory(), dto.getName());
        return productRepository.save(product);
    }

    public Product getProductById(Long productId) {
        Optional<Product> productOptional = productRepository.findById(productId);
        if (!productOptional.isPresent()) {
            throw new RuntimeException("product not found");
        }
        return productOptional.get();
    }

    public Product update(UpdateProductRequest dto) {
        Product product = getProductById(dto.getId());
        product.setCategory(dto.getCategory());
        product.setName(dto.getName());
        /*
            1. 문제 : 불필요한 변수 생성
            2. 원인 : 변수를 생성하여 값을 담고 별다른 추가 작업 없이 바로 리턴함
            3. 개선안 : 변수에 담지 않고 바로 return 시킴
         */
//        Product updatedProduct = productRepository.save(product);
//        return updatedProduct;
        return productRepository.save(product);
    }

    public void deleteById(Long productId) {
        Product product = getProductById(productId);
        productRepository.delete(product);
    }

    public Page<Product> getListByCategory(GetProductListRequest dto) {
        /*
            1-1. 문제 : 요청했을 때 기대한 페이지와 맞지 않는 응답을 받음
            2-1. 원인 : 요청 시 페이지가 1부터 시작한다는 가정하에 페이지 값을 지정하였는데, PageRequest.of()는 페이지를 0에서부터 시작하는 것으로 보기 때문
            3-1. 개선안 : 사용자들이 페이지를 지정한다면 보통 1부터 시작하는 것으로 가정하고 지정한다고 생각함(ex. 웹페이지의 검색 결과 / 보고서의 본문 페이지 표기 등)
                         따라서 요청을 통해 들어온 페이지 값(사용자의 기대값)에서 1을 빼서 메서드의 인자로 넘겨서 메서드 조건에 맞춤

            1-2. 문제 : 데이터 조회 시 데이터 순서가 정렬되지 않은 상태로 조회됨
            2-2. 원인 : 데이터 정렬 조건을 카테고리로 했기 때문에, 같은 카테고리 내에서는 정렬 기준이 없기 때문
            3-2. 개선안 : id 순서대로 정렬함(id는 자동생성되기에 빈 값이나 중복되는 값일 수 없기 때문 & 카테고리는 쿼리에 where 조건으로 들어가기에 정렬에서 제외함)
         */
        PageRequest pageRequest = PageRequest.of(dto.getPage()-1, dto.getSize(), Sort.by(Sort.Direction.ASC, "id"));
        return productRepository.findAllByCategory(dto.getCategory(), pageRequest);
    }

    public List<String> getUniqueCategories() {
        return productRepository.findDistinctCategories();
    }
}