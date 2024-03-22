package controller;

import java.io.File;

public class FileManager {

	final String ITEM = "item.txt";
	final String USER = "user.txt";
	final String LOG = "log.txt";

	private FileManager() {

	}

	private static FileManager instance = new FileManager();

	public static FileManager getInstance() {
		return instance;
	}
}
