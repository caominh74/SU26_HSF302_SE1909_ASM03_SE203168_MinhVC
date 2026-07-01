package org.minhvc.springwebmvc.parkingmanagement.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.minhvc.springwebmvc.parkingmanagement.entities.Role;
import org.minhvc.springwebmvc.parkingmanagement.entities.User;
import org.minhvc.springwebmvc.parkingmanagement.services.IUserService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.web.servlet.ModelAndView;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthControllerTests {

	@Mock
	private IUserService userService;

	@InjectMocks
	private AuthController authController;

	@Test
	void rejectsValidNonAdminAccount() {
		User customer = userWithRole(2, "CUSTOMER");
		when(userService.login("customer@example.com", "password")).thenReturn(customer);
		MockHttpSession session = new MockHttpSession();

		ModelAndView result = authController.login("customer@example.com", "password", session);

		assertEquals("login", result.getViewName());
		assertEquals("Admin access is required", result.getModel().get("error"));
		assertNull(session.getAttribute("userId"));
		assertNull(session.getAttribute("userRole"));
	}

	@Test
	void createsAdminSessionAndRedirectsToParkingSessions() {
		User admin = userWithRole(1, "ADMIN");
		admin.setFullName("System Administrator");
		when(userService.login("admin@example.com", "password")).thenReturn(admin);
		MockHttpSession session = new MockHttpSession();

		ModelAndView result = authController.login("admin@example.com", "password", session);

		assertEquals("redirect:/ParkingSessions/index", result.getViewName());
		assertEquals(1, session.getAttribute("userId"));
		assertEquals("ADMIN", session.getAttribute("userRole"));
	}

	private User userWithRole(Integer id, String roleName) {
		Role role = new Role();
		role.setRoleName(roleName);
		User user = new User();
		user.setId(id);
		user.setRoleID(role);
		return user;
	}
}
