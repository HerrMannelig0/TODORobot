package com.epam.util;

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;
import static com.epam.util.UrlUtils.*;

/**
 * Tests of IRlUtilsClass
 */
public class UrlUtilsTest {

    /**
     * Testing {@code checkIfUrl} method - optimistic path
     */
    @Test
    public void testIfUrlIsUrl(){
        boolean url = checkIfUrl("demotywatory.pl");
        Assertions.assertThat(url).isTrue();
    }

    /**
     * Testing {@code checkIfUrl} method - passing null as parameter
     */
    @Test
    public void testIfUrlIsEmpty(){
        boolean url = checkIfUrl(null);
        Assertions.assertThat(url).isFalse();
    }

    /**
     * Testing {@code checkIfUrl} method - pessimistic path
     */
    @Test
    public void testIfTextIsNotUrl(){
        boolean url = checkIfUrl("sgsdrgrdg");
        Assertions.assertThat(url).isFalse();
    }


    /**
     * Testing {@code checkIfUrl} method - empty sting as parameter
     */
    @Test
    public void testIfEmptyStringIsNotUrl(){
        boolean url = checkIfUrl("");
        Assertions.assertThat(url).isFalse();
    }

    /**
     * Test of adding http to path's beginning, when there is no http already
     */
    @Test
    public void testIfUrlWithOutHttpHasHttp(){
        java.lang.String url = addHttpToBegining("www.facebook.com/");
        Assertions.assertThat(url).asString().contains("https://", "https://");
    }

    /**
     * Test of adding http to path's beginning, when there is http already
     */
    @Test
    public void testIfUrlHttpHasHttp(){
        java.lang.String url = addHttpToBegining("https://www.facebook.com/");
        Assertions.assertThat(url).asString().contains("https://", "https://");
    }

    /**
     * Test of removing slashes from URL
     */
    @Test
    public void ifPutUrlItRemoveSlashes(){
        String url="http://e-bookshop.pl.txt/362-wyprzedaz";

        String fileNameWithOutSlashes = UrlUtils.getFileName(url);

        Assertions.assertThat(fileNameWithOutSlashes).isEqualTo("e-bookshop.pl.txt.txt");
    }

}
