package org.minhvc.springwebmvc.parkingmanagement.controllers;

import jakarta.servlet.http.HttpSession;
import org.minhvc.springwebmvc.parkingmanagement.entities.User;
import org.minhvc.springwebmvc.parkingmanagement.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AuthController {

	private static final String ADMIN_ROLE = "ADMIN";

	@Autowired
	private IUserService userService;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView loginPage(HttpSession session) {
		if (ADMIN_ROLE.equalsIgnoreCase((String) session.getAttribute("userRole"))) {
			return new ModelAndView("redirect:/ParkingSessions/index");
		}
		return new ModelAndView("login");
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView login(@RequestParam("email") String email,
			@RequestParam("password") String password,
			HttpSession session) {
		User user = userService.login(email, password);

		if (user == null) {
			return loginWithError("Invalid email or password", email);
		}
		if (user.getRoleID() == null || user.getRoleID().getRoleName() == null
				|| !ADMIN_ROLE.equalsIgnoreCase(user.getRoleID().getRoleName())) {
			return loginWithError("Admin access is required", email);
		}

		session.setAttribute("userId", user.getId());
		session.setAttribute("userName", user.getFullName());
		session.setAttribute("userRole", ADMIN_ROLE);
		return new ModelAndView("redirect:/ParkingSessions/index");
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ModelAndView logout(HttpSession session) {
		session.invalidate();
		return new ModelAndView("redirect:/login?logout");
	}

	private ModelAndView loginWithError(String message, String email) {
		ModelAndView modelAndView = new ModelAndView("login");
		modelAndView.addObject("error", message);
		modelAndView.addObject("email", email);
		return modelAndView;
	}
}
