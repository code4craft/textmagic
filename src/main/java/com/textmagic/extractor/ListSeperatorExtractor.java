package com.textmagic.extractor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import com.textmagic.config.ExtractorConfig;

public class ListSeperatorExtractor extends AbstractExtractor {

    public final static String KEY = "ListSep";

    private String seperator;

    private Iterator<String> listIterator;

    public final static String FIELD_SEPERATOR = "Seperator";

    private final static String DEFAULT_SEPERATOR = ",";

    public String getKey() {
        return KEY;
    }

    public ListSeperatorExtractor(String seperator) {
        this.seperator = seperator;
    }

    public static ExtractorConfig buildConfig(String seperator) {
        ExtractorConfig extractorConfig = new ExtractorConfig();
        extractorConfig.setType(KEY);
        Map<String, String> param = Collections.singletonMap(FIELD_SEPERATOR, seperator);
        extractorConfig.setParam(param);
        return extractorConfig;
    }

    public ExtractorConfig toConfig() {
        ExtractorConfig extractorConfig = new ExtractorConfig();
        extractorConfig.setType(KEY);
        Map<String, String> param = Collections.singletonMap(FIELD_SEPERATOR, seperator);
        extractorConfig.setParam(param);
        return extractorConfig;
    }

    /**
     * @return
     */
    @Override
    public boolean hasNextThisText() {
        return listIterator.hasNext();
    }

    /**
     * @return
     */
    @Override
    public String nextThisText() {
        return listIterator.next();
    }

    /**
     * @param text
     */
    @Override
    public void init(String text) {
        if (text != null) {
            final String[] tags = text.split(seperator);
            List<String> tagList = new ArrayList<>(tags.length);
            for (String tag : tags) {
                tagList.add(tag);
            }
            if (CollectionUtils.isNotEmpty(tagList)) {
                listIterator = tagList.iterator();
            } else {
                listIterator = DummyIterator.instance;
            }
        } else {
            listIterator = DummyIterator.instance;
        }

    }

    /**
     * @return
     */
    @Override
    public String toString() {
        return seperator;
    }

}
