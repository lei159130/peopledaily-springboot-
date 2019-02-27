package com.lee1314.peopledaily.model.vo;

import java.util.List;
import java.util.Map;

public class CodeMsgVo<T> {
	private Map<String, String> result;
	private List<T> data;

	public Map<String, String> getResult() {
		return result;
	}

	public void setResult(Map<String, String> result) {
		this.result = result;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

}
