package com.ohgiraffers.comprehensive.product.domain;

import com.ohgiraffers.comprehensive.product.domain.type.ProductStatusType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

import static com.ohgiraffers.comprehensive.product.domain.type.ProductStatusType.USABLE;
import static javax.persistence.EnumType.STRING;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "tbl_product")
@NoArgsConstructor(access = PROTECTED)  // 접근제한 /보다 안전하게 작성하기 위함이기때문에 없어도 상관은 없다.
@Getter
@EntityListeners(AuditingEntityListener.class)//이 entity의 변화를 감지하겠다.  감지하면 생성시간과 수정시간을 아래 있는 애들로 하겠다.
public class Product {


    @Id
    private Long productCode;
    @Column(nullable = false)
    private String productName;
    @Column(nullable = false)
    private Long productPrice;
    @Column(nullable = false)
    private String productDescription;

    @ManyToOne(fetch = FetchType.LAZY)  //FetchType.LAZY 이렇게 하면 나중에 늦게 필요하면 그때 조회하겠다
    @JoinColumn(name = "categoryCode") //fk의 이름이 들어가야함
    private Category category;


    @Column(nullable = false)
    private String productImageUrl;
    @Column(nullable = false)
    private Long productStock;


    @CreatedDate//생성시간 /해당행이 생성된 순간에 대해서 자동으로 처리를 해준다. 그럼 굳이 이컬럼을 다루지 않아도 된다.
    @Column(nullable = false)
    private LocalDateTime createdAt;



    @LastModifiedDate//수정시간 / 해당행이 생성된 순간에 대해서 자동으로 처리를 해준다. 그럼 굳이 이컬럼을 다루지 않아도 된다.
    @Column(nullable = false)
    private LocalDateTime modifiedAt;

    @Enumerated(value = STRING)
    @Column(nullable = false)
    private ProductStatusType status = USABLE;
}
