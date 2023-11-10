package com.ohgiraffers.comprehensive.product.domain.repository;

import com.ohgiraffers.comprehensive.product.domain.Product;
import com.ohgiraffers.comprehensive.product.domain.type.ProductStatusType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

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

}
