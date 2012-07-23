package com.textmagic.factory;

import java.util.ArrayList;
import java.util.List;

import com.textmagic.config.ExtractorConfig;
import com.textmagic.config.MultiExtractorConfig;
import com.textmagic.extractor.CombinationExtractor;
import com.textmagic.extractor.Extractor;

/**
 * @author cairne
 * @date 2012-7-23
 */
public class CombinationExtractorConvertor implements ExtractorConfigConvertor {

    private final static String KEY = "Combination";

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
        if (!(extractor instanceof CombinationExtractor)) {
            throw new IllegalArgumentException("invalid extractor type");
        }
        CombinationExtractor combinationExtractor = (CombinationExtractor) extractor;
        List<ExtractorConfig> extractorConfigs = new ArrayList<>();
        for (Extractor innerExtractor : combinationExtractor.getExtractors()) {
            extractorConfigs.add(ConvertorManager.instance().get(innerExtractor)
                    .toConfig(innerExtractor));
        }
        return new MultiExtractorConfig(KEY, extractorConfigs);
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
