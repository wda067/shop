package com.shop.repository.product;

import com.shop.dto.request.ProductSearch;
import com.shop.dto.response.ProductResponse;
import org.springframework.data.domain.Page;

public interface ProductRepositoryCustom {

    Page<ProductResponse> getList(ProductSearch productSearch);
}
