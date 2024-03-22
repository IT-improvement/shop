package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

import dto.Cart;
import dto.Item;
import dto.User;

public class FileManager {

	final String ITEM = "item.txt";
	final String USER = "user.txt";
	final String LOG = "log.txt";

	FileWriter fw;
	FileReader fr;
	BufferedReader br;

	File fileItem;
	File fileUser;
	File fileLog;

	private ItemManager itemManager;
	private UserManager userManager;

	private FileManager() {
		fileItem = new File(ITEM);
		fileUser = new File(USER);
		fileLog = new File(LOG);

		itemManager = ItemManager.getInstance();
		userManager = UserManager.getInstance();
	}

	private static FileManager instance = new FileManager();

	public static FileManager getInstance() {
		return instance;
	}

	/* LOAD */
	public void loadItem() {
		if (!fileItem.exists())
			return;

		String data = "";
		try {
			fr = new FileReader(fileItem);
			br = new BufferedReader(fr);
			while (br.ready()) {
				data += br.readLine() + "\n";
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		String datas[] = data.split("\n");
		ArrayList<Item> items = new ArrayList<>();
		for (int i = 0; i < datas.length; i++) {
			String temp[] = datas[i].split(",");
			int code = Integer.parseInt(temp[0]);
			String name = temp[1];
			int price = Integer.parseInt(temp[2]);
			Item item = new Item(code, name, price);
			items.add(item);
		}
		itemManager.set(items);
	}

	/* SAVE */
	public void saveItem() {
		ArrayList<Item> items = itemManager.getAll();
		String data = "";
		for (Item item : items) {
			data += item.getCode() + "," + item.getName() + "," + item.getPrice() + "\n";
		}
		data = data.substring(0, data.length() - 1);
		try {
			fw = new FileWriter(ITEM);
			fw.write(data);
			fw.close();
		} catch (Exception e) {
			System.err.println("파일저장 오류(아이템)");
		}
	}

	public void savaUser() {
		ArrayList<User> list = userManager.getAll();
		String data = "";

		for (User user : list) {
			data += user.getName() + "," + user.getId() + "," + user.getPassword() + "," + user.getMoney();
			Cart cart = user.getCart();
			String temp = "";
			if (cart.getSize() != 0) {
				ArrayList<Item> carts = cart.clone();
				ArrayList<Integer> count = cart.getCount();
				int cnt = cart.getSize();
				for (int i = 0; i < cnt; i++) {
					temp += carts.get(i).getCode() + "@" + count.get(i) + "-";
				}
				temp = temp.substring(0, temp.length() - 1);
				data += ","+temp;
			}
			data += "\n";
		}

		try {
			fw = new FileWriter(fileUser);
			fw.write(data);
			fw.close();
		} catch (Exception e) {
			System.err.println("파일저장 오류(유저)");
		}
	}
}
