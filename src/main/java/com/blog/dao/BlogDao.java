package com.blog.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blog.entity.Blog;

import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface BlogDao extends JpaRepository<Blog, Long> {
	// SELECT * FROM blog WHERE title = ?
	// 用途：ブログの登録チェックに使用
	Blog findByTitle(String title);
}
