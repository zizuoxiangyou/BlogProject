package com.blog.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blog.entity.Blog;


import jakarta.transaction.Transactional;
@Repository
@Transactional
public interface BlogDao extends JpaRepository<Blog, Long> {
	 // 保存処理と更新処理 insertとupdate
    Blog save(Blog blog);

    // SELECT * FROM blog
    // 用途：ブログ一覧を表示させるときに使用
    List<Blog> findAll();

    // SELECT * FROM blog WHERE title = ?
    // 用途：ブログの登録チェックに使用
    Blog findByTitle(String title);

    // SELECT * FROM blog WHERE blog_id = ?
    // 用途：編集画面を表示する際に使用
    Blog findByBlogId(Long blogId);

    // DELETE FROM blog WHERE blog_id = ?
    // 用途：削除に使用します。
    void deleteByBlogId(Long blogId);
}
