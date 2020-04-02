package com.universityoflimerick.sdaa.BackendCryptoLoot.Filters;

import com.universityoflimerick.sdaa.BackendCryptoLoot.BackendCryptoLootApplication;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MyInterceptorFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        BackendCryptoLootApplication.rd.onPreMarshalRequest(httpServletRequest);
    }
}
