package com.epam.library;

import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Own thread-safe collection based on Set keeping objects of Book type.
 */
public class ConcurrentLibrary extends AbstractSet<Book> {

	private ConcurrentHashMap<Book, Object> map;
	private final static Object DUMB_OBJECT = new Object();

	public ConcurrentLibrary() {
		map = new ConcurrentHashMap<>();
	}

	@Override
	public int size() {
		return map.size();
	}

	@Override
	public boolean isEmpty() {
		return map.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return map.containsKey(o);
	}

	@Override
	public Iterator<Book> iterator() {
		return map.keySet().iterator();
	}


	@Override
	public boolean add(Book e) {
		return map.put(e, DUMB_OBJECT) == null;
	}

	@Override
	public boolean remove(Object o) {
		return map.remove(o) == DUMB_OBJECT;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		for (Object e : c)
			if (!contains(e))
				return false;
		return true;
	}

	@Override
	public boolean addAll(Collection<? extends Book> c) {
		boolean modified = false;
		for (Book e : c)
			if (add(e))
				modified = true;
		return modified;
	}

	@Override
	public void clear() {
		map.clear();
	}

}
