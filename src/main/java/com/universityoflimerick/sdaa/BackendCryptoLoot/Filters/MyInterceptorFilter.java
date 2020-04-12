package com.universityoflimerick.sdaa.BackendCryptoLoot.Filters;


import com.universityoflimerick.sdaa.BackendCryptoLoot.BackendCryptoLootApplication;
import com.universityoflimerick.sdaa.BackendCryptoLoot.Config.ContextObject;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MyInterceptorFilter extends OncePerRequestFilter {
    /**
     * used to notify dispatcher of event
     * @param httpServletRequest
     * @param httpServletResponse
     * @param filterChain
     */
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) {
        ContextObject contextObj = new ContextObject(httpServletRequest.getSession().getServletContext().getServerInfo());
        contextObj.setHeaders(httpServletRequest.getHeader("Authorization"));

        BackendCryptoLootApplication.rd.onPreMarshalRequest(contextObj);

        try {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }
}
