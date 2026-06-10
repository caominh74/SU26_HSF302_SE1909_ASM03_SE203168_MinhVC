package org.minhvc.springwebmvc.parkingmanagement.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ParkingSessionsController {
	public ParkingSessionsController() {}

	@RequestMapping(value = "/CreateParkingSessions/index", method = {RequestMethod.GET})
	public ModelAndView index() {
		return new ModelAndView("/CreateParkingSessions/index");
	}
}
