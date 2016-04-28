package com.epam.robot;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.epam.file.Category;

/*package com.epam.robot;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import com.epam.file.Category;

*//**
 * This map contains category as key and library of books, that should match to
 * that category, as value.
 */
public class LibrariesMap implements Map<Category, Library> {

	private Map<Category, Library> map;

	public LibrariesMap() {
		map = new HashMap<>();
	}

	/**
	 * This static factory method converts Map<String, List<Book>> map into LibraryiesMap.
	 * @param Map with String as key and List of Books as value
	 * @return this
	 */
	public static LibrariesMap generateFrom(Map<String, List<Book>> map) {
		LibrariesMap librariesMap = new LibrariesMap();
		for (Map.Entry<String, List<Book>> entry : map.entrySet()) {
			Category category = new Category(entry.getKey());
			Library library = new Library(entry.getValue());
			librariesMap.put(category, library);
		}
		return librariesMap;
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
	public boolean containsKey(Object key) {
		return map.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return map.containsKey(value);
	}

	@Override
	public Library get(Object key) {
		return map.get(key);
	}

	@Override
	public Library put(Category key, Library value) {
		return map.put(key, value);
	}

	@Override
	public Library remove(Object key) {
		return map.remove(key);
	}

	@Override
	public void putAll(Map<? extends Category, ? extends Library> m) {
		map.putAll(m);
	}

	@Override
	public void clear() {
		map.clear();
	}

	@Override
	public Set<Category> keySet() {
		return map.keySet();
	}

	@Override
	public Collection<Library> values() {
		return map.values();
	}

	@Override
	public Set<java.util.Map.Entry<Category, Library>> entrySet() {
		return map.entrySet();
	}

}
