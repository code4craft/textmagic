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

    private int[] groups;

    private int groupIndex;

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
        this.groups = new int[] { 0 };
    }

    public RegexExtractor(String pattern, int[] groups) {
        this.pattern = pattern;
        this.groups = groups;
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
        if (groupIndex < groups.length) {
            next = m.group(groups[groupIndex++]);
        } else {
            groupIndex = 0;
            hasNext = m.find();
            if (!hasNext) {
                return false;
            }
            next = m.group(groups[groupIndex++]);
        }
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
