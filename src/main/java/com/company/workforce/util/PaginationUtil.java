package com.company.workforce.util;

import com.company.workforce.constants.EmployeeSortField;
import com.company.workforce.constants.PaginationConstants;
import com.company.workforce.constants.SortDirection;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public final class PaginationUtil {

    private PaginationUtil() {
    }

    public static String getSortField(
            String sortBy
    ) {

        try {

            return EmployeeSortField
                    .valueOf(sortBy.toUpperCase())
                    .getFieldName();

        } catch (IllegalArgumentException ex) {

            return EmployeeSortField.ID.getFieldName();
        }
    }

    public static int normalizeSize(
            int size
    ) {

        return Math.min(
                size,
                PaginationConstants.MAX_PAGE_SIZE
        );
    }

    public static Pageable buildPageable(
            int page,
            int size,
            String sortBy,
            SortDirection direction
    ) {

        String sortField =
                getSortField(sortBy);

        Sort sort =
                direction == SortDirection.DESC
                        ? Sort.by(sortField).descending()
                        : Sort.by(sortField).ascending();

        return PageRequest.of(
                page,
                size,
                sort
        );
    }

}