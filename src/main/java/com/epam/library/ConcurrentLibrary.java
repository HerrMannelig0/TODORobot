package com.epam.library;

import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentLibrary implements Set<Book> {

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
	public Object[] toArray() {
		return map.entrySet().toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return map.entrySet().toArray(a);
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
	public boolean retainAll(Collection<?> c) {
		Objects.requireNonNull(c);
		boolean modified = false;
		Iterator<Book> it = iterator();
		while (it.hasNext()) {
			if (!c.contains(it.next())) {
				it.remove();
				modified = true;
			}
		}
		return modified;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		Objects.requireNonNull(c);
		boolean modified = false;

		if (size() > c.size()) {
			for (Iterator<?> i = c.iterator(); i.hasNext();)
				modified |= remove(i.next());
		} else {
			for (Iterator<?> i = iterator(); i.hasNext();) {
				if (c.contains(i.next())) {
					i.remove();
					modified = true;
				}
			}
		}
		return modified;
	}

	@Override
	public void clear() {
		map.clear();
	}

}
