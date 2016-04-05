package com.epam.robot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Keywords {
	private List <String> keywords;
	
	public Keywords(String [] keywordsTable){
		keywords = new ArrayList<>(Arrays.asList(keywordsTable));
	}
	
	@Override
	public String toString() {
		return "<keywords: " + Arrays.toString(keywords.toArray()) + ">";
	}
	
}
