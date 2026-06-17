package org.minhvc.springwebmvc.parkingmanagement.controllers;

import org.minhvc.springwebmvc.parkingmanagement.entities.*;
import org.minhvc.springwebmvc.parkingmanagement.services.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.Instant;
import java.util.List;

@Controller
@RequestMapping("/CreateParkingSessions")
public class ParkingSessionsController {
	private final IParkingSessionsService parkingSessionsService;
	private final IVehicleService vehicleService;
	private final IParkingSlotService parkingSlotService;
	private final IUserService userService;

	public ParkingSessionsController(IParkingSessionsService parkingSessionsService,
									 IVehicleService vehicleService,
									 IParkingSlotService parkingSlotService,
									 IUserService userService) {
		this.parkingSessionsService = parkingSessionsService;
		this.vehicleService = vehicleService;
		this.parkingSlotService = parkingSlotService;
		this.userService = userService;
	}

	@GetMapping("/index")
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView("/CreateParkingSessions/index");
		List<ParkingSessions> sessions = parkingSessionsService.findAll();
		mav.addObject("sessions", sessions);
		return mav;
	}

	@GetMapping("/create")
	public ModelAndView create() {
		ModelAndView mav = new ModelAndView("/CreateParkingSessions/create");
		mav.addObject("parkingSession", new ParkingSessions());
		mav.addObject("vehicles", vehicleService.findAll());
		mav.addObject("slots", parkingSlotService.findAll());
		mav.addObject("users", userService.findAll());
		return mav;
	}

	@PostMapping("/save")
	public ModelAndView save(@ModelAttribute("parkingSession") ParkingSessions parkingSession) {
		if (parkingSession.getEntryTime() == null) {
			parkingSession.setEntryTime(Instant.now());
		}
		if (parkingSession.getStatus() == null || parkingSession.getStatus().isEmpty()) {
			parkingSession.setStatus("Active");
		}
		parkingSessionsService.save(parkingSession);
		return new ModelAndView("redirect:/CreateParkingSessions/index");
	}

	@GetMapping("/edit/{id}")
	public ModelAndView edit(@PathVariable("id") Integer id) {
		ModelAndView mav = new ModelAndView("/CreateParkingSessions/edit");
		ParkingSessions session = parkingSessionsService.findById(id);
		if (session == null) {
			return new ModelAndView("redirect:/CreateParkingSessions/index");
		}
		mav.addObject("parkingSession", session);
		mav.addObject("vehicles", vehicleService.findAll());
		mav.addObject("slots", parkingSlotService.findAll());
		mav.addObject("users", userService.findAll());
		return mav;
	}

	@GetMapping("/view/{id}")
	public ModelAndView view(@PathVariable("id") Integer id) {
		ModelAndView mav = new ModelAndView("/CreateParkingSessions/view");
		ParkingSessions session = parkingSessionsService.findById(id);
		if (session == null) {
			return new ModelAndView("redirect:/CreateParkingSessions/index");
		}
		mav.addObject("parkingSession", session);
		return mav;
	}

	@GetMapping("/delete/{id}")
	public ModelAndView delete(@PathVariable("id") Integer id) {
		parkingSessionsService.delete(id);
		return new ModelAndView("redirect:/CreateParkingSessions/index");
	}
}