package com.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class LogoutController {
	@Autowired
	private HttpSession session;

	// ログアウト処理
	@GetMapping("/account/logout")
	public String accountLogout() {
		// セッションの無効化
		session.invalidate();
		return "redirect:/account/login";
	}
}
