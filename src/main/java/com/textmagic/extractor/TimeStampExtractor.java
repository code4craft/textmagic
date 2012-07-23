package com.textmagic.extractor;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.time.DateUtils;

import com.textmagic.config.ExtractorConfig;

public class TimeStampExtractor extends AbstractExtractor {

    public final static String KEY = "TimeStamp";

    private String[] dateFormat;

    private Locale locale;

    private String next;

    private boolean hasNext;

    public String getKey() {
        return KEY;
    }

    /**
     * @return
     */
    @Override
    public String toHumanFriendString() {
        return Arrays.asList(dateFormat).toString();
    }

    public final static String FIELD_FORMAT = "Format";

    public final static String FIELD_LOCALE = "Locale";

    /**
     * @param extractorConfig
     */
    @Override
    public void doConfig(ExtractorConfig extractorConfig) {
        List<String> list = extractorConfig.getParam().get(FIELD_FORMAT);
        if (CollectionUtils.isNotEmpty(list)) {
            dateFormat = new String[list.size()];
            for (int i = 0; i < list.size(); i++) {
                dateFormat[i] = list.get(i);
            }
        }
        list = extractorConfig.getParam().get(FIELD_LOCALE);
        if (CollectionUtils.isNotEmpty(list)) {
            if (list.size() >= 3) {
                locale = new Locale(list.get(0), list.get(1), list.get(2));
            } else if (list.size() == 2) {
                locale = new Locale(list.get(0), list.get(1), "");
            } else {
                locale = new Locale(list.get(0), "", "");
            }
        }
    }

    public static ExtractorConfig buildConfig(List<String> formats) {
        ExtractorConfig extractorConfig = new ExtractorConfig();
        extractorConfig.setType(KEY);
        Map<String, List<String>> param = Collections.singletonMap(FIELD_FORMAT, formats);
        extractorConfig.setParam(param);
        return extractorConfig;
    }

    public ExtractorConfig toConfig() {
        ExtractorConfig extractorConfig = new ExtractorConfig();
        extractorConfig.setType(KEY);
        Map<String, List<String>> param = new HashMap<>();
        param.put(FIELD_FORMAT, Arrays.asList(dateFormat));
        if (locale != null) {
            param.put(
                    FIELD_LOCALE,
                    com.diandian.framework.utils.CollectionUtils.<String> asList(
                            locale.getLanguage(), locale.getCountry(), locale.getVariant()));
        }
        extractorConfig.setParam(param);
        return extractorConfig;
    }

    public static ExtractorConfig buildConfig(String format) {
        return buildConfig(Collections.singletonList(format));
    }

    public TimeStampExtractor(String dateFormat) {
        super();
        this.dateFormat = new String[] { dateFormat };
    }

    /**
     * @param dateFormat
     */
    public TimeStampExtractor(String[] dateFormat) {
        super();
        this.dateFormat = dateFormat;
    }

    /**
     * @param html
     * @return
     */
    @Override
    public void init(String text) {
        if (text != null && dateFormat != null) {
            try {
                if (locale != null) {
                    next = String.valueOf(com.diandian.framework.utils.DateUtils.parseDate(text,
                            Arrays.asList(dateFormat), locale).getTime());
                } else {
                    next = String.valueOf(DateUtils.parseDate(text, dateFormat).getTime());
                }
                hasNext = true;
                return;
            } catch (ParseException e) {}
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
