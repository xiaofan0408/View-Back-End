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
@TableName("view_magnet")
@ApiModel(value = "ViewMagnetEntity对象", description = "")
public class ViewMagnetEntity extends BaseEntity<ViewMagnetEntity> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("magnet:?xt=urn:btih:")
    @TableField("magnet_xt")
    private String magnetXt;

    @TableField("gid")
    private String gid;

    @TableField("censored_id")
    private String censoredId;

    @TableField("magnet_name")
    private String magnetName;

    @TableField("magnet_type")
    private String magnetType;

    @TableField("magnet_date")
    private String magnetDate;

    @TableField("have_hd")
    private Integer haveHd;

    @ApiModelProperty("1")
    @TableField("have_sub")
    private Integer haveSub;

    @TableField("have_down")
    private Integer haveDown;


    @Override
    public Serializable pkVal() {
        return this.id;
    }

}
