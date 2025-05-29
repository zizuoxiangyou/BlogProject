package com.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.blog.entity.Account;
import com.blog.entity.Blog;
import com.blog.services.BlogService;

import jakarta.servlet.http.HttpSession;

@Controller
public class BlogDeleteController {
	@Autowired
	private HttpSession session;

	@Autowired
	private BlogService blogService;

	// 削除画面処理
	@GetMapping("/blog/delete")
	public String getDeletePage(Model model) {
		Account account = (Account) session.getAttribute("loginAccount");

		if (account == null) {
			return "redirect:/account/login";
		}

		model.addAttribute("blogList", blogService.selectAllBlogList(account.getAccountId()));
		model.addAttribute("account", account);
		return "blog-delete.html";
	}

	// セッションからログインしている人の情報をaccountという変数に格納
	@PostMapping("/blog/delete/process")
	public String deleteBlog(@RequestParam Long blogId) {
		Account account = (Account) session.getAttribute("loginAccount");
		// もしaccount==null ログイン画面にリダイレクトする
		if (account == null) {
			return "redirect:/account/login";
		}

		// ブログの存在確認とブログの作者確認
		Blog blog = blogService.blogEditCheck(blogId);
		if (blog == null || !blog.getAccountId().equals(account.getAccountId())) {
			return "redirect:/blog/list";
		}

		if (blogService.deleteBlog(blogId)) {
			return "redirect:/blog/list";
		} else {
			return "redirect:/blog/delete";
		}
	}
}
