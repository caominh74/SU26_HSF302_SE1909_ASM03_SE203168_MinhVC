package org.minhvc.springwebmvc.parkingmanagement;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AdminAuthInterceptorTests {

	private final AdminAuthInterceptor interceptor = new AdminAuthInterceptor();

	@Test
	void redirectsAnonymousUserToLogin() throws Exception {
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();

		assertFalse(interceptor.preHandle(request, response, new Object()));
		assertEquals("/login", response.getRedirectedUrl());
	}

	@Test
	void rejectsLoggedInNonAdminUser() throws Exception {
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpSession session = new MockHttpSession();
		session.setAttribute("userId", 2);
		session.setAttribute("userRole", "CUSTOMER");
		request.setSession(session);
		MockHttpServletResponse response = new MockHttpServletResponse();

		assertFalse(interceptor.preHandle(request, response, new Object()));
		assertEquals(403, response.getStatus());
	}

	@Test
	void permitsLoggedInAdminUser() throws Exception {
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpSession session = new MockHttpSession();
		session.setAttribute("userId", 1);
		session.setAttribute("userRole", "ADMIN");
		request.setSession(session);

		assertTrue(interceptor.preHandle(request, new MockHttpServletResponse(), new Object()));
	}
}
