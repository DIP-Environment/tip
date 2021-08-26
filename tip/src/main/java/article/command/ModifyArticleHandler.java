package article.command;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import article.model.ArticleData;
import article.service.ArticleNotFoundException;
import article.service.ModifyArticleService;
import article.service.ModifyRequest;
import article.service.PermissionDeniedException;
import article.service.ReadArticleService;
import auth.service.User;
import mvc.command.CommandHandler;

public class ModifyArticleHandler implements CommandHandler{
	private static final String FORM_VIEW = "/WEB-INF/view/sayWriteModify.jsp";
	
	private ReadArticleService readService = new ReadArticleService();
	private ModifyArticleService modifyService = new ModifyArticleService();
	
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if(request.getMethod().equalsIgnoreCase("GET")) {
			return processForm(request, response);
		}else if(request.getMethod().equalsIgnoreCase("POST")) {
			return processSubmit(request, response);
		}else {
			response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
		}
		return null;
	}
	
	private String processForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ParseException {
		try {
			String noVal = request.getParameter("no");
			int no = Integer.parseInt(noVal);
			ArticleData articleData = readService.getArticle(no, false);
			User authUser = (User) request.getSession().getAttribute("authUser");
			if(!canModify(authUser, articleData)) {
				response.sendError(HttpServletResponse.SC_FORBIDDEN);
				return null;
			}
			ModifyRequest modReq = new ModifyRequest(authUser.getId(), no, articleData.getArticle().getTitle(), articleData.getContent());
			
			request.setAttribute("modReq", modReq);
			return FORM_VIEW;
		}catch (ArticleNotFoundException ex) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}
	}

	private boolean canModify(User authUser, ArticleData articleData) {
		String writerId = articleData.getArticle().getWriter().getId();
		return authUser.getId().equals(writerId);
	}

	private String processSubmit(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ParseException {
		// 한글을 위해~
		request.setCharacterEncoding("UTF-8");
		User authUser = (User) request.getSession().getAttribute("authUser");
		String noVal = request.getParameter("no");
		int no = Integer.parseInt(noVal);
		
		ModifyRequest modReq = new ModifyRequest(authUser.getId(), no, request.getParameter("title"), request.getParameter("content"));
		request.setAttribute("modReq", modReq);
		
		Map<String, Boolean> errors = new HashMap<>();
		request.setAttribute("errors", errors);
		modReq.validate(errors);
		if(!errors.isEmpty()) {
			return FORM_VIEW;
		}
		try {
			modifyService.modify(modReq);
			System.out.println("수정 성공!");
			return "articleRead.do"; //request.setAttribute("errors", errors) ������ �� �� ����.
		}catch (ArticleNotFoundException ex) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}catch (PermissionDeniedException ex) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN);
			return null;
		}
		
	}

	
}
