package com.lee1314.peopledaily.model.po;

import java.util.Date;

public class SysConfigPo {
	private String business;

	private String code;

	private String content;

	private String description;

	private Date createDate;

	private Date modifyData;

	public String getBusiness() {
		return business;
	}

	public void setBusiness(String business) {
		this.business = business == null ? null : business.trim();
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code == null ? null : code.trim();
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content == null ? null : content.trim();
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description == null ? null : description.trim();
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getModifyData() {
		return modifyData;
	}

	public void setModifyData(Date modifyData) {
		this.modifyData = modifyData;
	}

	@Override
	public String toString() {
		return "SysConfigPo [business=" + business + ", code=" + code + ", content=" + content + ", description="
				+ description + ", createDate=" + createDate + ", modifyData=" + modifyData + "]";
	}
}