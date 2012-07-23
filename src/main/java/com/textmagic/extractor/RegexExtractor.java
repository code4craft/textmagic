package com.textmagic.extractor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * 这个类同时也可做String类型返回的extractor
 * 
 * @author cairne
 * @date 2012-4-1
 */
public class RegexExtractor extends AbstractExtractor {

    @Override
    public String toString() {
        return "RegexTextExtractor [pattern=" + pattern + "]";
    }

    private String pattern;

    private Pattern _pattern;

    private Matcher m;

    private boolean hasNext;

    private String next;

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public RegexExtractor clone() {
        return new RegexExtractor(pattern);
    }

    public RegexExtractor(String pattern) {
        this.pattern = pattern;
    }

    /**
     * WARN: this method is not concurently safe
     * 
     * @param html
     * @return
     */
    @Override
    public void init(String text) {
        if (_pattern != null) {
            m = _pattern.matcher(text);
            if (m.find()) {
                hasNext = true;
                return;
            }
        }
        if (pattern == null) {
            hasNext = false;
            return;
        }
        _pattern = Pattern.compile(pattern, Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
        m = _pattern.matcher(text);
        if (m.find()) {
            hasNext = true;
            return;
        }
        hasNext = false;
        return;
    }

    /**
     * @return
     */
    @Override
    public boolean hasNextThisText() {
        if (!hasNext) {
            next = null;
            return false;
        }
        next = m.group(1);
        hasNext = m.find();
        return true;
    }

    /**
     * @return
     */
    @Override
    public String nextThisText() {
        return next;
    }

}
