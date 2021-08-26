package auth.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mvc.command.CommandHandler;

public class LogoutHandler implements CommandHandler{

	@Override
	public String process(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession(false);
		if(session != null) {
			System.out.println("로그아웃이 실행되었습니다.");
			session.invalidate();
		}
		response.sendRedirect(request.getContextPath() + "/index.jsp");
		return null;
	}

}
