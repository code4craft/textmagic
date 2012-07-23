package com.textmagic.extractor;

import java.util.Iterator;

import org.junit.Test;

/**
 * @author cairne
 * @date 2012-7-23
 */
public class RegexExtractorTest {

    @Test
    public void extract() {
        RegexExtractor regexExtractor = new RegexExtractor("a*(b*)", new int[] { 0, 1, 2 });
        Iterator<String> it = regexExtractor.extract("aaaaabbbbbbbbbb");
        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }

}
