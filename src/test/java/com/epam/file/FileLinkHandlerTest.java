package com.epam.file;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Set;

import org.testng.annotations.Test;

/**
 * Tests of {@code FileLinkHandler} class
 *
 */
public class FileLinkHandlerTest {
	
    /**
     * Test if {@code doesLinkContainsSixParts()} returns false if given object does not contain six parts
     */
    @Test
    public void testSixPartsContainigReturnsFalse() {
       String [] emptyStringsTab = {""};
       assertThat(FileLinkHandler.doesLinkContainsSixParts(emptyStringsTab)).isEqualTo(false);
    }

    /**
     * Test if {@code doesLinkContainsSixParts()} returns true if given object contains six parts
     */
    @Test
    public void testSixPartsContainigReturnsTrue() {
       String [] notEmptyStringsTab = {"a", "b", "c", "d", "e", "f"};
       assertThat(FileLinkHandler.doesLinkContainsSixParts(notEmptyStringsTab)).isEqualTo(true);
    }
    
    /**
     * Test reading from blank file
     * @throws FileNotFoundException
     */
    @Test
    public void testReadingLinksFromBlankFile() throws FileNotFoundException {
    	FileLinkHandler fileLinkHandler = new FileLinkHandler();
       File file = new File("src/test/resources/BLANK.txt");
       Set<Link> links = fileLinkHandler.readLinksFromFile(file);
       assertThat(links.isEmpty()).isTrue();  

    }
    
    /**
     * Test reading from non-empty file
     * @throws FileNotFoundException
     */
    @Test
    public void testReadingLinksFromOneLineFile() throws FileNotFoundException {
    	int one = 1;
    	FileLinkHandler fileLinkHandler = new FileLinkHandler();
       File file = new File("src/test/resources/OneLine.txt");
       Set<Link> links = fileLinkHandler.readLinksFromFile(file);
       assertThat(links.size()).isEqualTo(one);
    }
 
    /**
     * Test of putting new item from file to LinkList
     * @throws FileNotFoundException
     */
    @Test
    public void testUpdatingLineList() throws FileNotFoundException {
    	FileLinkHandler fileLinkHandler = new FileLinkHandler();
       File file = new File("src/test/resources/OneLine.txt");
       fileLinkHandler.updateLinkList(file);
       assertThat(fileLinkHandler.getLinksList().isEmpty()).isFalse();
    }
    
    /**
     * Test of updating list of URLs
     * @throws FileNotFoundException
     */
    @Test
    public void testUpdatingURLList() throws FileNotFoundException {
    	FileLinkHandler fileLinkHandler = new FileLinkHandler();
       File file = new File("src/test/resources/OneLine.txt");
       fileLinkHandler.updateLinkList(file);
       fileLinkHandler.updateURLList();
       
       assertThat(fileLinkHandler.getUrlList().isEmpty()).isFalse();
    }
    
    /**
     * Test of clearing the list of links
     * @throws FileNotFoundException
     */
    @Test
    public void testClearingLinkList() throws FileNotFoundException {
    	FileLinkHandler fileLinkHandler = new FileLinkHandler();
    	File file = new File("src/test/resources/OneLine.txt");
    	fileLinkHandler.updateLinkList(file);

    	fileLinkHandler.clearLinkList();
    	
    	assertThat(fileLinkHandler.getLinksList().isEmpty()).isTrue();
    }
    
    /**
     * Test of clearing the list of links
     * @throws FileNotFoundException
     */
    @Test
    public void testClearingURLList() throws FileNotFoundException {
    	FileLinkHandler fileLinkHandler = new FileLinkHandler();
    	File file = new File("src/test/resources/OneLine.txt");
    	fileLinkHandler.updateLinkList(file);
    	fileLinkHandler.updateURLList();
       
    	fileLinkHandler.clearURLList();
    	
    	assertThat(fileLinkHandler.getUrlList().isEmpty()).isTrue();
    }

    /**
     * Test of writing given list to file
     * @throws FileNotFoundException
     */
    @Test
    public void testWritingToFile() throws FileNotFoundException {
    	FileLinkHandler fileLinkHandler = new FileLinkHandler();
    	File file = new File("src/test/resources/WritingToFileTest.txt");
    	@SuppressWarnings("unchecked")
		List<Link> list = mock(List.class);
    	Link link = mock(Link.class);
    	
    	when(link.toString()).thenReturn("a");
    	when(list.size()).thenReturn(1);
    	when(list.get(0)).thenReturn(link);
    	
    	fileLinkHandler.writeLinksToFile(list, file);
    	verify(list).get(0);  	   	
    }
   
}
