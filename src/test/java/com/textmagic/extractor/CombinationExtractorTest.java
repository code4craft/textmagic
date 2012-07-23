package com.textmagic.extractor;

import java.util.Iterator;

import org.junit.Test;

/**
 * @author cairne
 * @date 2012-7-23
 */
public class CombinationExtractorTest {

    @Test
    public void testExtract() {
        RegexExtractor regexExtractor1 = new RegexExtractor("a+(b*)", new int[] { 0, 1 });
        RegexExtractor regexExtractor2 = new RegexExtractor("(a+)b*", new int[] { 1 });
        CombinationExtractor combinationExtractor = new CombinationExtractor(regexExtractor1,
                regexExtractor2);
        Iterator<String> it = combinationExtractor.extract("aaaaabbbbbbbbbb");
        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }

}
