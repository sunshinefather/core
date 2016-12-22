package com.palmlink.core.platform.web.session;

import java.io.IOException;
import java.util.UUID;
import javax.inject.Inject;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.GenericFilterBean;
import com.palmlink.core.platform.web.session.provider.SessionProvider;
import com.palmlink.core.platform.web.site.SiteSettings;
import com.palmlink.core.platform.web.site.sso.SsoSettings;
import com.palmlink.core.util.AssertUtils;

public class SessionFilter extends GenericFilterBean {

    private final Logger logger = LoggerFactory.getLogger(SessionFilter.class);
    private static final String ATTRIBUTE_CONTEXT_INITIALIZED = SessionFilter.class.getName() + ".CONTEXT_INITIALIZED";
    private static final String SESSION_COOKIE_ID = "SESSION_ID";
    public int cookieMaxAge =  -1;


    @Inject
    public SessionContext sessionContext;

    @Inject
    public SiteSettings siteSettings;

    @Inject
    public SessionProvider sessionProvider;

    @Inject
    public SsoSettings ssoSettings;

    @Override
    public void initFilterBean() throws ServletException {
        AssertUtils.assertHasText(siteSettings.getRemoteSessionServers(), "remote session server configuration is missing");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        before(req);
        chain.doFilter(req, resp);
        after(resp);
    }

    private void before(HttpServletRequest request) {

        Boolean initialized = (Boolean) request.getAttribute(ATTRIBUTE_CONTEXT_INITIALIZED);
        
        if (!Boolean.TRUE.equals(initialized)) {
            loadSession(request);
            request.setAttribute(ATTRIBUTE_CONTEXT_INITIALIZED, Boolean.TRUE);
        }

    }

    private void after(HttpServletResponse response) {
        saveAllSessions(response);
    }

    private void saveAllSessions(HttpServletResponse response) {
        if (sessionContext.changed()) {
            if (sessionContext.invalidated()) {
                deleteSession(response);
            } else {
                persistSession(response);
            }
            sessionContext.saved();
        }
    }

    private void loadSession(HttpServletRequest request) {
        String sessionId = obtainSessionId(request);
        if (sessionId !=null) {
            String sessionData = sessionProvider.getAndRefreshSession(sessionId);
            if (sessionData != null) {
                sessionContext.setId(sessionId);
                sessionContext.loadSessionData(sessionData);
                integrationFramework(request);
            } else {
                logger.debug("can not find session, generate new sessionId to replace old one");
                sessionContext.requireNewSessionId();
            }
        }
    }
    
    public void integrationFramework(HttpServletRequest request) {
        logger.debug("combine  with custom other frameworks");
    }

    private String obtainSessionId(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String cookid="";
        if (null != cookies && cookies.length>0){
            for (Cookie cookie : cookies){
                if (cookie.getName().equals(SESSION_COOKIE_ID)){
                    cookid=cookie.getValue();
                    break;
                }
            } 
        }
        return cookid;
    }

    public void deleteSession(HttpServletResponse response) {
        String sessionId = sessionContext.getId();
        if (sessionId == null) {
            return;
        }
        sessionProvider.clearSession(sessionId);
        Cookie cookie = new Cookie(SESSION_COOKIE_ID, null);
        cookie.setMaxAge(0);
        cookie.setPath(ssoSettings.getCookiePath());
        cookie.setDomain(ssoSettings.getCookieDomain());
        response.addCookie(cookie);
    }

    public void persistSession(HttpServletResponse response) {
        String sessionId = sessionContext.getId();
        if (sessionId == null) {
            sessionId = UUID.randomUUID().toString();
            sessionContext.setId(sessionId);
            response.addCookie(buildSessionCookie(sessionId, cookieMaxAge));
        }
        sessionProvider.saveSession(sessionId, sessionContext.getSessionData());
    }
    
    public Cookie buildSessionCookie(String sessionId, int maxAge) {
        Cookie cookie = new Cookie(SESSION_COOKIE_ID, sessionId);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(maxAge);
        if (!StringUtils.isBlank(ssoSettings.getCookieDomain()))
            cookie.setDomain(ssoSettings.getCookieDomain());
        cookie.setPath(ssoSettings.getCookiePath());
        return cookie;
    }

    public int getCookieMaxAge() {
        return cookieMaxAge;
    }

    public void setCookieMaxAge(int cookieMaxAge) {
        this.cookieMaxAge = cookieMaxAge;
    }
}