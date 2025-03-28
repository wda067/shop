package com.shop.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.domain.product.Product;
import com.shop.domain.product.ProductSellStatus;
import com.shop.dto.request.ProductCreate;
import com.shop.repository.product.ProductRepository;
import java.util.List;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();
    }

    @Test
    void 상품을_등록한다() throws Exception {
        //given
        ProductCreate request = ProductCreate.builder()
                .name("테스트 상품")
                .price(10_000)
                .stockQuantity(100)
                .description("테스트 상품입니다.")
                .build();

        String json = objectMapper.writeValueAsString(request);

        //when
        ResultActions resultActions = mockMvc.perform(post("/api/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print());

        //then
        assertEquals(1L, productRepository.count());
        resultActions.andExpect(status().isOk());
    }

    @Test
    void 상품의_가격은_천만원을_초과할_수_없다() throws Exception {
        //given
        ProductCreate request = ProductCreate.builder()
                .name("테스트 상품")
                .price(100_000_000)
                .stockQuantity(100)
                .description("테스트 상품입니다.")
                .build();

        String json = objectMapper.writeValueAsString(request);

        //when
        ResultActions resultActions = mockMvc.perform(post("/api/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print());

        //then
        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.validation.price")
                        .value("가격은 1,000만원까지 입력해 주세요."));
    }

    @Test
    void 상품의_재고는_만개를_초과할_수_없다() throws Exception {
        //given
        ProductCreate request = ProductCreate.builder()
                .name("테스트 상품")
                .price(100_000)
                .stockQuantity(100_000)
                .description("테스트 상품입니다.")
                .build();

        String json = objectMapper.writeValueAsString(request);

        //when
        ResultActions resultActions = mockMvc.perform(post("/api/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print());

        //then
        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.validation.stockQuantity")
                        .value("상품 재고는 10,000개까지 입력해 주세요."));
    }

    @Test
    void 상품_목록을_페이징_처리하여_조회한다() throws Exception {
        //given
        List<Product> products = IntStream.range(1, 31)
                .mapToObj(i -> Product.builder()
                        .name("테스트 상품" + i)
                        .price(10_000)
                        .stockQuantity(100)
                        .description("테스트 상품" + i + " 입니다.")
                        .sellStatus(ProductSellStatus.SELL)
                        .build())
                .toList();

        productRepository.saveAll(products);

        //when
        ResultActions resultActions = mockMvc.perform(get("/api/product/list")
                        .param("page", "1")
                        .param("size", "10")
                        .param("query", ""))
                .andDo(print());

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].name").value("테스트 상품30"))
                .andExpect(jsonPath("$.[9].name").value("테스트 상품21"));
    }

    @Test
    void 상품명으로_상품을_조회한다() throws Exception {
        //given
        List<Product> products = IntStream.range(1, 31)
                .mapToObj(i -> Product.builder()
                        .name("테스트 상품" + i)
                        .price(10_000)
                        .stockQuantity(100)
                        .description("테스트 상품" + i + " 입니다.")
                        .sellStatus(ProductSellStatus.SELL)
                        .build())
                .toList();

        productRepository.saveAll(products);

        //when
        ResultActions resultActions = mockMvc.perform(get("/api/product/list")
                        .param("query", "테스트 상품3"))
                .andDo(print());

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].name").value("테스트 상품30"))
                .andExpect(jsonPath("$.[1].name").value("테스트 상품3"));
    }
}