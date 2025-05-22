package com.blog.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.blog.entity.Account;
import com.blog.services.BlogService;

import jakarta.servlet.http.HttpSession;

@Controller
public class BlogCreateController {
	@Autowired
	private BlogService blogService;

	@Autowired
	private HttpSession session;

	// ブログ登録画面の表示
	@GetMapping("/blog/register")
	public String getBlogRegisterPage(Model model) {
		Account account = (Account) session.getAttribute("loginAccount");

		if (account == null) {
			return "redirect:/account/login";
		} else {
			model.addAttribute("accountName", account.getAccountName());
			return "blog-register.html";
		}
	}

	// ブログの登録処理
	@PostMapping("/blog/register/process")
	public String blogRegisterProcess(@RequestParam String title, @RequestParam String content,
			@RequestParam MultipartFile image) {

		Account account = (Account) session.getAttribute("loginAccount");

		if (account == null) {
			return "redirect:/account/login";
		} else {
			// 画像のファイル名を作成
			String fileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-").format(new Date())
					+ image.getOriginalFilename();

			// 画像ファイルの保存
			try {
				Files.copy(image.getInputStream(), Path.of("src/main/resources/static/blog-image/" + fileName));
			} catch (IOException e) {
				e.printStackTrace();
			}

			// ブログの保存処理
			if (blogService.createBlog(title, content, fileName, account.getAccountId())) {
				return "redirect:/blog/list";
			} else {
				return "blog_register.html";
			}
		}
	}
}
