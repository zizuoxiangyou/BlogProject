package com.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.blog.entity.Account;
import com.blog.entity.Blog;
import com.blog.services.BlogService;

import jakarta.servlet.http.HttpSession;

@Controller
public class BlogListController {
	 @Autowired
	    private HttpSession session;

	    @Autowired
	    private BlogService blogService;

	    // ブログホーム画面を表示する
	    @GetMapping("/blog/list")
	    public String getBlogHome(Model model) {
	        // セッションからログイン中のユーザー情報を取得
	        Account account = (Account) session.getAttribute("loginAccount");

	        // ログインしていない場合はログイン画面にリダイレクト
	        if (account == null) {
	            return "redirect:/account/login";
	        } else {
	            // ログインユーザーのブログ記事一覧を取得
	            List<Blog> blogList = blogService.selectAllBlogList(account.getAccountId());

	            // モデルにユーザー名とブログリストを追加
	            model.addAttribute("accountName", account.getAccountName());
	            model.addAttribute("blogList", blogList);

	            // ホーム画面を表示
	            return "blog-list.html";
	        }
	    }
}
