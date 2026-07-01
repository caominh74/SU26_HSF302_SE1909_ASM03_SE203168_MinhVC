package org.minhvc.springwebmvc.parkingmanagement;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AdminAuthInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("userId") == null) {
			response.sendRedirect(request.getContextPath() + "/login");
			return false;
		}

		String role = (String) session.getAttribute("userRole");
		if (!"ADMIN".equalsIgnoreCase(role)) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN);
			return false;
		}
		return true;
	}
}
