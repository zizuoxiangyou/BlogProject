package com.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.blog.services.AccountService;

@Controller
public class RegisterController {
	@Autowired
	private AccountService accountService;

	// 登録画面の表示（GETリクエスト）
	@GetMapping("/account/register")
	public String getAccountRegisterPage() {
		return "register.html";
	}

	// 登録処理
	@PostMapping("/account/register/process")
	public String accountRegisterProcess(@RequestParam String accountName, @RequestParam String accountEmail,
			@RequestParam String password) {

		// アカウントが新規作成できたらログイン画面へ
		if (accountService.createAccount(accountName, accountEmail, password)) {
			return "login.html";
		} else {
			return "register.html";
		}
	}
}
