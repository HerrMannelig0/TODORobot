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

public class FileLinkHandlerTest {
	
    @Test
    public void testThreePartsContainigReturnsFalse() {
       String [] emptyStringsTab = {""};
       assertThat(FileLinkHandler.doesLinkContainsSixParts(emptyStringsTab)).isEqualTo(false);
    }

    @Test
    public void testThreePartsContainigReturnsTrue() {
       String [] notEmptyStringsTab = {"a", "b", "c", "d", "e", "f"};
       assertThat(FileLinkHandler.doesLinkContainsSixParts(notEmptyStringsTab)).isEqualTo(true);
    }
    
    @Test
    public void testReadingLinksFromBlankFile() throws FileNotFoundException {
    	FileLinkHandler fileLinkHandler = new FileLinkHandler();
       File file = new File("src/test/resources/BLANK.txt");
       Set<Link> links = fileLinkHandler.readLinksFromFile(file);
       assertThat(links.isEmpty()).isTrue();  

    }
    
    @Test
    public void testReadingLinksFromOneLineFile() throws FileNotFoundException {
    	int one = 1;
    	FileLinkHandler fileLinkHandler = new FileLinkHandler();
       File file = new File("src/test/resources/OneLine.txt");
       Set<Link> links = fileLinkHandler.readLinksFromFile(file);
       assertThat(links.size()).isEqualTo(one);
    }
 
    @Test
    public void testUpdatingLineList() throws FileNotFoundException {
    	FileLinkHandler fileLinkHandler = new FileLinkHandler();
       File file = new File("src/test/resources/OneLine.txt");
       fileLinkHandler.updateLinkList(file);
       assertThat(fileLinkHandler.getLinksList().isEmpty()).isFalse();
    }
    
    @Test
    public void testUpdatingURLList() throws FileNotFoundException {
    	FileLinkHandler fileLinkHandler = new FileLinkHandler();
       File file = new File("src/test/resources/OneLine.txt");
       fileLinkHandler.updateLinkList(file);
       fileLinkHandler.updateURLList();
       
       assertThat(fileLinkHandler.getUrlList().isEmpty()).isFalse();
    }
    
    @Test
    public void testClearingLinkList() throws FileNotFoundException {
    	FileLinkHandler fileLinkHandler = new FileLinkHandler();
    	File file = new File("src/test/resources/OneLine.txt");
    	fileLinkHandler.updateLinkList(file);

    	fileLinkHandler.clearLinkList();
    	
    	assertThat(fileLinkHandler.getLinksList().isEmpty()).isTrue();
    }
    
    @Test
    public void testClearingURLList() throws FileNotFoundException {
    	FileLinkHandler fileLinkHandler = new FileLinkHandler();
    	File file = new File("src/test/resources/OneLine.txt");
    	fileLinkHandler.updateLinkList(file);
    	fileLinkHandler.updateURLList();
       
    	fileLinkHandler.clearURLList();
    	
    	assertThat(fileLinkHandler.getUrlList().isEmpty()).isTrue();
    }

    @Test
    public void testWritingToFile() throws FileNotFoundException {
    	FileLinkHandler fileLinkHandler = new FileLinkHandler();
    	File file = new File("src/test/resources/WritingToFileTest.txt");
    	List<Link> list = mock(List.class);
    	Link link = mock(Link.class);
    	
    	when(link.toString()).thenReturn("a");
    	when(list.size()).thenReturn(1);
    	when(list.get(0)).thenReturn(link);
    	
    	fileLinkHandler.writeLinksToFile(list, file);
    	verify(list).get(0);  	   	
    }
   
}
