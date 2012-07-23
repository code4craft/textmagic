package com.textmagic.config;

import java.util.Map;

public class ExtractorConfig {

    protected String type;

    protected Map<String, String> param;

    /**
     * 
     */
    public ExtractorConfig() {
        super();
    }

    /**
     * @param type
     * @param param
     */
    public ExtractorConfig(String type, Map<String, String> param) {
        super();
        this.type = type;
        this.param = param;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, String> getParam() {
        return param;
    }

    public void setParam(Map<String, String> param) {
        this.param = param;
    }

    @Override
    public String toString() {
        return "ExtractorConfig [type=" + type + ", param=" + param + "]";
    }

}
