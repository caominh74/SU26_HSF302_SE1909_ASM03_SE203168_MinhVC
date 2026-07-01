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

	@Autowired
	private IUserService userService;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView loginPage(HttpSession session) {
		if (session.getAttribute("loggedInUser") != null) {
			return new ModelAndView("redirect:/");
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

		session.setAttribute("loggedInUser", user);
		return new ModelAndView("redirect:/");
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
