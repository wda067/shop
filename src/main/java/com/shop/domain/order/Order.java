package com.shop.domain.order;

import static com.shop.domain.order.OrderStatus.COMPLETED;
import static com.shop.domain.order.OrderStatus.PAYMENT_PENDING;

import com.shop.domain.Member;
import com.shop.domain.payment.Payment;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private final List<OrderProduct> orderProducts = new ArrayList<>();

    @OneToOne(mappedBy = "order", fetch = FetchType.LAZY)
    private Payment payment;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    public Order(Member member, List<OrderProduct> orderProducts) {
        this.member = member;
        orderProducts.forEach(this::addOrderProduct);
        this.status = OrderStatus.NEW;
    }

    public void addOrderProduct(OrderProduct orderProduct) {
        orderProducts.add(orderProduct);
        orderProduct.setOrder(this);
    }

    public void cancel() {
        this.status = OrderStatus.CANCELED;
        orderProducts.forEach(OrderProduct::cancel);
    }

    public void processPayment() {
        this.status = PAYMENT_PENDING;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
        this.status = COMPLETED;
    }

    public String getOrderName() {
        if (orderProducts.size() == 1) {
            return orderProducts.get(0).getProduct().getName();
        } else {
            return orderProducts.get(0).getProduct().getName()
                    + "외 " + orderProducts.size() + "건";
        }
    }

    public int getTotalPrice() {
        return orderProducts.stream()
                .mapToInt(OrderProduct::getTotalPrice)
                .sum();
    }

    public String getMemberEmail() {
        return member.getEmail();
    }
}
