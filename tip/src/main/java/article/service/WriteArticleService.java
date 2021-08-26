package article.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;

import article.dao.ArticleContentDao;
import article.dao.ArticleDao;
import article.model.Article;
import article.model.ArticleContent;
import jdbc.JdbcUtil;
import jdbc.connection.ConnectionProvider;

public class WriteArticleService {
	private ArticleDao articleDao = new ArticleDao();
	private ArticleContentDao contentDao = new ArticleContentDao();
	
	public Integer write(WriteRequest req) throws SQLException, ParseException {
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection();
			conn.setAutoCommit(false);
			
			Article article = toArticle(req);
			Article savedArticle = articleDao.insert(conn, article);
			if(savedArticle == null) {
				throw new RuntimeException("fail to insert article");
			}
			ArticleContent content = new ArticleContent(
					savedArticle.getNumber(),
					req.getContent());
			ArticleContent savedContent = contentDao.insert(conn, content); //�۹�ȣ�� �������. db����
			if(savedArticle == null) {
				throw new RuntimeException("fail to insert article");
			}
			conn.commit();
			
			return savedArticle.getNumber();
		}catch (SQLException ex) {
			JdbcUtil.rollback(conn);
			throw new RuntimeException(ex);
		}catch (RuntimeException ex) {
			JdbcUtil.rollback(conn);
			throw ex;
		}finally {
			JdbcUtil.close(conn);
		}
	}

	private Article toArticle(WriteRequest req) throws ParseException {
		Date now = new Date();
		return new Article(null, req.getWirer(), req.getTitle(), now, now);
	}
}
