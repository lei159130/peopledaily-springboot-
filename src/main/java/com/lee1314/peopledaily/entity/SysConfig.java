package com.lee1314.peopledaily.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author leili
 * @since 2021-07-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 业务模块
     */
    private String business;

    /**
     * 业务功能代码
     */
    private String code;

    /**
     * 业务功能对应值
     */
    private String content;

    /**
     * 业务功能代码描述
     */
    private String description;

    /**
     * 创建日期
     */
    private LocalDateTime createDate;

    /**
     * 修改日期
     */
    private LocalDateTime modifyData;


}
