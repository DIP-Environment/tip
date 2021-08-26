package article.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import article.service.ArticlePage;
import article.service.ListArticleService;
import mvc.command.CommandHandler;

public class ListArticleHandler implements CommandHandler {
	private ListArticleService listService = new ListArticleService();
	
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 한글을 위해~
		request.setCharacterEncoding("UTF-8");
		String pageNoVal = request.getParameter("pageNo");
		int pageNo = 1;
		if(pageNoVal != null) {
			pageNo = Integer.parseInt(pageNoVal);
		}
		ArticlePage articlePage = listService.getArticlePage(pageNo);
		request.setAttribute("articlePage", articlePage);
		return "/WEB-INF/view/sayList.jsp";
	}
	
}
