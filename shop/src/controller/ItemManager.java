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
}
