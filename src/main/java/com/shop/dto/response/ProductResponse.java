package com.shop.dto.response;

import com.shop.domain.product.Product;
import com.shop.domain.product.ProductSellStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class ProductResponse {

    private String name;
    private int price;
    private int stockQuantity;
    private String description;
    private ProductSellStatus sellStatus;

    public ProductResponse(String name, int price, int stockQuantity, String description,
                           ProductSellStatus sellStatus) {
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.description = description;
        this.sellStatus = sellStatus;
    }

    public ProductResponse(Product product) {
        this.name = product.getName();
        this.price = product.getPrice();
        this.stockQuantity = product.getStockQuantity();
        this.description = product.getDescription();
        this.sellStatus = product.getSellStatus();
    }
}
