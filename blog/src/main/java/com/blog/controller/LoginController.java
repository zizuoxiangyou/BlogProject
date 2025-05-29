package com.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.blog.entity.Account;
import com.blog.services.AccountService;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {
	// AccountServiceを自動で注入する
	@Autowired
	private AccountService accountService;

	// Sessionの使用宣言
	@Autowired
	private HttpSession session;

	// ログイン画面の表示
	@GetMapping("/account/login")
	public String getAccountLoginPage() {
		return "login.html";
	}

	// ログイン処理
	@PostMapping("/account/login/process")
	public String accountLoginProcess(@RequestParam String accountEmail, @RequestParam String password) {
		// loginCheckメソッドを呼び出してその結果をaccountという変数に格納
		Account account = accountService.login(accountEmail, password).orElse(null);
		// ログイン失敗 → 同じ画面にとどまる
		if (account == null) {
			return "login.html";
		} else {
			session.setAttribute("loginAccount", account);
			return "redirect:/blog/list";
		}

	}
}
