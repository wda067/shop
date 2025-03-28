package com.shop.service;

import com.shop.domain.member.Member;
import com.shop.domain.order.Order;
import com.shop.domain.order.OrderProduct;
import com.shop.domain.product.Product;
import com.shop.dto.request.OrderRequest;
import com.shop.dto.response.CommonResponse;
import com.shop.dto.response.OrderResponse;
import com.shop.exception.MemberNotFound;
import com.shop.exception.OrderMemberMismatch;
import com.shop.exception.OrderNotFound;
import com.shop.exception.ProductNotFound;
import com.shop.repository.OrderRepository;
import com.shop.repository.member.MemberRepository;
import com.shop.repository.product.ProductRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    @Transactional
    public synchronized void order(Long memberId, OrderRequest request) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(MemberNotFound::new);
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(ProductNotFound::new);
        product.removeStock(request.getQuantity());

        OrderProduct orderProduct = new OrderProduct(product, request.getQuantity());
        List<OrderProduct> orderProducts = new ArrayList<>();
        orderProducts.add(orderProduct);

        Order order = new Order(member, orderProducts);
        orderRepository.save(order);
    }

    @Transactional
    public void cancel(Long memberId, Long orderId) {
        Order order = getOrderForMember(memberId, orderId);
        order.cancel();
    }

    @Transactional(readOnly = true)
    public CommonResponse<OrderResponse> get(Long memberId, Long orderId) {
        Order order = getOrderForMember(memberId, orderId);

        int totalAmount = order.getOrderProducts().stream()
                .mapToInt(OrderProduct::getOrderPrice)
                .sum();

        OrderResponse orderResponse = OrderResponse.builder()
                .email(order.getMemberEmail())
                .orderProduct(order.getOrderName())
                .totalAmount(String.valueOf(totalAmount))
                .status(order.getStatus().toString())
                .build();

        return CommonResponse.success(orderResponse);
    }

    private Order getOrderForMember(Long memberId, Long orderId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(MemberNotFound::new);
        Order order = orderRepository.findById(orderId)
                .orElseThrow(OrderNotFound::new);

        Member orderMember = order.getMember();
        boolean isNotOrderedMember = !member.getId().equals(orderMember.getId());
        if (isNotOrderedMember) {
            throw new OrderMemberMismatch();
        }

        return order;
    }
}
