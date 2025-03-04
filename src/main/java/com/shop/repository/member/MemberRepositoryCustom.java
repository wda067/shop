package com.shop.repository.member;

import com.shop.dto.request.MemberSearch;
import com.shop.dto.response.MemberResponse;
import org.springframework.data.domain.Page;

public interface MemberRepositoryCustom {

    Page<MemberResponse> getList(MemberSearch memberSearch);
}
