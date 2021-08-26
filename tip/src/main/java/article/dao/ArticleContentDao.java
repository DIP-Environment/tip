package article.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import article.model.ArticleContent;
import jdbc.JdbcUtil;

public class ArticleContentDao {
	
	public ArticleContent insert(Connection conn, ArticleContent content) throws SQLException{
		PreparedStatement pstmt = null;
		
		//< ----------------  mariaDB, Oracle  ----------------------->
		try {
			pstmt = conn.prepareStatement("insert into say_article_content "
					+ "(article_no, content) values(?,?)");
			pstmt.setLong(1, content.getNumber());
			pstmt.setString(2, content.getContent());
			int insertedCount = pstmt.executeUpdate(); //insert성공하면 숫자가 1이 들어감.
			if(insertedCount > 0) { //성공하면 0이상이니깐 insert
				return content;
			}else { //실패하면 null을 반환해줌.
				return null;
			}
		}finally {
			JdbcUtil.close(pstmt);
		}
	}
	
	public ArticleContent selectById(Connection conn, int id) throws SQLException{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement("select * from say_article_content where article_no = ?");
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			ArticleContent content = null;
			if(rs.next()) {
				content = new ArticleContent(
						rs.getInt("article_no"), rs.getString("content"));
			}
			return content;
		}finally {
			JdbcUtil.close(rs);
			JdbcUtil.close(pstmt);
		}
	}
	
	public int update(Connection conn, int no, String content) throws SQLException{
		try (PreparedStatement pstmt = conn.prepareStatement(
				"update say_article_content set content=? "
				+ "where article_no=?")){
			pstmt.setString(1, content);
			pstmt.setInt(2, no);
			return pstmt.executeUpdate();
		}
	}
	
	public int delete(Connection conn, int no) throws SQLException{
		try (PreparedStatement pstmt = conn.prepareStatement(
				"delete from say_article_content where article_no=?")){
			pstmt.setInt(1, no);
			return pstmt.executeUpdate();
		}
	}
}
