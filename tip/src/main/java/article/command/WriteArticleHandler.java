package article.command;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import article.model.Writer;
import article.service.WriteArticleService;
import article.service.WriteRequest;
import auth.service.User;
import mvc.command.CommandHandler;

public class WriteArticleHandler implements CommandHandler{
	private static final String FORM_VIEW = "/WEB-INF/view/sayWrite.jsp";
	private WriteArticleService writeService = new WriteArticleService();
	
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

	private String processForm(HttpServletRequest request, HttpServletResponse response) {
		return FORM_VIEW;
	}
	
	private String processSubmit(HttpServletRequest request, HttpServletResponse response) throws SQLException, UnsupportedEncodingException, ParseException {
		// 한글을 위해~
		request.setCharacterEncoding("UTF-8");
		Map<String, Boolean> errors = new HashMap<>();
		request.setAttribute("errors", errors);
		
		User user = (User)request.getSession(false).getAttribute("authUser");
		WriteRequest writeReq = createWriterRequest(user, request);
		writeReq.validate(errors);
		
		if(!errors.isEmpty()) {
			return FORM_VIEW;
		}
	
		int newArticleNo = writeService.write(writeReq); //���ο� �۹�ȣ�� ����.
		request.setAttribute("newArticleNo", newArticleNo);
		
		//return "/WEB-INF/view/sayList.jsp";
		return "articleList.do";
	}
	
	private WriteRequest createWriterRequest(User user, HttpServletRequest request) {
		return new WriteRequest(new Writer(user.getId(), user.getName()),request.getParameter("title"), request.getParameter("content"));
		
	}
}
