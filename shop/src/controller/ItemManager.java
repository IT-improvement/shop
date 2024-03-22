package controller;

import java.util.ArrayList;

import dto.Cart;
import dto.Item;

public class ItemManager {
	private ArrayList<Item> itemList;

	private ItemManager() {
		itemList = new ArrayList<>();
	}

	private static ItemManager instance = new ItemManager();

	public static ItemManager getInstance() {
		return instance;
	}

	/* C(insert) */
	public int creatCode() {
		while (true) {
			boolean isCheck = true;
			int rNum = (int) (Math.random() * 9000) + 1000;
			for (Item e : itemList)
				if (e.getCode() == rNum)
					isCheck = false;
			if (isCheck)
				return rNum;
		}
	}

	public boolean add(Item item) {
		itemList.add(item);
		return true;
	}

	public Cart addCart(Cart cart, int index, int count) {
		Item item = itemList.get(index - 1);
		ArrayList<Item> carts = cart.clone();
		ArrayList<Integer> counts = cart.getCount();
		boolean isCheck = false;
		int idx = -1;
		for (int i = 0; i < carts.size(); i++)
			if (carts.get(i).getCode() == item.getCode()) {
				isCheck = true;
				idx = i;
			}
		try {
			if (isCheck) {
				int n = counts.get(idx);
				n += count;
				counts.set(idx, n);
			} else {
				carts.add(item);
				counts.add(count);
			}
		} catch (Exception e) {
			System.err.println("장바구니 추가 오류");
		}
		cart.setCarts(carts);
		cart.setCount(counts);
		return cart;
	}

	/* R(select) */
	public ArrayList<Item> getAll() {
		return (ArrayList<Item>) itemList.clone();
	}

	public int indexOf(int code) {
		for (int i = 0; i < itemList.size(); i++) {
			Item item = itemList.get(i);
			if (item.getCode() == code)
				return i;
		}
		return -1;
	}

	public void selectAll() {
		int i = 1;
		for (Item item : itemList) {
			System.out.println(i + ")" + item.getName() + "(" + item.getPrice() + ")");
			i++;
		}
	}

	public void selectAllAdmin() {
		int i = 1;
		for (Item item : itemList) {
			System.out.println(i + ")" + item.getName() + "(" + item.getPrice() + ")" + "/" + item.getCode());
			i++;
		}
	}

	public Item get(int index) {
		return itemList.get(index);
	}

	public int size() {
		return itemList.size();
	}

	/* U(update) */
	public void update(int index, String name, int price) {
		Item item = itemList.get(index);
		item.setName(name);
		item.setPrice(price);
		itemList.remove(index);
		itemList.add(index, item);
	}

	/* D(delete) */
	public void remove(int index) {
		itemList.remove(index);
	}

}
