package shop;

import java.util.Scanner;

import controller.UserManager;

public class Shop {

	private Scanner scan = new Scanner(System.in);

	private UserManager userManager = UserManager.getInstance();
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

		}
	}
}
