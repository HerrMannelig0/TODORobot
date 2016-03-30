package com.epam.util;

import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;
import static com.epam.util.UrlUtils.*;

/**
 * Created by damian on 21.03.16.
 */
public class UrlUtilsTest {

    @Test
    public void testIfUrlIsUrl(){
        //given

        //when
        boolean url = checkIfUrl("demotywatory.pl");

        //then
        Assertions.assertThat(url).isTrue();
    }

    @Test
    public void testIfUrlIsEmpty(){
        //given

        //when
        boolean url = checkIfUrl(null);

        //then
        Assertions.assertThat(url).isFalse();
    }

    @Test
    public void testIfTextIsNotUrl(){
        //given

        //when
        boolean url = checkIfUrl("sgsdrgrdg");

        //then
        Assertions.assertThat(url).isFalse();
    }


    @Test
    public void testIfEmptyStringIsNotUrl(){
        //given

        //when
        boolean url = checkIfUrl("");

        //then
        Assertions.assertThat(url).isFalse();
    }

    @Test
    public void testIfUrlWithOutHttpHasHttp(){
        //given

        //when
        java.lang.String url = addHttpToBegining("www.facebook.com/");

        //then
        Assertions.assertThat(url).asString().contains("https://", "https://");
        Assertions.assertThat(url).asString().contains("https://", "https://");
    }

    @Test
    public void testIfUrlHttpHasHttp(){
        //given

        //when
        java.lang.String url = addHttpToBegining("https://www.facebook.com/");

        //then
        Assertions.assertThat(url).asString().contains("https://", "https://");
        Assertions.assertThat(url).asString().contains("https://", "https://");
    }

    @Test
    public void ifPutUrlItRemoveSlashes(){
        String url="http://e-bookshop.pl.txt/362-wyprzedaz";

        String fileNameWithOutSlashes = UrlUtils.getFileName(url);

        Assertions.assertThat(fileNameWithOutSlashes).isEqualTo("e-bookshop.pl.txt.txt");
    }

}
