package article.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Article {
	private Integer number;
	private Writer writer;
	private String title;
	private Date regDate;
	private Date modifiedDate;
	
	public Article(Integer number, Writer writer, String title, Date regDate, Date modifiedDate) throws ParseException {
		this.number = number;
		this.writer = writer;
		this.title = title;
		this.regDate = regDate;
		this.modifiedDate = modifiedDate;
	}
	
	public Integer getNumber() {
		return number;
	}
	public Writer getWriter() {
		return writer;
	}
	public String getTitle() {
		return title;
	}
	public Date getRegDate() {
		return regDate;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
}
