package com.ohgiraffers.comprehensive.product.service;

import com.ohgiraffers.comprehensive.product.domain.Product;
import com.ohgiraffers.comprehensive.product.domain.repository.ProductRepository;
import com.ohgiraffers.comprehensive.product.dto.response.AdminProductsResponse;
import com.ohgiraffers.comprehensive.product.dto.response.CustomerProductsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.ohgiraffers.comprehensive.product.domain.type.ProductStatusType.DELETED;
import static com.ohgiraffers.comprehensive.product.domain.type.ProductStatusType.USABLE;

@Service
@RequiredArgsConstructor// 반드시 필요한 argument를 전달받는 constructor
//이거 해주면
//public ProductService(ProductRepository productRepository){
//        this.productRepository = productRepository;
//   } 생성자 안만들어도 됨
public class ProductService {

    private final ProductRepository productRepository;

    private Pageable getPageable(final Integer page){
        return PageRequest.of(page -1, 10, Sort.by("productCode").descending());
    }


    /* 1. 상품 목록 조회 : 페이징, 주문 불가 상품 제외 (고객) */
    @Transactional(readOnly = true)
    public Page<CustomerProductsResponse> getCustomerProducts(final Integer page){

        Page<Product> products = productRepository.findByStatus(getPageable(page), USABLE);
        //Page<Product> products를 Page<CustomerProductsResponse> 이렇게 변환하고 싶다.

        return products.map(product -> CustomerProductsResponse.from(product));//product 엔티티로 부터 필요한 CustomerProductsResponse을 꺼내서 사용한다.
    }

    /* 2. 상품 목록 조회 : 페이징, 주문 불가 상품 포함 (관리자) */
    @Transactional(readOnly = true)
    public Page<AdminProductsResponse> getAdminProducts(final Integer page){

        Page<Product> products = productRepository.findByStatusNot(getPageable(page), DELETED);
        //Page<Product> products를 Page<CustomerProductsResponse> 이렇게 변환하고 싶다.

        return products.map(product -> AdminProductsResponse.from(product));//product 엔티티로 부터 필요한 CustomerProductsResponse을 꺼내서 사용한다.
    }





}
