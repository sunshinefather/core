package com.palmlink.core.platform.context;

import com.palmlink.core.util.RuntimeIOException;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import java.io.IOException;
import java.util.Properties;

public class PropertyContext extends PropertySourcesPlaceholderConfigurer {
    public Properties getAllProperties() {
        try {
            return mergeProperties();
        } catch (IOException e) {
            throw new RuntimeIOException(e);
        }
    }
}
