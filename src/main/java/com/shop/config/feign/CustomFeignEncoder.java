package com.shop.config.feign;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.RequestTemplate;
import feign.codec.Encoder;
import java.lang.reflect.Type;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
public class CustomFeignEncoder implements Encoder {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void encode(Object object, Type bodyType, RequestTemplate template) {
        try {
            String jsonBody = objectMapper.writeValueAsString(object);
            log.info("[Feign Encoder] Request Body: {}", jsonBody);
            template.body(jsonBody.getBytes(), template.requestCharset());
        } catch (Exception e) {
            log.error("[Feign Encoder] Encoding error: ", e);
            throw new RuntimeException(e);
        }
    }
}
