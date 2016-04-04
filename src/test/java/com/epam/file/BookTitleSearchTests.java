package com.epam.file;

import java.lang.reflect.Method;

import org.testng.annotations.Test;

import com.epam.robot.BookTitleSearch;

public class BookTitleSearchTests {

	@Test
	public void testOfAddressesHashSet() {
		// testing searchsubpagesinpagebynum, need to check addresshashset to
		// know what method does

	}

	@Test
	public void testOfSearchSubPagesInPageByNumber() throws Exception {

	}

	@Test
	public void testOfaddLinkToSetAndSearchInLinkForPages() throws Exception {
		Object argClasses;
		// private method
		Method method = BookTitleSearch.getDeclaredMethod("addLinkToSetAndSearchInLinkForPages", argClasses);
		method.setAccessible(true);
		return method.invoke(targetObject, argObjects);

	}

}
