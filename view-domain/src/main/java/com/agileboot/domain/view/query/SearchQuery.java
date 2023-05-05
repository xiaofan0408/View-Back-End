package com.agileboot.domain.view.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author: xuzefan
 * @date: 2023/5/5 14:46
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Schema(name = "搜索参数")
public class SearchQuery extends PageQuery {

    private String keyword;

}
