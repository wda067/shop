package com.shop.repository.member;

import static com.shop.domain.QMember.member;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shop.dto.request.MemberSearch;
import com.shop.dto.response.MemberResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

@RequiredArgsConstructor
public class MemberRepositoryCustomImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<MemberResponse> getList(MemberSearch request) {

        BooleanBuilder builder = new BooleanBuilder();
        if (request.getQuery() != null && !request.getQuery().isBlank()) {
            builder.and(member.email.like("%" + request.getQuery() + "%"));
        }

        Long totalCount = queryFactory.select(member.count())
                .from(member)
                .fetchFirst();

        List<MemberResponse> members = queryFactory.select(
                        Projections.constructor(
                                MemberResponse.class,
                                member.email,
                                member.name))
                .from(member)
                .where(builder)
                .limit(request.getSize())
                .offset(request.getOffset())
                .orderBy(member.id.desc())
                .fetch();

        return new PageImpl<>(members, request.getPageable(), totalCount);
    }
}
