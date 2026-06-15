package com.company.workforce.mapper;

import com.company.workforce.dto.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PageResponseMapper {

    public <T> PageResponse<T> map(
            Page<?> page,
            List<T> content
    ) {

        return new PageResponse<>(
                content,
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isFirst(),
                page.isLast()
        );
    }
}