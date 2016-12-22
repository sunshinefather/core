package com.palmlink.core.json;

import java.io.IOException;
import java.text.SimpleDateFormat;
import org.codehaus.jackson.annotate.JsonAutoDetect.Visibility;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.xc.JaxbAnnotationIntrospector;
import com.palmlink.core.util.Convert;
import com.palmlink.core.util.RuntimeIOException;

/**
 * @author Shihai.Fu
 */
public final class JSONBinder<T> {
    public static <T> JSONBinder<T> binder(Class<T> beanClass, Class<?>... elementClasses) {
        return new JSONBinder<T>(beanClass, elementClasses);
    }

    public static ObjectMapper createMapper() {
        ObjectMapper mapper = new ObjectMapper();
        SimpleDateFormat dateFormat = new SimpleDateFormat(Convert.DATE_FORMAT_DATETIME);
        mapper.setVisibility(JsonMethod.FIELD, Visibility.ANY);
        mapper.setSerializationConfig(mapper.getSerializationConfig().withDateFormat(dateFormat));
        mapper.setDeserializationConfig(mapper.getDeserializationConfig().withDateFormat(dateFormat));
        mapper.setAnnotationIntrospector(new JaxbAnnotationIntrospector());
        mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper;
    }

    private final Class<T> beanClass;

    private final Class<?>[] elementClasses;

    private JSONBinder(Class<T> beanClass, Class<?>... elementClasses) {
        this.beanClass = beanClass;
        this.elementClasses = elementClasses;
    }

    public T fromJSON(String json) {
        ObjectMapper mapper = createMapper();
        try {
            return elementClasses == null || elementClasses.length == 0 ? mapper.readValue(json, beanClass) : fromJSONToGeneric(json);
        } catch (IOException e) {
            throw new RuntimeIOException(e);
        }
    }

    // add to support generic
    private T fromJSONToGeneric(String json) throws IOException {
        ObjectMapper mapper = createMapper();
        return mapper.readValue(json, mapper.getTypeFactory().constructParametricType(beanClass, elementClasses));
    }

    public String toJSON(T object) {
        ObjectMapper mapper = createMapper();
        try {
            return mapper.writeValueAsString(object);
        } catch (IOException e) {
            throw new RuntimeIOException(e);
        }
    }
}
