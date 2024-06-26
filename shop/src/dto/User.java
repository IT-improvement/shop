package dto;


public class User {
	private String name;
	private String id;
	private String password;
	private int money;
	private Cart cart;

	public User(String name, String id, String password, int money) {
		this.name = name;
		this.id = id;
		this.password = password;
		this.money = money;
		cart = new Cart();
	}

	public User(String name, String id, String password) {
		this.name = name;
		this.id = id;
		this.password = password;
		cart = new Cart();
	}
	
	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

}
