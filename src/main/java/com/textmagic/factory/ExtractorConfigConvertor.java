package com.textmagic.factory;

import com.textmagic.config.ExtractorConfig;
import com.textmagic.extractor.Extractor;

/**
 * @author cairne
 * @date 2012-7-23
 */
interface ExtractorConfigConvertor {

    public Extractor fromConfig(ExtractorConfig config);

    public ExtractorConfig toConfig(Extractor extractor);

    public String getKey();

    public boolean canHandle(Extractor extractor);

}
