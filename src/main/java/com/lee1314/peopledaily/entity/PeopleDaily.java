package com.lee1314.peopledaily.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

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
public class PeopleDaily implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private Integer id;

    /**
     * 分类ID
     */
    @JSONField(name = "seminar_id")
    private Integer seminarId;

    /**
     * 标题
     */
    private String title;

    /**
     * 作者
     */
    @JSONField(name = "admin_name")
    private String adminName;

    /**
     * 融云ID
     */
    @JSONField(name = "rongyun_id")
    private Integer rongyunId;

    /**
     * 微信uniqueID
     */
    @JSONField(name = "unique_id")
    private Integer uniqueId;

    /**
     * 内容
     */
    private String contents;

    /**
     * 音频链接
     */
    @JSONField(name = "audio_url")
    private String audioUrl;

    /**
     * 音频时长
     */
    @JSONField(name = "audio_play_time")
    private String audioPlayTime;

    /**
     * 分享链接
     */
    @JSONField(name = "share_url")
    private String shareUrl;

    /**
     * 分享图片
     */
    @JSONField(name = "share_image")
    private String shareImage;

    /**
     * 展示图片
     */
    @JSONField(name = "show_image")
    private String showImage;

    /**
     * 分享标题
     */
    @JSONField(name = "share_title")
    private String shareTitle;

    /**
     * 描述
     */
    private String description;

    /**
     * 创建时间
     */
    @JSONField(name = "news_time", format = "yyyy-MM-dd HH-mm-ss")
    private Date newsTime;


}
