package controller;

import java.util.ArrayList;

import dto.User;

public class UserManager {

	private ArrayList<User> userList;

	private UserManager() {
		userList = new ArrayList<>();
		User admin = new User("admin", "admin", "1234");
		userList.add(admin);
	}

	private static UserManager instance = new UserManager();

	public static UserManager getInstance() {
		return instance;
	}

	/* C(insert) */
	/* R(select) */
	// find Id index
	public int selectId(String id) {
		for (int i = 0; i < userList.size(); i++) {
			User user = userList.get(i);
			if (user.getId().equals(id))
				return i;
		}
		return -1;
	}

	// check Password
	public boolean checkPassword(int index, String pw) {
		User user = userList.get(index);
		return user.getPassword().equals(pw) ? true : false;
	}
	
	
	/* U(update) */
	/* D(delete) */
}
