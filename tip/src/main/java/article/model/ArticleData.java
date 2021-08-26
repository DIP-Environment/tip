package article.model;

import java.util.Date;

public class ArticleData {
	private Article article;
	private ArticleContent content;
	public Date today = new Date();
	
	public ArticleData(Article article, ArticleContent content) {
		super();
		this.article = article;
		this.content = content;
	}

	public Article getArticle() {
		return article;
	}

	public String getContent() {
		return content.getContent();
	}
	
	
}
