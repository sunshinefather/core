package com.palmlink.core.template;

import com.palmlink.core.util.AssertUtils;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.ObjectWrapper;
import freemarker.template.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

/**
 * @author Shihai.Fu
 */
public class FreemarkerTemplate {
    private static final String TEMPLATE_NAME = "template";
    private final Logger logger = LoggerFactory.getLogger(FreemarkerTemplate.class);

    Configuration configuration;
    String templateSource;

    Configuration getConfiguration() {
        if (configuration == null) {
            configuration = createConfiguration();
        }
        return configuration;
    }

    // for multiple thread condition, we may want to explicitly init in advance
    public void initialize() {
        getConfiguration();
    }

    private Configuration createConfiguration() {
        Configuration config = new Configuration();

        StringTemplateLoader loader = new StringTemplateLoader();
        loader.putTemplate(TEMPLATE_NAME, templateSource);

        config.setTemplateLoader(loader);
        config.setLocalizedLookup(false);
        config.setObjectWrapper(ObjectWrapper.DEFAULT_WRAPPER);
        return config;
    }

    public String transform(Map<String, Object> context) {
        AssertUtils.assertHasText(templateSource, "templateSource cannot be empty");
        logger.debug("transform by freemarker template, template={}", templateSource);

        try {
            Configuration configuration = getConfiguration();
            Template template = configuration.getTemplate(TEMPLATE_NAME);
            StringWriter writer = new StringWriter();
            template.process(context, writer);
            return writer.toString();
        } catch (IOException e) {
            throw new TemplateException(e);
        } catch (freemarker.template.TemplateException e) {
            throw new TemplateException(e);
        }
    }

    public void setTemplateSource(String templateSource) {
        this.templateSource = templateSource;
    }
}
