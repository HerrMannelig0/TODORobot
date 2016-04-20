package com.epam.robot;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

public class Library extends AbstractList<Book> {
	private List<Book> library = new ArrayList<>();

	@Override
	public boolean add(Book book) {
		return library.add(book);
	}

	@Override
	public Book get(int index) {
		return library.get(index);
	}

	@Override
	public int size() {
		return library.size();
	}

	
	
}
