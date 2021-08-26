package article.service;

import java.util.Map;

import article.model.Writer;

public class WriteRequest {
	private Writer wirer;
	private String title;
	private String content;
	
	public WriteRequest(Writer wirer, String title, String content) {
		super();
		this.wirer = wirer;
		this.title = title;
		this.content = content;
	}

	public Writer getWirer() {
		return wirer;
	}

	public String getTitle() {
		return title;
	}

	public String getContent() {
		return content;
	}
	
	public void validate(Map<String, Boolean> errors) {
		if(title == null || title.trim().isEmpty()) {
			errors.put("title", Boolean.TRUE);
		}
	}
}
