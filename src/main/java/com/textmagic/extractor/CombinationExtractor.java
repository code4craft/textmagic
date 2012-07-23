package com.textmagic.extractor;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

/**
 * @author cairne
 * @date 2012-7-21
 */
public class CombinationExtractor extends AbstractExtractor {

    private List<Extractor> extractors;

    private Iterator<Extractor> extractorIterator;

    private Iterator<String> iterator;

    private String thisText;

    /**
     * @return
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Extractors:[");
        for (Extractor extractor : extractors) {
            sb.append(extractor.toString() + " | ");
        }
        sb.append("]");
        return sb.toString();
    }

    public CombinationExtractor(Extractor... extractors) {
        this.extractors = Arrays.asList(extractors);
    }

    public CombinationExtractor(List<Extractor> extractors) {
        this.extractors = extractors;
    }

    public List<Extractor> getExtractors() {
        return extractors;
    }

    /**
     * @return
     */
    @Override
    public boolean hasNextThisText() {
        if (iterator == null) {
            return false;
        }
        if (iterator.hasNext()) {
            return true;
        } else {
            if (extractorIterator.hasNext()) {
                iterator = extractorIterator.next().extract(thisText);
                return hasNextThisText();
            } else {
                return false;
            }
        }
    }

    /**
     * @return
     */
    @Override
    public String nextThisText() {
        return iterator.next();
    }

    /**
     * @param text
     */
    @Override
    public void init(String text) {
        thisText = text;
        if (CollectionUtils.isEmpty(extractors)) {
            return;
        }
        extractorIterator = extractors.iterator();
        iterator = extractorIterator.next().extract(text);
    }

}
