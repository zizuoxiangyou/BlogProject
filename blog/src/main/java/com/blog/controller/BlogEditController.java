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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.blog.entity.Account;
import com.blog.entity.Blog;
import com.blog.services.BlogService;

import jakarta.servlet.http.HttpSession;
@Controller
public class BlogEditController {
	@Autowired
    private BlogService blogService;

    @Autowired
    private HttpSession session;

    // 編集画面の表示
    @GetMapping("/blog/edit/{blogId}")
    public String getBlogEditPage(@PathVariable Long blogId, Model model) {
        Account account = (Account) session.getAttribute("loginAccount");

        if (account == null) {
            return "redirect:/account/login";
        } else {
            Blog blog = blogService.blogEditCheck(blogId);

            if (blog == null) {
                return "redirect:/blog/home";
            } else {
                model.addAttribute("accountName", account.getAccountName());
                model.addAttribute("blog", blog);
                return "blog-edit.html";
            }
        }
    }

    // 更新処理
    @PostMapping("/blog/edit/process")
    public String blogUpdate(@RequestParam String blogTitle,
                             @RequestParam String blogContent,
                             @RequestParam MultipartFile blogImage,
                             @RequestParam Long blogId) {

        Account account = (Account) session.getAttribute("loginAccount");

        if (account == null) {
            return "redirect:/account/login";
        } else {
            // ファイル名を生成
            String fileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-").format(new Date())
                    + blogImage.getOriginalFilename();

            try {
                // static/blog-img フォルダに画像保存
                Files.copy(blogImage.getInputStream(), Path.of("src/main/resources/static/blog-image/" + fileName));
            } catch (IOException e) {
                e.printStackTrace();
            }

            // 更新処理実行
            boolean result = blogService.blogUpdate(blogId, blogTitle, blogContent, fileName, account.getAccountId());

            if (result) {
                return "redirect:/blog/list";
            } else {
                return "redirect:/blog/edit/" + blogId;
            }
        }
    }
}
