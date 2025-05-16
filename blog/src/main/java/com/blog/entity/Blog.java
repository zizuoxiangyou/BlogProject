package com.blog.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Blog {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long postId;

	private String title;

	private String content;

	private String image;

	@Column(name = "created_at", insertable = false, updatable = false)
	private LocalDateTime createdAt;

	private Long accountId;

	// 空のコンストラクタ
	public Blog() {
	}

	public Blog(Long postId, String title, String content, String image, Long accountId) {
		super();
		this.postId = postId;
		this.title = title;
		this.content = content;
		this.image = image;
		this.accountId = accountId;
	}

//ゲッターとセッター
	public Long getPostId() {
		return postId;
	}

	public void setPostId(Long postId) {
		this.postId = postId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

}
