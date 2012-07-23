package com.textmagic.extractor;

import java.util.Iterator;

/**
 * @author cairne
 * @date 2012-4-1
 */
public interface Extractor {

    public Iterator<String> extract(String text);

    public Extractor setPreviousExtractor(Extractor extractor);

    public Extractor getPreviousExtractor();

}
