package auth.command;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import auth.service.LoginFailException;
import auth.service.LoginService;
import auth.service.User;
import mvc.command.CommandHandler;

public class LoginHandler implements CommandHandler{
	
	private static final String FORM_VIEW = "/WEB-INF/view/loginForm.jsp";
	private LoginService loginService = new LoginService();
	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if(request.getMethod().equalsIgnoreCase("GET")) {
			return processForm(request,response);
		}else if(request.getMethod().equalsIgnoreCase("POST")) {
			return processSubmit(request,response);
		}else {
			response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return null;
		}
	}
	
	private String processForm(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return FORM_VIEW;
	}
	
	private String processSubmit(HttpServletRequest request, HttpServletResponse response) {
		String id = trim(request.getParameter("id"));
		String password = trim(request.getParameter("password"));
		
		Map<String, Boolean> errors = new HashMap<>();
		request.setAttribute("errors", errors);
		
		if(id == null || id.isEmpty()) {
			errors.put("id", Boolean.TRUE);
			//System.out.println("id 에러 호출");
		}
		
		if(password == null || password.isEmpty()) {
			errors.put("password", Boolean.TRUE);
			//System.out.println("pass 에러 호출");
		}
		
		if(!errors.isEmpty()) {
			return FORM_VIEW;
		}
		
		try {
			//세션에 User객체를 담는다.
			User user = loginService.login(id, password);
			request.getSession().setAttribute("authUser", user);
			response.sendRedirect(request.getContextPath() + "/index.jsp");
			return null;
		}catch(LoginFailException | IOException e) {
			System.out.println("아이디와 비밀번호가 일치하지 않습니다." + e);
			errors.put("idOrPwNotMatch", Boolean.TRUE);
			return FORM_VIEW;
		}
	}

	private String trim(String str) {
		return str == null ? null : str.trim();
	}
	
	
}
