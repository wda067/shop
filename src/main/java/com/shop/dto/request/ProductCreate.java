package com.shop.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductCreate {

    @NotBlank(message = "상품명을 입력해 주세요.")
    @Size(max = 100, message = "상품명은 100자까지 입력해 주세요.")
    private final String name;

    @Min(value = 100, message = "가격은 100원 이상 입력해 주세요.")
    @Max(value = 10_000_000, message = "가격은 1,000만원까지 입력해 주세요.")
    private final int price;

    @Min(value = 1, message = "상품 재고는 최소 1개 이상이어야 합니다.")
    @Max(value = 10_000, message = "상품 재고는 10,000개까지 입력해 주세요.")
    private final int stockQuantity;

    @NotBlank(message = "상품 설명을 입력해 주세요.")
    @Size(max = 1_000, message = "상품 설명은 1,000자까지 입력해 주세요.")
    private final String description;
}
