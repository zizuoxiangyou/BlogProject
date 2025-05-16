package com.blog.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blog.entity.Account;


@Repository
public interface AccountDao extends JpaRepository<Account, Long> {
	//保存処理と更新処理　insertとupdate
	Account save(Account account);
	 // emailでアカウントを検索するメソッド
    Account findByAccountEmail(String accountEmail);

    // emailとpasswordでアカウントを検索するメソッド
    Account findByAccountEmailAndPassword(String accountEmail, String password);

}
