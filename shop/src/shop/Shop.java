package shop;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Scanner;

import controller.ItemManager;
import controller.UserManager;
import dto.Item;
import dto.User;

public class Shop {

	private Scanner scan = new Scanner(System.in);

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
			System.err.print(message + "입력 오류: 숫자만 입력하세요.");
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
			if (log == 0)
				admin();
//			else if(log!=-1&&log!=0)
//				user();
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
		if (sel == 2)
			signUp();
	}

	// login Method()
	private void login() {
		String id = inputString("아이디");
		String pw = inputString("비밀번호");
		log = isCheckUser(id, pw);
		if (log == -1) {
			System.out.println("비밀번호가 일치하지 않습니다.");
		}
	}

	// find User Method()
	private int isCheckUser(String id, String pw) {
		int index = userManager.selectId(id);
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
			System.out.println("비밀번호가 일치하지 않습니다.");
			return;
		}
		String name = inputString("이름");
		int money = inputNum("가지고 있는 금액");
		userManager.addUser(name, id, name, money);
	}

	/* 유저기능 */
	/* 관리자기능 */
	private void admin() {
		printAdminMenu();
		adminMenu(inputNum("메뉴입력"));
	}

	// amdin menu Output
	private void printAdminMenu() {
		System.out.println("1)아이템");
		System.out.println("2)조회(매출)");
	}

	// admin menu
	private void adminMenu(int sel) {
		if (sel == 1)
			adminSubMenu();
		else if (sel == 2)
			totalSales();
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
		NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.KOREA);

		int total = user.getMoney();
		System.out.println("총 매출액: " + nf.format(total));
	}
}
