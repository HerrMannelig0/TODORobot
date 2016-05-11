package com.epam.library;

import java.util.AbstractSet;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * Wrapper class to set of strings, which can be interpreted as book's keywords.
 * 
 * @see Book
 */
public class Keywords extends AbstractSet<String>{
	private Set <String> keywords;
	
	public Keywords(String[] keywordsTable){
		keywords = new HashSet<>(Arrays.asList(keywordsTable));
	}
	
	/**
	 * Check if given set of strings contains one or more keywords stored in this object.
	 * @param keywordsToCheck
	 * @return True if keywords contains one or more keywords from given set.
	 */
	public boolean contains(Set<String> keywordsToCheck){
		for(String keyword : keywords){
			for (String keywordToCheck : keywordsToCheck) {
				if(keyword.equals(keywordToCheck)) return true;
			}
		}
		return false;
	}
	
	@Override
	public String toString() {
		return "Keywords [keywords=" + keywords + "]";
	}

	@Override
	public Iterator<String> iterator() {
		return keywords.iterator();
	}

	@Override
	public int size() {
		return keywords.size();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((keywords == null) ? 0 : keywords.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		Keywords otherKeywords = (Keywords) obj;
		TreeSet<String> thisTree = new TreeSet<>(this);
		TreeSet<String> otherTree = new TreeSet<>(otherKeywords);
		
		Iterator<String> first = thisTree.iterator();
		Iterator<String> second = otherTree.iterator();
		
		while(first.hasNext() && second.hasNext()){
			if(!(first.next().equals(second.next()))) return false;
		}
		return true;
	}

	

	
	
	
}
