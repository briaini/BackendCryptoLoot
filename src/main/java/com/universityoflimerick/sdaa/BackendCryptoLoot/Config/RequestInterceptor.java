package com.universityoflimerick.sdaa.BackendCryptoLoot.Config;

//Abstract interceptor, concrete interceptor created in BackendCryptoLootApplication
public interface RequestInterceptor {
    public void onPreMarshalRequest(ContextObject context);
//    public void onPostMarshalRequest(HttpServletRequest context);
}
