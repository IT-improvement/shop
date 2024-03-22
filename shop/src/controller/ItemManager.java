package controller;

import java.util.ArrayList;

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
	/* R(select) */
	/* U(update) */
	/* D(delete) */

}
