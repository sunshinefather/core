package com.palmlink.core.platform.web.tag;

import java.net.URLDecoder;
import org.springframework.util.StringUtils;
import com.palmlink.core.platform.web.cookie.CookieContext;
import com.palmlink.core.platform.web.session.SessionContext;

public class LoginStatusTag  extends TagSupport {
    
    private String stytle;  //cookie/session

    public String getStytle() {
        return stytle;
    }

    public void setStytle(String stytle) {
        this.stytle = stytle;
    }

    @Override
    protected int startTag() throws Exception {
        SessionContext sessionContext = (SessionContext) ctx.getBean("sessionContext");
        CookieContext cookieContext = (CookieContext) ctx.getBean("cookieContext");

        String loginUser = "cookie".equals(getStytle()) ? "Guest" : "，请重新登录";
        
        if ("cookie".equals(getStytle())) {
            Boolean loggedIn = cookieContext.getCookie(authSettings.getKeyForLoginedIn());
            
            
            String cookieUser = cookieContext.getCookie(authSettings.getKeyForLoginedUser());
            
            loginUser = StringUtils.hasText(cookieUser) ? cookieUser : "Guest";
 
            /*先验证session*/
            if (Boolean.TRUE.equals(loggedIn) && StringUtils.hasText(loginUser)) {
                loginUser = URLDecoder.decode(loginUser, "UTF-8").replace('.', ' ');
                request.setAttribute(authSettings.getKeyForLoginedIn().name(),  Boolean.TRUE);
                pageContext.getOut().print(loginUser);
                return SKIP_BODY;
            }
        } else {
            Boolean loggedIn = sessionContext.get(authSettings.getKeyForLoginedIn());
            loginUser = sessionContext.get(authSettings.getKeyForLoginedUser());
            if (Boolean.TRUE.equals(loggedIn)  && StringUtils.hasText(loginUser)) {
                request.setAttribute(authSettings.getKeyForLoginedIn().name(),  Boolean.TRUE);
                pageContext.getOut().print(loginUser);
                return SKIP_BODY;
            } 
        }
        
        request.setAttribute(authSettings.getKeyForLoginedIn().name(), Boolean.FALSE);
        pageContext.getOut().print(loginUser);
        return SKIP_BODY;
    }
}