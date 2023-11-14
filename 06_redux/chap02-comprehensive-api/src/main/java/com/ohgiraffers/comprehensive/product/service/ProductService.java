package com.ohgiraffers.comprehensive.product.service;

import com.ohgiraffers.comprehensive.common.exception.BadRequestException;
import com.ohgiraffers.comprehensive.common.util.FileUploadUtils;
import com.ohgiraffers.comprehensive.product.domain.Category;
import com.ohgiraffers.comprehensive.product.domain.Product;
import com.ohgiraffers.comprehensive.product.domain.repository.CategoryRepository;
import com.ohgiraffers.comprehensive.product.domain.repository.ProductRepository;
import com.ohgiraffers.comprehensive.product.dto.request.ProductCreateRequest;
import com.ohgiraffers.comprehensive.product.dto.request.ProductUpdateRequest;
import com.ohgiraffers.comprehensive.product.dto.response.AdminProductResponse;
import com.ohgiraffers.comprehensive.product.dto.response.AdminProductsResponse;
import com.ohgiraffers.comprehensive.product.dto.response.CustomerProductResponse;
import com.ohgiraffers.comprehensive.product.dto.response.CustomerProductsResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

import static com.ohgiraffers.comprehensive.common.exception.type.ExceptionCode.ExceptionCode.NOT_FOUND_CATEGORY_CODE;
import static com.ohgiraffers.comprehensive.common.exception.type.ExceptionCode.ExceptionCode.NOT_FOUND_PRODUCT_CODE;
import static com.ohgiraffers.comprehensive.product.domain.type.ProductStatusType.DELETED;
import static com.ohgiraffers.comprehensive.product.domain.type.ProductStatusType.USABLE;

@Service
@RequiredArgsConstructor// 반드시 필요한 argument를 전달받는 constructor
//이거 해주면
//public ProductService(ProductRepository productRepository){
//        this.productRepository = productRepository;
//   } 생성자 안만들어도 됨
@Transactional
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Value("${image.image-url}")
    private String IMAGE_URL;
    @Value("${image.image-dir}")
    private String IMAGE_DIR;

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

    /* 3. 상품 목록 조회 - 카테고리 기준, 페이징, 주문 불가 상품 제외(고객) */
    @Transactional(readOnly = true)
    public Page<CustomerProductsResponse> getCustomerProductByProducts(final Integer page, final Long categoryCode){

        Page<Product> products = productRepository.findByCategoryCategoryCodeAndStatus(getPageable(page), categoryCode, USABLE);


        return products.map(product -> CustomerProductsResponse.from(product));//product 엔티티로 부터 필요한 CustomerProductsResponse을 꺼내서 사용한다.
    }

    /* 4. 상품 목록 조회 - 상품명 검색 기준, 페이징, 주문 불가 상품 제외(고객) */
    @Transactional(readOnly = true)
    public Page<CustomerProductsResponse> getCustomerProductByProductsName(final Integer page, final String productName){

        Page<Product> products = productRepository.findByProductNameContainsAndStatus(getPageable(page), productName, USABLE);


        return products.map(product -> CustomerProductsResponse.from(product));//product 엔티티로 부터 필요한 CustomerProductsResponse을 꺼내서 사용한다.
    }

    /* 5. 상품 상세 조회 - productCode로 상품 1개 조회, 주문 불가 상품 제외(고객) */
    @Transactional(readOnly = true)
    public CustomerProductResponse getCustomerProduct(final Long productCode){
      Product product = productRepository.findByProductCodeAndStatus(productCode, USABLE)
              .orElseThrow(() -> new BadRequestException(NOT_FOUND_PRODUCT_CODE));

      return CustomerProductResponse.from(product);

    }
    /* 6. 상품 상세 조회 - productCode로 상품 1개 조회, 주문 불가 상품 포함(관리자) */
    @Transactional(readOnly = true)
    public AdminProductResponse getAdminProduct(final Long productCode){
        Product product = productRepository.findByProductCodeAndStatusNot(productCode, DELETED)
                .orElseThrow(()-> new BadRequestException(NOT_FOUND_PRODUCT_CODE));

        return AdminProductResponse.from(product);// product entity를 AdminProductResponse 형태로 변경
    }

    private String getRandomName(){
            return UUID.randomUUID().toString().replace("-", "");
    }


    /* 7. 상품 등록(관리자) */
    public Long save(final MultipartFile productImg, final ProductCreateRequest productRequest) {

        /* 전달 된 파일을 서버의 지정 경로에 저장 */

        String replaceFileName = FileUploadUtils.saveFile(IMAGE_DIR,getRandomName(),productImg);  //저장하고싶은 경로 이름

        Category category = categoryRepository.findById(productRequest.getCategoryCode())
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_CATEGORY_CODE));

        final Product newProduct = Product.of(
                productRequest.getProductName(),
                productRequest.getProductPrice(),
                productRequest.getProductDescription(),
                category,
                IMAGE_URL + replaceFileName,
        productRequest.getProductStock()
        );

        final Product product = productRepository.save(newProduct);

        return product.getProductCode();
    }

    /* 8. 상품 수정(관리자) */
    public void update(final Long productCode, final MultipartFile productImg, final ProductUpdateRequest productRequest) {  //ProductUpdateRequest productRequest 변경하려고 넘어올 애들

        Product product = productRepository.findByProductCodeAndStatusNot(productCode, DELETED)
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_PRODUCT_CODE));


        Category category = categoryRepository.findById(productRequest.getCategoryCode())
                .orElseThrow(() -> new BadRequestException(NOT_FOUND_PRODUCT_CODE));

        /* 이미지 수정 시 새로운 이미지 저장 후 기존 이미지 삭제 로직 필요 */
        if (productImg != null){
            /* 새로 입력 된 이미지 저장 */
            String replaceFileName = FileUploadUtils.saveFile(IMAGE_DIR, getRandomName(), productImg);
            /* 기존 이미지 삭제 */
            FileUploadUtils.deleteFile(IMAGE_DIR, product.getProductImageUrl().replace(IMAGE_URL, ""));
            /* entity 정보 변경 */
            product.updateProductImageUrl(IMAGE_URL + replaceFileName);
        }

        product.update(
                productRequest.getProductName(),
                productRequest.getProductPrice(),
                productRequest.getProductDescription(),
                category,
                productRequest.getProductStock(),
                productRequest.getStatus()
        );




    }

    /* 9. 상품 삭제(관리자) */
    public void delete(final Long productCode){

        productRepository.deleteById(productCode);

    }
}
