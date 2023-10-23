package com.myproject.library.Services;

import com.myproject.library.Models.User;

public interface IUserService {
    
	public User saveUser(User user);

	public void removeSessionMessage();

	public User findbyUserId(int id);
}
