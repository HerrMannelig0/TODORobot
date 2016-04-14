package com.epam.robot;

import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;
import static org.assertj.core.api.Assertions.*;
import org.testng.annotations.Test;

import com.epam.file.FileBookHandler;

public class FileBookHandlerTest {

	@Test
	public void testWritingToFile() throws Exception {
		String filename = "src/main/resources/WritingTest.txt";
		FileBookHandler.writeBookToFile(new Book("anyTitle", "AnyAuthor", new Keywords(new String[]{"keyword"})), new PrintWriter(new File(filename)));
		
		Scanner s = new Scanner(filename);
		assertThat(s.hasNext()).isTrue();
		s.close();
	}
	
	
	
}
