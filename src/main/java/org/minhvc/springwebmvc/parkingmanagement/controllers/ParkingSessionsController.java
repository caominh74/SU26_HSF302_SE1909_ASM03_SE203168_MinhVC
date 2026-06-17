package org.minhvc.springwebmvc.parkingmanagement.controllers;

import org.minhvc.springwebmvc.parkingmanagement.entities.*;
import org.minhvc.springwebmvc.parkingmanagement.services.*;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import jakarta.validation.Valid;

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
		ModelAndView mav = new ModelAndView("CreateParkingSessions/index");
		List<ParkingSessions> sessions = parkingSessionsService.findAll();
		mav.addObject("sessions", sessions);
		return mav;
	}

	@GetMapping("/create")
	public ModelAndView create() {
		ModelAndView mav = new ModelAndView("CreateParkingSessions/create");
		ParkingSessions parkingSession = new ParkingSessions();
		parkingSession.setStatus("PARKING");  // Matches DB CHECK constraint
		parkingSession.setVehicleID(new Vehicle());
		mav.addObject("parkingSession", parkingSession);
		mav.addObject("vehicles", vehicleService.findAll());
		mav.addObject("slots", parkingSlotService.findAll());
		mav.addObject("users", userService.findAll());
		return mav;
	}

	@PostMapping("/saveCreate")
	public ModelAndView saveCreate(@Valid @ModelAttribute("parkingSession") ParkingSessions parkingSession, BindingResult bindingResult) {
		ModelAndView mav = new ModelAndView();
		if (bindingResult.hasErrors()) {
			mav.addObject("vehicles", vehicleService.findAll());
			mav.addObject("slots", parkingSlotService.findAll());
			mav.addObject("users", userService.findAll());
			mav.setViewName("CreateParkingSessions/create");
			return mav;
		}

		if (parkingSession.getEntryTime() == null) {
			parkingSession.setEntryTime(Instant.now());
		}
		if (parkingSession.getStatus() == null || parkingSession.getStatus().trim().isEmpty()) {
			parkingSession.setStatus("PARKING");  // Matches DB CHECK constraint
		}

		// Resolve Vehicle from ID (TuNLA pattern)
		if (parkingSession.getVehicleID() != null && parkingSession.getVehicleID().getId() != null) {
			Vehicle vehicle = vehicleService.findById(parkingSession.getVehicleID().getId());
			parkingSession.setVehicleID(vehicle);
		}

		// Resolve ParkingSlot from ID (TuNLA pattern)
		if (parkingSession.getSlotID() != null && parkingSession.getSlotID().getId() != null) {
			ParkingSlot slot = parkingSlotService.findById(parkingSession.getSlotID().getId());
			parkingSession.setSlotID(slot);
		}

		// Resolve User from ID (TuNLA pattern)
		if (parkingSession.getCreatedBy() != null && parkingSession.getCreatedBy().getId() != null) {
			User user = userService.findById(parkingSession.getCreatedBy().getId());
			parkingSession.setCreatedBy(user);
		}

		parkingSessionsService.save(parkingSession);
		mav.setViewName("redirect:/CreateParkingSessions/index");
		return mav;
	}

	@GetMapping("/edit/{id}")
	public ModelAndView edit(@PathVariable("id") Integer id) {
		ModelAndView mav = new ModelAndView("CreateParkingSessions/edit");
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

	@PostMapping("/saveUpdate")
	public ModelAndView saveUpdate(@Valid @ModelAttribute("parkingSession") ParkingSessions parkingSession, BindingResult bindingResult) {
		ModelAndView mav = new ModelAndView();
		if (bindingResult.hasErrors()) {
			mav.addObject("vehicles", vehicleService.findAll());
			mav.addObject("slots", parkingSlotService.findAll());
			mav.addObject("users", userService.findAll());
			mav.setViewName("CreateParkingSessions/edit");
			return mav;
		}

		if (parkingSession.getEntryTime() == null) {
			parkingSession.setEntryTime(Instant.now());
		}

		// Resolve Vehicle from ID (TuNLA pattern)
		if (parkingSession.getVehicleID() != null && parkingSession.getVehicleID().getId() != null) {
			Vehicle vehicle = vehicleService.findById(parkingSession.getVehicleID().getId());
			parkingSession.setVehicleID(vehicle);
		}

		// Resolve ParkingSlot from ID (TuNLA pattern)
		if (parkingSession.getSlotID() != null && parkingSession.getSlotID().getId() != null) {
			ParkingSlot slot = parkingSlotService.findById(parkingSession.getSlotID().getId());
			parkingSession.setSlotID(slot);
		}

		// Resolve User from ID (TuNLA pattern)
		if (parkingSession.getCreatedBy() != null && parkingSession.getCreatedBy().getId() != null) {
			User user = userService.findById(parkingSession.getCreatedBy().getId());
			parkingSession.setCreatedBy(user);
		}

		parkingSessionsService.save(parkingSession);
		mav.setViewName("redirect:/CreateParkingSessions/index");
		return mav;
	}

	@GetMapping("/view/{id}")
	public ModelAndView view(@PathVariable("id") Integer id) {
		ModelAndView mav = new ModelAndView("CreateParkingSessions/view");
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