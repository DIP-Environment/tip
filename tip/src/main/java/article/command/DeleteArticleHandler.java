package article.command;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import article.model.ArticleData;
import article.service.ArticleNotFoundException;
import article.service.DeleteArticleService;
import article.service.DeleteRequest;
import article.service.ModifyRequest;
import article.service.PermissionDeniedException;
import article.service.ReadArticleService;
import auth.service.User;
import mvc.command.CommandHandler;

public class DeleteArticleHandler implements CommandHandler {
	private static final String FORM_VIEW = "articleList.do";

	private DeleteArticleService deleteService = new DeleteArticleService();
	
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		User authUser = (User) request.getSession().getAttribute("authUser");
		String noVal = request.getParameter("no");
		int no = Integer.parseInt(noVal);

		DeleteRequest delReq = new DeleteRequest(authUser.getId(), no);
		request.setAttribute("delReq", delReq); //����ؾ���.

		try {
			deleteService.delete(delReq);
			return FORM_VIEW;
		} catch (ArticleNotFoundException e) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return null;
		} catch (PermissionDeniedException e) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN);
			return null;
		}
	}
}
