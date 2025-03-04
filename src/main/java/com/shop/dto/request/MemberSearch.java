package com.shop.dto.request;

import static java.lang.Math.max;
import static java.lang.Math.min;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Getter
@Setter
public class MemberSearch {

    private static final int MAX_SIZE = 2_000;

    private String query = "";
    private Integer page = 1;
    private Integer size = 5;

    public long getOffset() {
        return (long) (max(1, page) - 1) * min(size, MAX_SIZE);
    }

    public Pageable getPageable() {
        return PageRequest.of(max(1, page) - 1, min(size, MAX_SIZE));
    }
}
