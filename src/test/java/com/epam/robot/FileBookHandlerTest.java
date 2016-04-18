package com.epam.robot;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.PrintWriter;
import java.net.URL;
import java.util.Scanner;

import org.testng.annotations.Test;

import com.epam.file.FileBookHandler;

public class FileBookHandlerTest {

	@Test
	public void testWritingToFile() throws Exception {
		String filename = "src/main/resources/WritingTest.txt";
		FileBookHandler.writeBookToFile(new Book("anyTitle", "AnyAuthor", "Free", new Keywords(new String[]{"keyword"}), new URL("https://www.gutenberg.org/ebooks/search/?query=free+book&go=Go")), new PrintWriter(new File(filename)));
		
		Scanner s = new Scanner(filename);
		assertThat(s.hasNext()).isTrue();
		s.close();
	}
	
	
	
}
