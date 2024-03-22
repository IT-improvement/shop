package shop;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

import controller.ItemManager;
import controller.UserManager;
import dto.Cart;
import dto.Item;
import dto.User;

public class Shop {

	private Scanner scan = new Scanner(System.in);
	private NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.KOREA);

	private UserManager userManager;
	private ItemManager itemManager;

	private int log;

	public Shop() {
		userManager = UserManager.getInstance();
		itemManager = ItemManager.getInstance();
		log = -1;
	}
	// Shop Project
	// class : Item, ItemManager, User, UserManager, Cart, FileManager Shop

	// method :

	// 유저 -
	// ㄴ 회원가입/탈퇴/
	// ㄴ 로그인/로그아웃/
	// ㄴ 쇼핑하기/
	// ㄴ 마이페이지(내장바구니,항목,삭제,수량수정,결제)
	// ㄴ 자동저장/자동로드

	// 관리자
	// ㄴ 아이템등록/삭제/수정/
	// ㄴ 조회(총 매출)

	// input type:int
	private int inputNum(String message) {
		int num = -1;
		try {
			System.out.print(message + ": ");
			String temp = scan.next();
			num = Integer.parseInt(temp);
		} catch (Exception e) {
			System.err.println("입력 오류: 숫자만 입력하세요.");
		}

		return num;
	}

	// input type:String
	private String inputString(String message) {
		System.out.print(message + ": ");
		return scan.next();
	}

	public void run() {
		while (true) {
			start();
			while (log != -1) {
				if (log == 0)
					admin();
				else if (log != 0)
					user();
			}
		}
	}

	/* 시작메뉴 */
	private void start() {
		printStartMenu();
		startMenu(inputNum("메뉴 선택"));
	}

	// Start Menu Output
	private void printStartMenu() {
		System.out.println("1)로그인");
		System.out.println("2)회원가입");
	}

	// Start Menu
	private void startMenu(int sel) {
		if (sel == 1)
			login();
		else if (sel == 2)
			signUp();
	}

	// login Method()
	private void login() {
		String id = inputString("아이디");
		int index = userManager.selectId(id);
		if (index == -1) {
			System.err.println("없는 아이디입니다.");
			return;
		}
		String pw = inputString("비밀번호");
		log = isCheckUser(index, pw);
		if (log == -1) {
			System.err.println("비밀번호가 일치하지 않습니다.");
		}
	}

	// find User Method()
	private int isCheckUser(int index, String pw) {
		if (userManager.checkPassword(index, pw)) {
			return index;
		}
		return -1;
	}

	// signUp Method()
	private void signUp() {
		String id = inputString("아이디");
		if (userManager.selectId(id) != -1) {
			System.err.println("이미 있는 아이디입니다.");
			return;
		}
		String pw = inputString("비밀번호");
		String pwCheck = inputString("재입력");
		if (!pw.equals(pwCheck)) {
			System.err.println("비밀번호가 일치하지 않습니다.");
			return;
		}
		String name = inputString("이름");
		int money = inputNum("가지고 있는 금액(100원단위)");
		if (money < 0 || money % 100 != 0) {
			System.err.println("금액을 올바르게 입력하세요.");
			return;
		}
		userManager.addUser(name, id, name, money);
	}

	/* User Function */
	private void user() {
		printUserMenu();
		userMenu(inputNum("메뉴입력"));
	}

	// user menu Output
	private void printUserMenu() {
		System.out.println("1)회원탈퇴");
		System.out.println("2)로그아웃");
		System.out.println("3)쇼핑하기");
		System.out.println("4)마이페이지");
	}

	// user Menu
	private void userMenu(int sel) {
		if (sel == 1)
			deleteUser();
		else if (sel == 2)
			logOut();
		else if (sel == 3)
			purchase();
		else if (sel == 4)
			myPage();
	}

	// delete user Method
	private void deleteUser() {
		String pw = inputString("비밀번호입력");
		if (!userManager.checkPassword(log, pw)) {
			System.err.println("비밀번호가 일치하지 않습니다.");
			return;
		}
		userManager.remove(log);
		log = -1;
	}

	// logout Method
	private void logOut() {
		log = -1;
	}

	// purchase Method
	private void purchase() {
		if (itemManager.size() == 0) {
			System.err.println("구매할 상품이 없습니다");
			return;
		}
		System.out.println("판패현황>>>");
		itemManager.selectAll();
		int index = inputNum("번호입력");
		if (!exceptionItemIndex(index)) {
			System.err.println("번호를 다시 입력하세요");
			return;
		}
		int num = inputNum("수량 입력");
		if (num < 0) {
			System.err.println("1개 이상 입력하세요.");
			return;
		}
		updateCart(index, num);
	}

	// update Cart
	private void updateCart(int index, int num) {
		Cart cart = userManager.getCart(log);
		cart = itemManager.addCart(cart, index, num);
		userManager.updateCart(log, cart);
	}

	// exception item index
	private boolean exceptionItemIndex(int index) {
		return index < 1 || index > itemManager.size() ? false : true;
	}

	// myPage
	private void myPage() {
		printmyPage();
		myPageMenu(inputNum("서브메뉴입력"));
	}

	// myPage subMenu Output
	private void printmyPage() {
		System.out.println("1)내 장바구니");
		System.out.println("2)항목 취소");
		System.out.println("3)수량 수정");
		System.out.println("4)결제");
	}

	// myPageMenu
	private void myPageMenu(int sel) {
		if (sel == 1)
			myJang();
		else if (sel == 2)
			deleteJang();
		else if (sel == 3)
			editJang();
		else if (sel == 4)
			userPurchase();
	}

	// myJang Method
	private void myJang() {
		Cart cart = userManager.getCart(log);
		ArrayList<Item> carts = cart.clone();
		ArrayList<Integer> count = cart.getCount();
		for (int i = 0; i < carts.size(); i++) {
			Item item = carts.get(i);
			System.out.printf("%d) %s(%d개)\n", (i + 1), item.getName(), count.get(i));
		}
	}

	// delete jang
	private void deleteJang() {
		Cart cart = userManager.getCart(log);
		int index = inputNum("항목번호입력");
		if (index < 0 || index > cart.getSize()) {
			System.err.println("번호가 일치하지 않습니다");
			return;
		}
		cart = update(cart, index - 1);
		userManager.updateCart(log, cart);
	}

	// Cart update(overload)
	private Cart update(Cart cart, int index) {
		ArrayList<Item> carts = cart.clone();
		ArrayList<Integer> count = cart.getCount();

		carts.remove(index);
		count.remove(index);
		cart.setCarts(carts);
		cart.setCount(count);
		return cart;
	}

	// edit jang
	private void editJang() {
		Cart cart = userManager.getCart(log);
		int index = inputNum("항목번호입력");
		if (index < 0 || index > cart.getSize()) {
			System.err.println("번호가 일치하지 않습니다");
			return;
		}
		int count = inputNum("수량입력");
		if (count == 0) {
			System.err.println("항목삭제메뉴를 이용하세요");
			return;
		} else {
			cart = update(cart, index - 1, count);
		}
		userManager.updateCart(log, cart);
	}

	// cart update(overlode)
	private Cart update(Cart cart, int index, int num) {
		ArrayList<Item> carts = cart.clone();
		ArrayList<Integer> count = cart.getCount();
		count.set(index, num);
		cart.setCarts(carts);
		cart.setCount(count);
		return cart;
	}

	// user purchase
	private void userPurchase() {
		int total = 0;
		Cart cart = userManager.getCart(log);
		ArrayList<Item> carts = cart.clone();
		ArrayList<Integer> count = cart.getCount();
		for (int i = 0; i < cart.getSize(); i++) {
			Item item = carts.get(i);
			int price = item.getPrice();
			total += count.get(i) * price;
		}
		System.out.println("총 결제금액: " + nf.format(total));
		String sel = inputString("결제하시겠습니까?(1:에/나머지:아니오)");
		if (sel.equals("1")) {
			checkPurchase(total);
		}
	}

	// checkPurchase
	private void checkPurchase(int total) {
		User user = userManager.get(log);
		int money = user.getMoney();
		if (money < total) {
			System.err.println("현금이 부족합니다.");
			return;
		}
		money -= total;
		user.setMoney(money);
		userManager.updateUser(log, user);
		printBill(total, money);

		// edit Admin
		user = userManager.get(0);
		money = user.getMoney();
		money += total;
		user.setMoney(money);
		userManager.updateUser(0, user);

	}

	private void printBill(int total, int money) {
		System.out.println("--------------");
		myJang();
		System.out.println("총 결제금액: " + nf.format(total));
		System.out.println("남은 현금: " + nf.format(money));
		System.out.println("--------------");
	}

	/* Admin Function */
	private void admin() {
		printAdminMenu();
		adminMenu(inputNum("메뉴입력"));
	}

	// amdin menu Output
	private void printAdminMenu() {
		System.out.println("1)아이템");
		System.out.println("2)조회(매출)");
		System.out.println("3)로그아웃");
	}

	// admin menu
	private void adminMenu(int sel) {
		if (sel == 1)
			adminSubMenu();
		else if (sel == 2)
			totalSales();
		else if (sel == 3)
			logOut();
	}

	// admin subMenu about Menu1
	private void adminSubMenu() {
		printAdminSubmenu();
		adminSubmenu(inputNum("서브메뉴"));
	}

	// admin submenu Output
	private void printAdminSubmenu() {
		System.out.println("1)등록");
		System.out.println("2)삭제");
		System.out.println("3)수정");
	}

	// admin submenu
	private void adminSubmenu(int sel) {
		if (sel == 1)
			addItem();
		else if (sel == 2)
			deleteItem();
		else if (sel == 3)
			modifyItem();
	}

	// addItem Method
	private void addItem() {
		itemManager.selectAllAdmin();
		int code = itemManager.creatCode();
		String name = inputString("이름입력");
		int price = exceptionPrice();
		Item item = new Item(code, name, price);
		itemManager.add(item);
	}

	// exception price
	private int exceptionPrice() {
		int num = 1;
		while (num < 1 || num % 100 != 0) {
			num = inputNum("가격입력");
		}
		return num;
	}

	// deleteItem Method
	private void deleteItem() {
		int code = inputNum("코드입력");
		int index = itemManager.indexOf(code);
		if (index == -1) {
			System.err.println("아이템이 없습니다.");
			return;
		}
		System.out.println("삭제되었습니다");
		itemManager.remove(index);
	}

	// modifyItem item
	private void modifyItem() {
		int code = inputNum("코드입력");
		int index = itemManager.indexOf(code);
		if (index == -1) {
			System.err.println("아이템이 없습니다.");
			return;
		}
		Item item = itemManager.get(index);

		System.out.println("이름:" + item.getName());
		String name = inputString("이름입력");
		int price = exceptionPrice();
		itemManager.update(index, name, price);
	}

	// total Sales Output
	private void totalSales() {
		User user = userManager.get(log);

		int total = user.getMoney();
		System.out.println("총 매출액: " + nf.format(total));
	}
}
