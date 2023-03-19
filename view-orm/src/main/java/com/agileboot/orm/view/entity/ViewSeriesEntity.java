package com.agileboot.orm.view.entity;

import com.agileboot.common.core.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author xiaofan0408
 * @since 2023-03-19
 */
@Getter
@Setter
@TableName("view_series")
@ApiModel(value = "ViewSeriesEntity对象", description = "")
public class ViewSeriesEntity extends BaseEntity<ViewSeriesEntity> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("code_36")
    private String code36;

    @TableField("series_name")
    private String seriesName;

    @TableField("code_10")
    private Integer code10;


    @Override
    public Serializable pkVal() {
        return this.id;
    }

}
