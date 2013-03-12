package com.actvc.client.common;

import com.google.gwt.user.client.ui.ListBox;

public class MyList extends ListBox {

	public MyList(String[] items) {

		for (String i : items) {
			this.addItem(i);
		}
	}

}

// private String[] itemList = null;
// private boolean firstItemGiven = false;
//
// public MyList(String[] items) {
// new MyList(items, null);
// }
//
// public MyList(String[] items, String firstItem) {
// super();
//
// itemList = items;
// firstItemGiven = (firstItem != null);
//
// if (firstItemGiven) {
// this.addItem(firstItem);
// }
// for (String i : itemList) {
// this.addItem(i);
// }
// }
//
// public int indexOf(String s) {
// int result = 0;
// int i = firstItemGiven ? 1 : 0;
// for (String item : itemList) {
// if (item.equals(s)) {
// result = i;
// break;
// }
// i++;
// }
// return result;
// }
