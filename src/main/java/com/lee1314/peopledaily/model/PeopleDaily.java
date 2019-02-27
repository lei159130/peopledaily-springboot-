package com.lee1314.peopledaily.model;

import java.util.Date;

public class PeopleDaily {
	private Integer id;

	private String title;

	private String audioPlayTime;

	private String audioUrl;

	private Integer commentCount;

	private Date dateTime;

	private String shareUrl;

	private String content;

	private Integer seminarId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title == null ? null : title.trim();
	}

	public String getAudioPlayTime() {
		return audioPlayTime;
	}

	public void setAudioPlayTime(String audioPlayTime) {
		this.audioPlayTime = audioPlayTime == null ? null : audioPlayTime.trim();
	}

	public String getAudioUrl() {
		return audioUrl;
	}

	public void setAudioUrl(String audioUrl) {
		this.audioUrl = audioUrl == null ? null : audioUrl.trim();
	}

	public Integer getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	public String getShareUrl() {
		return shareUrl;
	}

	public void setShareUrl(String shareUrl) {
		this.shareUrl = shareUrl == null ? null : shareUrl.trim();
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content == null ? null : content.trim();
	}

	public Integer getSeminarId() {
		return seminarId;
	}

	public void setSeminarId(Integer seminarId) {
		this.seminarId = seminarId;
	}

}