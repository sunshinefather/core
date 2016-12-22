package com.palmlink.core.log;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Shihai.Fu
 */
public class LogFilter implements Filter {
    
    private final Logger logger = LoggerFactory.getLogger(LogFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {   
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            TraceLogger.get().initialize();
            HttpServletRequest httpRequest = logRequest((HttpServletRequest) request);
            
//            HttpSession httpSession = httpRequest.getSession();
//            httpSession.setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, Locale.ENGLISH);
//            httpSession.setAttribute("lang", Locale.ENGLISH.getLanguage());
            chain.doFilter(httpRequest, response);
        } finally {
            TraceLogger.get().clear();
        }
    }

    private HttpServletRequest logRequest(HttpServletRequest request) throws IOException {
        logger.debug("requestURL={}", request.getRequestURL());
        logHeaders(request);
        logParameters(request);
        logger.debug("remoteAddress={}", request.getRemoteAddr());

        if (logBody(request)) {
            RequestWrapper requestWrapper = new RequestWrapper(request);
            logger.debug("body={}", new String(requestWrapper.getBody(), requestWrapper.getCharacterEncoding()));
            return requestWrapper;
        } else {
            return request;
        }
    }

    private void logHeaders(HttpServletRequest request) {
        Enumeration headers = request.getHeaderNames();
        while (headers.hasMoreElements()) {
            String headerName = (String) headers.nextElement();
            logger.debug("[header] {}={}", headerName, request.getHeader(headerName));
        }
    }

    private void logParameters(HttpServletRequest request) {
        Enumeration paramNames = request.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = (String) paramNames.nextElement();
            logger.debug("[param] {}={}", paramName, request.getParameter(paramName));
        }
    }

    private boolean logBody(HttpServletRequest request) {
        String method = request.getMethod().toLowerCase();
        if ("post".equals(method) || "put".equals(method)) {
            String contentType = request.getContentType();
            return !isMultipart(contentType);
        }

        return false;
    }

    private boolean isMultipart(String contentType) {
        return contentType != null && contentType.toLowerCase().startsWith("multipart/");
    }

    @Override
    public void destroy() {
    }
}
