package com.tyss.gmail.dao;

import java.util.List;

import com.tyss.gmail.dto.AccountDto;
import com.tyss.gmail.dto.Inbox;

public interface AccountDao {
	public boolean registerUser(AccountDto accountDto);
	public boolean loginUser(String email,String password);
	public boolean composeMail(Inbox inbox,String email);
	public List<Inbox> showAllInbox();
}
