package com.blog.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.dao.BlogDao;
import com.blog.entity.Account;
import com.blog.entity.Blog;


@Service

public class BlogService {
	@Autowired
    private BlogDao blogDao;

    // ブログ一覧のチェック
    // もしaccountId==null 戻り値としてnull
    // findAll内容をコントローラークラスに渡す
    public List<Blog> selectAllBlogList(Long accountId) {
        if (accountId == null) {
            return null;
        } else {
            return blogDao.findAll();
        }
    }

    // ブログの登録処理チェック
    // もし、findByTitleが==nullだったら、
    // 保存処理
    // true
    // そうでない場合
    // false
    public boolean createBlog(String title,
                               String content,
                               String image,
                               Long account) {
        if (blogDao.findByTitle(title) == null) {
            blogDao.save(new Blog(title, content, image, account));
            return true;
        } else {
            return false;
        }
    }

    // 編集画面を表示するときのチェック
    // もし、blogId == null  null
    // そうでない場合、
    // findByIdの情報をコントローラークラスに渡す
    public Blog blogEditCheck(Long blogId) {
        if (blogId == null) {
            return null;
        } else {
            Optional<Blog> blogOptional = blogDao.findById(blogId);
            return blogOptional.orElse(null);
        }
    }

    // 更新処理のチェック
    // もし、blogId==nullだったら、更新処理はしない
    // false
    // そうでない場合、
    // 更新処理をする
    // コントローラークラスからもらった、blogIdを使って、編集する前のデータを取得
    // 変更するべきところだけ、セッターを使用してデータの更新をする。
    // trueを返す
    public boolean blogUpdate(Long blogId,
                               String title,
                               String content,
                               String image,
                               Long accountId) {
        if (blogId == null) {
            return false;
        } else {
            Optional<Blog> blogOptional = blogDao.findById(blogId);
            if (blogOptional.isPresent()) {
                Blog blog = blogOptional.get();
                blog.setTitle(title);
                blog.setContent(content);
                blog.setImage(image);
                blog.setAccountId(accountId);
                blogDao.save(blog);
                return true;
            }
            return false;
        }
    }

    // 削除処理のチェック
    // もし、コントローラークラスから受け取ったblogIdがnull
    // false
    // そうでない場合、deleteByIdを使って削除処理
    // true
    public boolean deleteBlog(Long blogId) {
        if (blogId == null) {
            return false;
        } else {
            try {
                blogDao.deleteById(blogId);
                return true;
            } catch (Exception e) {
                return false;
            }
        }
    }
}
