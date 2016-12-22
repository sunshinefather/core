package com.palmlink.core.platform;

import org.springframework.context.annotation.Bean;

import com.palmlink.core.platform.web.DeploymentSettings;
import com.palmlink.core.platform.web.site.LockSettings;
import com.palmlink.core.platform.web.site.SiteSettings;
import com.palmlink.core.platform.web.site.scheme.HTTPSchemeEnforceInterceptor;
import com.palmlink.core.platform.web.site.sso.SsoSettings;

public class DefaultSiteWebConfig extends DefaultWebConfig {

    @Bean
    public DeploymentSettings deploymentSettings() {
        return new DeploymentSettings();
    }

    @Bean
    public SiteSettings siteSettings() {
        return new SiteSettings();
    }

    @Bean
    public LockSettings lockSettings() {
        return new LockSettings();
    }

    // @Bean
    // public TransactionSettings transactionSettings() {
    // return new TransactionSettings();
    // }

    @Bean
    public SsoSettings ssoSettings() {
        return new SsoSettings();
    }

    // @Bean
    // @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode =
    // ScopedProxyMode.TARGET_CLASS)
    // public CookieContext cookieContext() {
    // return new CookieContext();
    // }
    //
    // @Bean
    // @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode =
    // ScopedProxyMode.TARGET_CLASS)
    // public RequestContext requestContext() {
    // return new RequestContext();
    // }

//    @Bean
//    @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
//    public SessionContext sessionContext() {
//        return new SessionContext();
//    }

    // @Bean
    // @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode =
    // ScopedProxyMode.TARGET_CLASS)
    // public SecureSessionContext secureSessionContext() {
    // return new SecureSessionContext();
    // }

    // @Bean
    // public CookieInterceptor cookieInterceptor() {
    // return new CookieInterceptor();
    // }
    //
    // @Bean
    // public RequestContextInterceptor requestContextInterceptor() {
    // return new RequestContextInterceptor();
    // }
    //
    // @Bean
    // public SessionInterceptor sessionInterceptor() {
    // return new SessionInterceptor();
    // }

    @Bean
    public HTTPSchemeEnforceInterceptor httpSchemeEnforceInterceptor() {
        return new HTTPSchemeEnforceInterceptor();
    }
}
