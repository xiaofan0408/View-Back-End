package com.agileboot.common.core.page;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Pagination {

    private Long currentPage;

    private boolean hasNextPage;

    private Long nextPage;

    private List<Long> pages;

}
