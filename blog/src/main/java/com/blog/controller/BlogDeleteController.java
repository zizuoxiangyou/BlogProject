package com.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import com.blog.entity.Account;
import com.blog.services.BlogService;

import jakarta.servlet.http.HttpSession;

@Controller
public class BlogDeleteController {
	@Autowired
    private HttpSession session;

    @Autowired
    private BlogService blogService;

    // BLOGの削除処理
    @PostMapping("/blog/delete")
    public String deleteBlog(Long blogId) {
        // セッションからログインユーザーの情報を取得
        Account account = (Account) session.getAttribute("loginAccount");

        if (account == null) {
            // ログインしていない場合はログインページへリダイレクト
            return "redirect:/account/login";
        } else {
            // 削除処理の実行結果によって遷移先を変更
            if (blogService.deleteBlog(blogId)) {
                return "redirect:/blog/list";
            } else {
                return "redirect:/blog/edit/" + blogId;
            }
        }
    }
}
