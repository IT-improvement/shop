package shop;

import java.util.Scanner;

import controller.UserManager;

public class Shop {

	private Scanner scan = new Scanner(System.in);

	private UserManager userManager;

	private int log;

	public Shop() {
		userManager = UserManager.getInstance();
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
//		if (sel == 2)
//			signUp();
	}
	
	//login Method()
	private void login() {
		String id = inputString("아이디");
		String pw = inputString("비밀번호");
		log = isCheckUser(id, pw);
	}
	
	//find User Method()
	private int isCheckUser(String id, String pw) {
		int index = userManager.selectId(id);
		if (userManager.checkPassword(index, pw)) {
			return index;
		}
		return -1;
	}

	/* 유저기능 */
	/* 관리자기능 */

}
