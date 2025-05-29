package com.blog.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.blog.entity.Account;
import com.blog.services.AccountService;

@SpringBootTest
@AutoConfigureMockMvc
class LoginControllerTest {
	  @Autowired
	    private MockMvc mockMvc;

	   @MockBean
	    private AccountService accountService;

	    private final String correctEmail = "wchen1@unb.ca";
	    private final String correctPassword = "123456";

	    @Test
	    void loginSuccessTest() throws Exception {
	        Account mockAccount = new Account();
	        mockAccount.setAccountEmail(correctEmail);

	        when(accountService.login(correctEmail, correctPassword))
	                .thenReturn(Optional.of(mockAccount));

	        mockMvc.perform(post("/account/login/process")
	                .param("accountEmail", correctEmail)
	                .param("password", correctPassword))
	                .andExpect(redirectedUrl("/blog/list"));
	    }

	    @Test
	    void wrongEmailTest() throws Exception {
	        when(accountService.login("test@test.com", correctPassword))
	                .thenReturn(Optional.empty());

	        mockMvc.perform(post("/account/login/process")
	                .param("accountEmail", "test@test.com")
	                .param("password", correctPassword))
	                .andExpect(view().name("login.html"));
	    }

	    @Test
	    void wrongPasswordTest() throws Exception {
	        when(accountService.login(correctEmail, "12345"))
	                .thenReturn(Optional.empty());

	        mockMvc.perform(post("/account/login/process")
	                .param("accountEmail", correctEmail)
	                .param("password", "12345"))
	                .andExpect(view().name("login.html"));
	    }

	    @Test
	    void wrongEmailAndPasswordTest() throws Exception {
	        when(accountService.login("test@test.com", "12345"))
	                .thenReturn(Optional.empty());

	        mockMvc.perform(post("/account/login/process")
	                .param("accountEmail", "test@test.com")
	                .param("password", "12345"))
	                .andExpect(view().name("login.html"));
	    }
	  
	}
