package article.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import article.model.ArticleData;
import article.service.ArticleContentNotFoundException;
import article.service.ArticleNotFoundException;
import article.service.ReadArticleService;
import mvc.command.CommandHandler;

public class ReadArticleHandler implements CommandHandler {
	
	private ReadArticleService readService = new ReadArticleService();

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String noval = request.getParameter("no");
		int articleNum = Integer.parseInt(noval);
		try {
			ArticleData articleData = readService.getArticle(articleNum, true);
			request.setAttribute("articleData", articleData);
			return "/WEB-INF/view/sayDetail.jsp";
		}catch (ArticleNotFoundException | ArticleContentNotFoundException ex) {
			request.getServletContext().log("no article", ex);
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}
	}
}
