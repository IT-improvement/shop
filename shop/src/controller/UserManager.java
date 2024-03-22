package controller;

import java.util.ArrayList;

import dto.Cart;
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
	public boolean addUser(String name, String id, String password, int money) {
		User user = new User(name, id, password, money);
		userList.add(user);
		return true;
	}

	/* R(select) */
	// select User
	public ArrayList<User> getAll() {
		return (ArrayList<User>) userList.clone();
	}

	public User get(int index) {
		return userList.get(index);
	}

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

	public Cart getCart(int index) {
		return userList.get(index).getCart();
	}

	/* U(update) */
	public void updateCart(int index, Cart cart) {
		userList.get(index).setCart(cart);
	}

	public void updateUser(int index, User user) {
		userList.set(index, user);
	}

	/* D(delete) */
	public void remove(int index) {
		userList.remove(index);
	}
}
