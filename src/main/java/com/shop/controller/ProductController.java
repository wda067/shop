package com.shop.controller;

import com.shop.dto.request.ProductCreate;
import com.shop.dto.request.ProductSearch;
import com.shop.dto.response.CommonResponse;
import com.shop.dto.response.ProductResponse;
import com.shop.service.ProductService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ProductController {

    private final ProductService productService;

    @PostMapping("/product")
    public void save(@RequestBody @Validated ProductCreate request) {
        productService.save(request);
    }

    /*
    @PathVariable이 없으면, Spring MVC에서 URL 경로에 있는 변수를 메서드 파라미터로 매핑할 수 없다.
    즉, 동적인 URL 처리가 불가능해진다.
     */
    @GetMapping("/product/{productId}")
    public CommonResponse<ProductResponse> get(@PathVariable Long productId) {
        return productService.get(productId);
    }

    /*
    API 요청을 받을 때 요청 파라미터를 받아서 필요한 객체를 만들고 그 객체에 값을 넣어주어야 한다.
    Spring은 이 과정을 자동화해주는 @ModelAttribute를 제공한다
    Spring MVC는 @ModelAttribute가 있으면 객체를 생성하고 요청 파라미터의 이름으로 객체의 프로퍼티를 찾는다.
    그리고 해당 프로퍼티의 setter를 호출해서 파라미터의 값을 바인딩한다.
    @RequestParam와 @ModelAttribute는 생략 가능하며, Spring은 String같은 단순 타입은 @RequestParam,
    Argument Resolver로 지정해둔 타입 외는 @ModelAttribute를 적용한다.
     */
    @GetMapping("/product/list")
    public CommonResponse<List<ProductResponse>> getList(@ModelAttribute ProductSearch request) {
        return productService.getList(request);
    }

    @DeleteMapping("/product/{productId}")
    public void delete(@PathVariable Long productId) {
        productService.delete(productId);
    }

    @GetMapping("/test")
    public void test(@RequestParam(name = "id", defaultValue = "123") Long productId) {
        log.info("productId: {}", productId);
        productService.get(productId);
    }
}
