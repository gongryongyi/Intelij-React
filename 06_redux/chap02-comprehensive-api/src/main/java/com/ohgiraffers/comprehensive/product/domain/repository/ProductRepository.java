package com.ohgiraffers.comprehensive.product.domain.repository;

import com.ohgiraffers.comprehensive.product.domain.Product;
import com.ohgiraffers.comprehensive.product.domain.type.ProductStatusType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/* 연관 관계 매핑 시 연관 대산 entity의 pk 별로 한 번씩 구문이 발생하는 N + 1 문제가 있다.
* 해당 필드를 미사용 : FetchType.LAZY  -> 아예 조회 하지 않음
* 해당 필드 사용 : fetch join (JPQL), @EntityGraph (QQuery Method) -> join해서 가져옴
* */
public interface ProductRepository extends JpaRepository<Product, Long> { //entity와 entity의 pk의 타입

    /* 1. 상품 목록 조회 : 페이징, 주문 불가 상품 제외 (고객) */
    Page<Product> findByStatus(Pageable pageable, ProductStatusType productStatusType);

    /* 2. 상품 목록 조회 : 페이징, 주문 불가 상품 포함 (관리자) */
    @EntityGraph(attributePaths = {"category"})  //category 애초에 처음부터 join 해서 가져와
    Page<Product> findByStatusNot(Pageable pageable, ProductStatusType productStatusType); //findByStatus가 아닌 애들만 조회하겠다 하면 뒤에 Not 붙임


    /* 3. 상품 목록 조회 - 카테고리 기준, 페이징, 주문 불가 상품 제외(고객) */
    Page<Product> findByCategoryCategoryCodeAndStatus(Pageable pageable, Long categoryCode, ProductStatusType productStatusType);

    /* 4. 상품 목록 조회 - 상품명 검색 기준, 페이징, 주문 불가 상품 제외(고객) */
    Page<Product> findByProductNameContainsAndStatus(Pageable pageable, String productName, ProductStatusType productStatusType);  //Contians 포함하고 있는거 원한다

    /* 5. 상품 상세 조회 - productCode로 상품 1개 조회, 주문 불가 상품 제외(고객) */
    Optional<Product> findByProductCodeAndStatus(Long productCode, ProductStatusType productStatusType);  //null이 반환될수 있기 때문에 Optional 타입 쓰는 거임

    /* 6. 상품 상세 조회 - productCode로 상품 1개 조회, 주문 불가 상품 포함(관리자) */
    @EntityGraph(attributePaths = {"category"})  // category 와 join하겠다  이거 안하면 product 한번 category 한번 조회함
    Optional<Product> findByProductCodeAndStatusNot(Long productCode, ProductStatusType productStatusType);


}



