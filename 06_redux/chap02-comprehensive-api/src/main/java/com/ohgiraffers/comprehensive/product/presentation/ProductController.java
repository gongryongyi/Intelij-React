package com.ohgiraffers.comprehensive.product.presentation;

import com.ohgiraffers.comprehensive.common.paging.Pagenation;
import com.ohgiraffers.comprehensive.common.paging.PagingButtonInfo;
import com.ohgiraffers.comprehensive.common.paging.PagingResponse;
import com.ohgiraffers.comprehensive.product.dto.request.ProductCreateRequest;
import com.ohgiraffers.comprehensive.product.dto.request.ProductUpdateRequest;
import com.ohgiraffers.comprehensive.product.dto.response.AdminProductResponse;
import com.ohgiraffers.comprehensive.product.dto.response.AdminProductsResponse;
import com.ohgiraffers.comprehensive.product.dto.response.CustomerProductResponse;
import com.ohgiraffers.comprehensive.product.dto.response.CustomerProductsResponse;
import com.ohgiraffers.comprehensive.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ProductController {

    private final ProductService productService;

    /* 1. 상품 목록 조회 = 페이징, 주문 불가 상품 제외(고객) */
    @GetMapping("/products")
    public ResponseEntity<PagingResponse> getCustomerProducts(@RequestParam(defaultValue = "1") final Integer page){

        final Page<CustomerProductsResponse> products = productService.getCustomerProducts(page);
        final PagingButtonInfo pagingButtonInfo = Pagenation.getPagingButtonInfo(products);
        final PagingResponse pagingResponse = PagingResponse.of(products.getContent(), pagingButtonInfo);

        return ResponseEntity.ok(pagingResponse);
    }

    /* 2. 상품 목록 조회 = 페이징, 주문 불가 상품 포함(관리자) */
    @GetMapping("/products-management")
    public ResponseEntity<PagingResponse> getAdminProducts(@RequestParam(defaultValue = "1") final Integer page){

        final Page<AdminProductsResponse> products = productService.getAdminProducts(page);
        final PagingButtonInfo pagingButtonInfo = Pagenation.getPagingButtonInfo(products);
        final PagingResponse pagingResponse = PagingResponse.of(products.getContent(), pagingButtonInfo);

        return ResponseEntity.ok(pagingResponse);
    }
    /* 3. 상품 목록 조회 - 카테고리 기준, 페이징, 주문 불가 상품 제외(고객) */
    @GetMapping("/products/categories/{categoryCode}")
    public ResponseEntity<PagingResponse> getCustomerProductByProducts
    (@RequestParam(defaultValue = "1") final Integer page, @PathVariable final Long categoryCode){

        final Page<CustomerProductsResponse> products = productService.getCustomerProductByProducts(page, categoryCode);
        final PagingButtonInfo pagingButtonInfo = Pagenation.getPagingButtonInfo(products);
        final PagingResponse pagingResponse = PagingResponse.of(products.getContent(), pagingButtonInfo);

        return ResponseEntity.ok(pagingResponse);
    }

    /* 4. 상품 목록 조회 - 상품명 검색 기준, 페이징, 주문 불가 상품 제외(고객) */

    @GetMapping("/products/search")
    public ResponseEntity<PagingResponse> getCustomerProductByProductsName
            (@RequestParam(defaultValue = "1") final Integer page, @RequestParam final String productName){

        final Page<CustomerProductsResponse> products = productService.getCustomerProductByProductsName(page, productName);
        final PagingButtonInfo pagingButtonInfo = Pagenation.getPagingButtonInfo(products);
        final PagingResponse pagingResponse = PagingResponse.of(products.getContent(), pagingButtonInfo);

        return ResponseEntity.ok(pagingResponse);


    }

    /* 5. 상품 상세 조회 - productCode로 상품 1개 조회, 주문 불가 상품 제외(고객) */
    @GetMapping("/products/{productCode}")
    public ResponseEntity<CustomerProductResponse> getCustomerProduct(@PathVariable final Long productCode){
        final CustomerProductResponse customerProductResponse = productService.getCustomerProduct(productCode);

        return ResponseEntity.ok(customerProductResponse);
    }

    /* 6. 상품 상세 조회 - productCode로 상품 1개 조회, 주문 불가 상품 포함(관리자) */
    @GetMapping("/products-management/{productCode}")
    public ResponseEntity<AdminProductResponse> getAdminProduct(@PathVariable final Long productCode){
        final AdminProductResponse adminProductResponse = productService.getAdminProduct(productCode);

        return ResponseEntity.ok(adminProductResponse);
    }

    /* 7. 상품 등록(관리자) */
    @PostMapping("/products")
    public ResponseEntity<Void> save(@RequestPart @Valid final ProductCreateRequest productRequest,
                                     @RequestPart final MultipartFile productImg){

        final Long productCode = productService.save(productImg, productRequest);

        return ResponseEntity.created(URI.create("/products-management/" + productCode)).build();
    }


    /* 8. 상품 수정(관리자) */
    @PutMapping("/products/{productCode}")
    public ResponseEntity<Void> update(@PathVariable final Long productCode,
                                       @RequestPart @Valid final ProductUpdateRequest productRequest,
                                       @RequestPart(required = false) final MultipartFile productImg){

        productService.update(productCode, productImg, productRequest);

        return ResponseEntity.created(URI.create("/products-management/" + productCode)).build();
    }
    /* 9. 상품 삭제(관리자) */
    @DeleteMapping("/products/{productCode}")
    public ResponseEntity<Void> delete(@PathVariable final Long productCode){

        productService.delete(productCode);

        return ResponseEntity.noContent().build();
    }



}
