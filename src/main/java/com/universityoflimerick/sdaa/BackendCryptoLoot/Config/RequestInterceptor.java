package com.universityoflimerick.sdaa.BackendCryptoLoot.Config;

public interface RequestInterceptor {
    public void onPreMarshalRequest(ContextObject context);
//    public void onPostMarshalRequest(HttpServletRequest context);
}
