package com.blog.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.dao.AccountDao;
import com.blog.entity.Account;

@Service
public class AccountService {
	@Autowired
	private AccountDao accountDao;

	// アカウント登録処理
	// emailが重複していなければ新規登録を行う
	// 保存成功ならtrue、失敗ならfalseを返す
	public boolean createAccount(String accountName, String accountEmail, String password) {
		if (accountDao.findByAccountEmail(accountEmail) == null) {
			Account account = new Account();
			account.setAccountName(accountName);
			account.setAccountEmail(accountEmail);
			account.setPassword(password);
			accountDao.save(account);
			return true;
		} else {
			return false;
		}
	}

	// ログイン処理
	// emailとpasswordの組み合わせが一致するアカウントを探す
	// 見つからなければnullを返す
	public Optional<Account> login(String accountEmail, String password) {
		Account account = accountDao.findByAccountEmailAndPassword(accountEmail, password);
		return Optional.ofNullable(account);
	}
}
