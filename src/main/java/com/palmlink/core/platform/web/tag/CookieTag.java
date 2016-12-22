package com.palmlink.core.platform.web.tag;

import org.apache.commons.lang.StringUtils;

import com.palmlink.core.collection.Key;
import com.palmlink.core.platform.web.cookie.CookieContext;

public class CookieTag extends TagSupport {

    private String key;

    @Override
    protected int startTag() throws Exception {
        if (StringUtils.isBlank(key))
            return SKIP_BODY;
        CookieContext cookieContext = ctx.getBean(CookieContext.class);
        Object context = cookieContext.getCookie(Key.key(key, Object.class));
        pageContext.getOut().print(context == null ? "" : context);
        return SKIP_BODY;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
