package com.universityoflimerick.sdaa.BackendCryptoLoot.Config;

import java.util.Vector;

public class RequestDispatcher implements RequestInterceptor {
    private static RequestDispatcher singletonInstance = new RequestDispatcher();
    static Vector<RequestInterceptor> myInterceptors;
    private RequestDispatcher(){
        myInterceptors = new Vector<>();
    }

    public static RequestDispatcher getRequestDispatcher() {
        return singletonInstance;
    }

    synchronized public void registerRequestInterceptor(RequestInterceptor i) {
        myInterceptors.addElement (i);
    }
    synchronized public void removeRequestInterceptor(RequestInterceptor i) {
        myInterceptors.removeElement (i);
    }

    public void onPreMarshalRequest(ContextObject context) {
        Vector<RequestInterceptor> interceptors;
        synchronized (this) {
            interceptors = (Vector) myInterceptors.clone();
        }
        for (int i = 0; i < interceptors.size(); ++i) {
            RequestInterceptor ic = interceptors.elementAt(i);
            ic.onPreMarshalRequest(context);
        }
    }
 }
