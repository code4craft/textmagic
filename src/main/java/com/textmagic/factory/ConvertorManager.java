package com.textmagic.factory;

import java.util.LinkedHashMap;
import java.util.Map;

import com.textmagic.config.ExtractorConfig;
import com.textmagic.extractor.Extractor;

/**
 * @author cairne
 * @date 2012-7-23
 */
public class ConvertorManager {

    private static final ConvertorManager INSTANCE = new ConvertorManager();

    private Map<String, ExtractorConfigConvertor> convertors;

    public static ConvertorManager instance() {
        return INSTANCE;
    }

    public ConvertorManager() {
        convertors = new LinkedHashMap<>();
        convertors.put(DomAttributeExtractorConvertor.instance().getKey(),
                DomAttributeExtractorConvertor.instance());
    }

    public ExtractorConfigConvertor get(ExtractorConfig config) {
        return convertors.get(config.getType());
    }

    public ExtractorConfigConvertor get(Extractor extractor) {
        for (ExtractorConfigConvertor convertor : convertors.values()) {
            if (convertor.canHandle(extractor)) {
                return convertor;
            }
        }
        return null;
    }
}
