package com.blog.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.blog.dao.AccountDao;
import com.blog.dao.BlogDao;
import com.blog.entity.Account;
@SpringBootTest
@AutoConfigureMockMvc
class BlogCreateControllerTest {

	 @Autowired
	    private MockMvc mockMvc;

	    @Autowired
	    private AccountDao accountDao;

	    @Autowired
	    private BlogDao blogDao;
	    private MockHttpSession session;
	    @Test
	    void testBlogFlow() throws Exception {
	        mockMvc.perform(get("/account/register"))
	                .andExpect(view().name("register.html"));
	        mockMvc.perform(post("/account/register/process")
	                .param("accountName", "wchen6")
	                .param("accountEmail", "wchen6@unb.ca")
	                .param("password", "123456")
	                .andExpect(view().name("login.html"));
	        
	        Account wchenAccount = accountDao.findByAccountEmail("wchen6@unb.ca");
	        assertNotNull(wchenAccount);
	        assertEquals("wchen6", wchenAccount.getAccountName());
	        assertEquals("wchen6@unb.ca", wchenAccount.getAccountEmail());
	        assertEquals("123456", wchenAccount.getPassword());
	        
	        
	        mockMvc.perform(get("/account/login"))
            .andExpect(status().isOk())
            .andExpect(view().name("login.html"));
	        mockMvc.perform(post("/account/login/process")
	                .param("accountEmail", "wchen6@unb.ca")
	                .param("password", "123456")
	                .session(session))
	            
	                .andExpect(redirectedUrl("/blog/list"));
	    }
	    
	    
}
