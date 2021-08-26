package article.service;

public class DeleteRequest {
	private String userId; //아이디
	private int articleNumber; //글번호
	
	public DeleteRequest(String userId, int articleNumber) {
		this.userId = userId;
		this.articleNumber = articleNumber;
	}

	public String getUserId() {
		return userId;
	}

	public int getArticleNumber() {
		return articleNumber;
	}
	
}
