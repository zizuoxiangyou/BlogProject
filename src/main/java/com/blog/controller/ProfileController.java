package com.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.blog.entity.Account;
import jakarta.servlet.http.HttpSession;

@Controller
public class ProfileController {

	@Autowired
	private HttpSession session;

	@GetMapping("/profile")
	public String getProfilePage(Model model) {
		// セッションからログイン中のユーザー情報を取得
		Account account = (Account) session.getAttribute("loginAccount");

		// ログインしていない場合はログイン画面にリダイレクト
		if (account == null) {
			return "redirect:/account/login";
		}

		// モデルにユーザー名を追加
		model.addAttribute("accountName", account.getAccountName());

		// プロフィール画面を表示
		return "profile.html";
	}
}