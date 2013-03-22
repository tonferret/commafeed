package com.commafeed.backend.dao;

import java.util.List;

import javax.inject.Inject;

import com.commafeed.backend.security.PasswordEncryptionService;
import com.commafeed.frontend.utils.ModelFactory.MF;
import com.commafeed.model.User;
import com.google.common.collect.Iterables;

public class UserService extends GenericDAO<User, Long> {

	@Inject
	PasswordEncryptionService encryptionService;

	public User login(String name, String password) {
		List<User> users = findByField(MF.i(MF.p(User.class).getName()), name);
		User user = Iterables.getFirst(users, null);
		if (user != null) {
			boolean authenticated = encryptionService.authenticate(password,
					user.getPassword(), user.getSalt());
			if (authenticated) {
				return user;
			}
		}

		return null;
	}
}