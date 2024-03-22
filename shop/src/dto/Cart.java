package dto;

import java.util.ArrayList;

public class Cart {
	private ArrayList<Item> carts;
	private ArrayList<Integer> count;
	private int size;


	public Cart() {
		carts = new ArrayList<>();
		count = new ArrayList<>();
		size = carts.size();
	}

	public int getSize() {
		return size;
	}
	
	public ArrayList<Item> clone() {
		return (ArrayList<Item>) carts.clone();
	}

	public void setCarts(ArrayList<Item> carts) {
		size = carts.size();
		this.carts = carts;
	}

	public ArrayList<Integer> getCount() {
		return (ArrayList<Integer>) count.clone();
	}

	public void setCount(ArrayList<Integer> count) {
		this.count = count;
	}

}
