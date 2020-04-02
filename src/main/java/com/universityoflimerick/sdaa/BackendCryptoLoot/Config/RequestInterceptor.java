package com.universityoflimerick.sdaa.BackendCryptoLoot.Config;

import javax.servlet.http.HttpServletRequest;

public interface RequestInterceptor {
    public void onPreMarshalRequest(HttpServletRequest context);
//    public void onPostMarshalRequest(HttpServletRequest context);
}
