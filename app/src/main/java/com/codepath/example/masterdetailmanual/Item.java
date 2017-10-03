package com.codepath.example.masterdetailmanual;

import java.io.Serializable;
import java.util.ArrayList;

public class Item implements Serializable {
	private static final long serialVersionUID = -1213949467658913456L;
	private String title;
	private String body;
	private static ArrayList<Item> items = new ArrayList<Item>();
   // private ArrayList<Item> subitems = new ArrayList<Item>();

	public Item(String title, String body) {
		this.title = title;
		this.body = body;
	}

	public static void clearItems() {
		items.clear();
	}

	public String getTitle() {
		return title;
	}

	public String getBody() {
		return body;
	}

	@Override
	public String toString() {
		return getTitle();
	}

	public static ArrayList<Item> getItems() {
		return items;
	}

	public static void addItem(String canteen, String content) {
		items.add(new Item(canteen,content));
	}

	//public st


}