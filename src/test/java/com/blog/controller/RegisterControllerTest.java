package com.blog.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.blog.services.AccountService;

@SpringBootTest
@AutoConfigureMockMvc
class RegisterControllerTest {
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private AccountService accountService;

	@Test
	void testGetAccountRegisterPage() throws Exception {
		mockMvc.perform(get("/account/register")).andExpect(view().name("register.html"));
	}

	@Test
	void testRegisterSuccess() throws Exception {
		when(accountService.createAccount("wchen1", "wchen1@unb.ca", "123456")).thenReturn(true);

		mockMvc.perform(post("/account/register/process").param("accountName", "wchen1")
				.param("accountEmail", "wchen1@unb.ca").param("password", "123456"))
				.andExpect(view().name("login.html"));

		verify(accountService, times(1)).createAccount("wchen1", "wchen1@unb.ca", "123456");
	}

	@Test
	void testRegisterFaile() throws Exception {
		when(accountService.createAccount("Test", "wchen1@unb.ca", "123456")).thenReturn(false);

		mockMvc.perform(post("/account/register/process").param("accountName", "Test")
				.param("accountEmail", "wchen1@unb.ca").param("password", "123456"))
				.andExpect(view().name("register.html"));

		verify(accountService, times(1)).createAccount("Test", "wchen1@unb.ca", "123456");
	}
}
