package org.minhvc.springwebmvc.parkingmanagement.controllers;

import jakarta.validation.Valid;
import org.minhvc.springwebmvc.parkingmanagement.entities.ParkingSessions;
import org.minhvc.springwebmvc.parkingmanagement.entities.ParkingSlot;
import org.minhvc.springwebmvc.parkingmanagement.entities.User;
import org.minhvc.springwebmvc.parkingmanagement.entities.Vehicle;
import org.minhvc.springwebmvc.parkingmanagement.entities.VehicleType;
import org.minhvc.springwebmvc.parkingmanagement.services.IParkingSessionsService;
import org.minhvc.springwebmvc.parkingmanagement.services.IParkingSlotService;
import org.minhvc.springwebmvc.parkingmanagement.services.IUserService;
import org.minhvc.springwebmvc.parkingmanagement.services.IVehicleService;
import org.minhvc.springwebmvc.parkingmanagement.services.IVehicleTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ParkingSessionsController {

	@Autowired
	private IParkingSessionsService parkingSessionsService;

	@Autowired
	private IParkingSlotService parkingSlotService;

	@Autowired
	private IVehicleService vehicleService;

	@Autowired
	private IVehicleTypeService vehicleTypeService;

	@Autowired
	private IUserService userService;

	public ParkingSessionsController() {
	}

	@RequestMapping(value = {"/", "/ParkingSessions/index"}, method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView index(@RequestParam(value = "keyword", required = false) String keyword) {
		ModelAndView modelAndView = new ModelAndView("ParkingSessions/index");
		List<ParkingSessions> items;
		if (keyword != null && !keyword.trim().isEmpty()) {
			items = parkingSessionsService.searchByKeyword(keyword.trim());
			modelAndView.addObject("keyword", keyword);
		} else {
			items = parkingSessionsService.findAll();
		}
		modelAndView.addObject("parkingSessions", items);
		return modelAndView;
	}

	@RequestMapping(value = "/ParkingSessions/detail/{id}", method = RequestMethod.GET)
	public ModelAndView detail(@PathVariable("id") Integer id) {
		ModelAndView modelAndView = new ModelAndView("ParkingSessions/detail");
		modelAndView.addObject("parkingSession", parkingSessionsService.findById(id).orElse(null));
		return modelAndView;
	}

	@RequestMapping(value = "/ParkingSessions/create", method = RequestMethod.GET)
	public ModelAndView createForm() {
		ParkingSessions parkingSession = new ParkingSessions();
		parkingSession.setStatus("PARKING");
		parkingSession.setEntryTime(LocalDateTime.now());
		Vehicle vehicle = new Vehicle();
		vehicle.setVehicleTypeID(new VehicleType());
		parkingSession.setVehicleID(vehicle);
		parkingSession.setSlotID(new ParkingSlot());
		parkingSession.setCustomerUser(new User());

		ModelAndView modelAndView = new ModelAndView("ParkingSessions/create");
		addFormDropdowns(modelAndView);
		modelAndView.addObject("parkingSession", parkingSession);
		return modelAndView;
	}

	@RequestMapping(value = "/ParkingSessions/update/{id}", method = RequestMethod.GET)
	public ModelAndView updateForm(@PathVariable("id") Integer id) {
		ParkingSessions parkingSession = parkingSessionsService.findById(id).orElse(null);
		if (parkingSession == null) {
			return new ModelAndView("redirect:/ParkingSessions/index");
		}

		ModelAndView modelAndView = new ModelAndView("ParkingSessions/update");
		addFormDropdowns(modelAndView);
		modelAndView.addObject("parkingSession", parkingSession);
		return modelAndView;
	}

	@RequestMapping(value = "/ParkingSessions/delete/{id}", method = RequestMethod.GET)
	public ModelAndView delete(@PathVariable("id") Integer id) {
		parkingSessionsService.delete(id);
		return new ModelAndView("redirect:/ParkingSessions/index");
	}

	@RequestMapping(value = "/ParkingSessions/saveCreate", method = RequestMethod.POST)
	public ModelAndView saveCreate(
			@Valid @ModelAttribute("parkingSession") ParkingSessions parkingSession,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			ModelAndView modelAndView = new ModelAndView("ParkingSessions/create");
			addFormDropdowns(modelAndView);
			return modelAndView;
		}

		resolveRelationships(parkingSession);
		if (parkingSession.getEntryTime() == null) {
			parkingSession.setEntryTime(LocalDateTime.now());
		}
		if (parkingSession.getStatus() == null || parkingSession.getStatus().isBlank()) {
			parkingSession.setStatus("PARKING");
		}
		parkingSessionsService.save(parkingSession);
		return new ModelAndView("redirect:/ParkingSessions/index");
	}

	@RequestMapping(value = "/ParkingSessions/saveUpdate", method = RequestMethod.POST)
	public ModelAndView saveUpdate(
			@Valid @ModelAttribute("parkingSession") ParkingSessions parkingSession,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			ModelAndView modelAndView = new ModelAndView("ParkingSessions/update");
			addFormDropdowns(modelAndView);
			return modelAndView;
		}

		resolveRelationships(parkingSession);
		parkingSessionsService.save(parkingSession);
		return new ModelAndView("redirect:/ParkingSessions/index");
	}

	private void addFormDropdowns(ModelAndView modelAndView) {
		modelAndView.addObject("parkingSlots", parkingSlotService.findAll());
		modelAndView.addObject("vehicleTypes", vehicleTypeService.findAll());
		modelAndView.addObject("users", userService.findAll().stream()
				.filter(this::isCustomerUser)
				.collect(Collectors.toList()));
	}

	private void resolveRelationships(ParkingSessions parkingSession) {
		if (parkingSession.getSlotID() != null && parkingSession.getSlotID().getId() != null) {
			ParkingSlot parkingSlot = parkingSlotService.findById(parkingSession.getSlotID().getId()).orElse(null);
			parkingSession.setSlotID(parkingSlot);
		}
		Vehicle submittedVehicle = parkingSession.getVehicleID();
		if (submittedVehicle != null && submittedVehicle.getLicensePlate() != null
				&& !submittedVehicle.getLicensePlate().isBlank()) {
			String licensePlate = submittedVehicle.getLicensePlate().trim().toUpperCase();
			submittedVehicle.setLicensePlate(licensePlate);
			Vehicle vehicle = vehicleService.findByLicensePlate(licensePlate).orElse(null);
			if (vehicle == null) {
				if (submittedVehicle.getVehicleTypeID() != null
						&& submittedVehicle.getVehicleTypeID().getTypeName() != null
						&& !submittedVehicle.getVehicleTypeID().getTypeName().isBlank()) {
					String typeName = submittedVehicle.getVehicleTypeID().getTypeName().trim().toUpperCase();
					VehicleType vehicleType = vehicleTypeService.findByTypeName(typeName).orElse(null);
					if (vehicleType == null) {
						vehicleType = new VehicleType();
						vehicleType.setTypeName(typeName);
						vehicleType.setDescription(typeName + " parking vehicle");
						vehicleTypeService.save(vehicleType);
					}
					submittedVehicle.setVehicleTypeID(vehicleType);
				}
				vehicleService.save(submittedVehicle);
				vehicle = submittedVehicle;
			}
			parkingSession.setVehicleID(vehicle);
		}
		if (parkingSession.getCustomerUser() != null && parkingSession.getCustomerUser().getId() != null) {
			User customer = userService.findById(parkingSession.getCustomerUser().getId()).orElse(null);
			parkingSession.setCustomerUser(customer);
		} else {
			parkingSession.setCustomerUser(null);
		}
	}

	private boolean isCustomerUser(User user) {
		return user != null
				&& user.getRoleID() != null
				&& user.getRoleID().getRoleName() != null
				&& "CUSTOMER".equalsIgnoreCase(user.getRoleID().getRoleName());
	}
}
