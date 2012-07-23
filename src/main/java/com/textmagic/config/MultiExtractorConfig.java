package com.textmagic.config;

import java.util.List;


public class MultiExtractorConfig extends ExtractorConfig {

    private List<ExtractorConfig> extractorConfigs;

    /**
     * 
     */
    public MultiExtractorConfig() {
        super();
    }

    /**
     * @param type
     * @param param
     */
    public MultiExtractorConfig(String type, List<ExtractorConfig> extractorConfigs) {
        this.type = type;
        this.extractorConfigs = extractorConfigs;
    }

    @Override
    public String toString() {
        return "MultiExtractorConfig [extractorConfigs=" + extractorConfigs + ", type=" + type
                + ", param=" + param + "]";
    }

    public List<ExtractorConfig> getExtractorConfigs() {
        return extractorConfigs;
    }

    public void setExtractorConfigs(List<ExtractorConfig> extractorConfigs) {
        this.extractorConfigs = extractorConfigs;
    }

}
