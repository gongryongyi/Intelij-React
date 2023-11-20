package com.ohgiraffers.comprehensive.order.presentation;

import com.ohgiraffers.comprehensive.common.paging.Pagenation;
import com.ohgiraffers.comprehensive.common.paging.PagingButtonInfo;
import com.ohgiraffers.comprehensive.common.paging.PagingResponse;
import com.ohgiraffers.comprehensive.jwt.CustomUser;
import com.ohgiraffers.comprehensive.order.Service.OrderService;
import com.ohgiraffers.comprehensive.order.dto.request.OrderCreateRequest;
import com.ohgiraffers.comprehensive.order.dto.response.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    /* 주문 등록 */
    @PostMapping("/order")
    public ResponseEntity<Void> save(@RequestBody @Valid OrderCreateRequest orderRequest,
                                     @AuthenticationPrincipal final CustomUser customUser){  //insert할때 모든 값들을 가져온코드임
        orderService.save(orderRequest, customUser);

        return ResponseEntity.status(HttpStatus.CREATED).build(); //주문건이 있다면 uri 코드 전달 없으면 ㅡ
    }


    /* 2. 회원의 주문 목록 조회 */
    @GetMapping("/order")
    public ResponseEntity<PagingResponse> getOrders(
            @RequestParam(defaultValue = "1") final Integer page,
            @AuthenticationPrincipal CustomUser customUser
    ){
        final Page<OrderResponse> orders = orderService.getOrders(page, customUser);
        final PagingButtonInfo pagingButtonInfo = Pagenation.getPagingButtonInfo(orders);
        final PagingResponse pagingResponse = PagingResponse.of(orders.getContent(), pagingButtonInfo);

       return ResponseEntity.ok(pagingResponse);


    }


}
