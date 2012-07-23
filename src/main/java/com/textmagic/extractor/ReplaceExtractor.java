package com.textmagic.extractor;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.textmagic.config.ExtractorConfig;

public class ReplaceExtractor extends AbstractExtractor {

    public final static String KEY = "Replace";

    public final static String FIELD_OLD_STRING = "OldString";

    public final static String FIELD_NEW_STRING = "NewString";

    private String oldString;

    private Pattern pattern;

    private String newString;

    private String next;

    private boolean hasNext;

    public String getKey() {
        return KEY;
    }

    /**
     * @return
     */
    @Override
    public String toString() {
        return "[oldString=" + oldString + ", newString=" + newString + "]";
    }

    public static ExtractorConfig buildConfig(String oldString, String newString) {
        return new ReplaceExtractor(oldString, newString).toConfig();
    }

    /**
     * @param oldString
     * @param newString
     */
    public ReplaceExtractor(String oldString, String newString) {
        super();
        this.oldString = oldString;
        this.newString = newString;
        pattern = Pattern.compile(oldString);
    }

    public ExtractorConfig toConfig() {
        ExtractorConfig extractorConfig = new ExtractorConfig();
        extractorConfig.setType(KEY);
        Map<String, String> param = new HashMap<>();
        param.put(FIELD_OLD_STRING, oldString);
        param.put(FIELD_NEW_STRING, newString);
        extractorConfig.setParam(param);
        return extractorConfig;
    }

    /**
     * @param patterns
     * @param dateFormat
     */
    public ReplaceExtractor() {
    }

    /**
     * @param html
     * @return
     */
    @Override
    public void init(String text) {
        if (text != null) {
            Matcher m = pattern.matcher(text);
            next = m.replaceAll(newString);
            hasNext = true;
            return;
        }
        hasNext = false;
        next = null;
    }

    /**
     * @return
     */
    @Override
    public boolean hasNextThisText() {
        if (hasNext) {
            hasNext = false;
            return true;
        }
        next = null;
        return false;
    }

    /**
     * @return
     */
    @Override
    public String nextThisText() {
        return next;
    }

}
