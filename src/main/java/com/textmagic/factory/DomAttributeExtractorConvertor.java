package com.textmagic.factory;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.textmagic.config.ExtractorConfig;
import com.textmagic.extractor.DomAttributeExtractor;
import com.textmagic.extractor.Extractor;

/**
 * @author cairne
 * @date 2012-7-23
 */
public class DomAttributeExtractorConvertor implements ExtractorConfigConvertor {

    private final static String KEY = "Dom";

    private final static String TAG_KEY = "Tag";

    private final static DomAttributeExtractorConvertor instance = new DomAttributeExtractorConvertor();

    public static DomAttributeExtractorConvertor instance() {
        return instance;
    }

    /**
     * @return
     */
    @Override
    public String getKey() {
        return KEY;
    }

    /**
     * @param config
     * @return
     */
    @Override
    public DomAttributeExtractor fromConfig(ExtractorConfig config) {
        String tag = config.getParam().get(TAG_KEY);
        Map<String, String> attributes = new HashMap<>();
        for (Entry<String, String> entry : config.getParam().entrySet()) {
            if (!TAG_KEY.equals(entry.getKey())) {
                attributes.put(entry.getKey(), entry.getValue());
            }
        }
        return new DomAttributeExtractor(tag, attributes);
    }

    /**
     * @param extractor
     * @return
     */
    @Override
    public ExtractorConfig toConfig(Extractor extractor) {
        if (!(extractor instanceof DomAttributeExtractor)) {
            throw new IllegalArgumentException("invalid extractor class");
        }
        DomAttributeExtractor domAttributeExtractor = (DomAttributeExtractor) extractor;
        ExtractorConfig extractorConfig = new ExtractorConfig();
        extractorConfig.setType(KEY);
        Map<String, String> param = new HashMap<>();
        param.put(TAG_KEY, domAttributeExtractor.getTag());
        for (Entry<String, String> attribute : domAttributeExtractor.getAttributes().entrySet()) {
            param.put(attribute.getKey(), attribute.getValue());
        }
        extractorConfig.setParam(param);
        return extractorConfig;
    }

    public DomAttributeExtractor configFromHtml(String html) {
        html = html.replaceAll("[<>]", "");
        String[] s = html.split("\\s");
        for (int i = 0; i < s.length; i++) {
            s[i] = s[i].replace("\\s", " ");
        }
        if (s.length < 1) {
            throw new IllegalArgumentException("参数错误");
        }
        String tag = s[0];
        Map<String, String> attributes = new HashMap<>();
        for (int i = 1; i < s.length; i++) {
            String[] s1 = s[i].split("=");
            if (s1.length != 2) {
                throw new IllegalArgumentException("属性使用=分开");
            }
            attributes.put(s1[0], s1[1].replaceAll("[\"\']", ""));
        }
        return new DomAttributeExtractor(tag, attributes);
    }

    /**
     * @param extractor
     * @return
     */
    @Override
    public boolean canHandle(Extractor extractor) {
        return extractor instanceof DomAttributeExtractor;
    }

}
