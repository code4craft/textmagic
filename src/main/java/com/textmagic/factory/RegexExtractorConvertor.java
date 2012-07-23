package com.textmagic.factory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.textmagic.config.ExtractorConfig;
import com.textmagic.config.MultiExtractorConfig;
import com.textmagic.extractor.CombinationExtractor;
import com.textmagic.extractor.Extractor;
import com.textmagic.extractor.RegexExtractor;

/**
 * @author cairne
 * @date 2012-7-23
 */
public class RegexExtractorConvertor implements ExtractorConfigConvertor {

    private final static String KEY = "Regex";

    private final static String FIELD_PATTERN = "Pattern";

    /**
     * @param config
     * @return
     */
    @Override
    public Extractor fromConfig(ExtractorConfig config) {
        List<Extractor> extractors = new ArrayList<>();
        if (!(config instanceof MultiExtractorConfig)) {
            throw new IllegalArgumentException("invalid config, must be MultiExtractorConfig class");
        }
        MultiExtractorConfig multiExtractorConfig = (MultiExtractorConfig) config;
        for (ExtractorConfig extractorConfig : multiExtractorConfig.getExtractorConfigs()) {
            extractors.add(ConvertorManager.instance().get(extractorConfig)
                    .fromConfig(extractorConfig));
        }
        return new CombinationExtractor(extractors);
    }

    /**
     * @param extractor
     * @return
     */
    @Override
    public ExtractorConfig toConfig(Extractor extractor) {
        if (!(extractor instanceof RegexExtractor)) {
            throw new IllegalArgumentException("invalid extractor type");
        }
        RegexExtractor regexExtractor = (RegexExtractor) extractor;
        ExtractorConfig extractorConfig = new ExtractorConfig();
        extractorConfig.setType(KEY);
        Map<String, String> param = Collections.singletonMap(FIELD_PATTERN,
                regexExtractor.getPattern());
        extractorConfig.setParam(param);
        return extractorConfig;
    }

    /**
     * @return
     */
    @Override
    public String getKey() {
        return KEY;
    }

    /**
     * @param extractor
     * @return
     */
    @Override
    public boolean canHandle(Extractor extractor) {
        return extractor instanceof CombinationExtractor;
    }

}
