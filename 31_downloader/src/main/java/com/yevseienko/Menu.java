package com.yevseienko;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum Menu {
	EXIT(0), DOWNLOAD(1), SHOW_PROGRESS(2), TEST(3), CHANGE_DOWNLOAD_DIR(4), TRY_AGAIN(-1);

	private final int _number;
	private static Map<Integer, Menu> _menu = null;

	public int getNumber() {
		return _number;
	}

	Menu(int num) {
		_number = num;
	}

	public static Menu getByNumber(int num) {
		if (_menu == null) {
			ArrayList<Menu> menu = new ArrayList<>(Arrays.asList(Menu.values()));
			_menu = new HashMap<>();
			for (Menu m : menu) {
				_menu.put(m._number, m);
			}
		}
		if (_menu.containsKey(num)) {
			return _menu.get(num);
		}
		return TRY_AGAIN;
	}
}