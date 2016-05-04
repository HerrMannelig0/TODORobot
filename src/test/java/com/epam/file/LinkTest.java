package com.epam.file;

import static org.assertj.core.api.Assertions.*;
import org.testng.annotations.Test;

/**
 * Tests of {@code Link} class
 */
public class LinkTest {

    /**
     * Test of removing slashes from URL
     */
    @Test
    public void ifPutUrlItRemoveSlashes(){
        Link link=new Link("dsa///dsadsad/adss", null, null, null, null, null);

        String fileNameWithOutSlashes = link.createFileName();

        assertThat(fileNameWithOutSlashes).isEqualTo("dsadsadsadadss");
    }
    
    /**
     * Test of non-equality of two links 
     */
    @Test
	public void testIfTwoDifferentLinksAreNotEqual() throws Exception {
		Link link1 = new Link("a", null, null, null, null, null);
		Link link2 = new Link("b", null, null, null, null, null);
		
		assertThat(link1.equals(link2)).isFalse();
	}
    
    /**
     * Test of equality of two links 
     */
    @Test
   	public void testIfTwoSameLinksAreEqual() throws Exception {
   		Link link1 = new Link("a", "a", "a", "a", "a", "a");
   		Link link2 = new Link("a", "a", "a", "a", "a", "a");
   		
   		assertThat(link1.equals(link2)).isTrue();
   	}
    
    /**
	 * Test of non-equality of two, partially same, links 
     */
    @Test
   	public void testIfTwoPartiallySameLinksAreNotEqual() throws Exception {
   		Link link1 = new Link("a", "a", "a", "a", "a", "a");
   		Link link2 = new Link("a", "b", "a", "a", "a", "a");
   		
   		assertThat(link1.equals(link2)).isFalse();
   	}
    
    /**
     * Test of the same hascodes of the same objects
     */
    @Test
	public void testSameHashCodeForSameObjects() throws Exception {
    	Link link1 = new Link("a", "a", "a", "a", "a", "a");
   		Link link2 = new Link("a", "a", "a", "a", "a", "a");
   		
   		int hash1 = link1.hashCode();
   		int hash2 = link2.hashCode();
   		
   		assertThat(hash1 == hash2).isTrue();
	}
}
