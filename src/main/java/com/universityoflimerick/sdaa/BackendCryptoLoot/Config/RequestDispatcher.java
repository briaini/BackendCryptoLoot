package com.universityoflimerick.sdaa.BackendCryptoLoot.Config;

import java.util.Vector;

/**
 * RequestDispatcher composed of container holding all concrete interceptors
 * calls hook methods of interceptors upon event, request dispatcher notified by framework
 * implements interceptor in order to ensure method signature matches those of hook methods of interceptors
 */
public class RequestDispatcher implements RequestInterceptor {
    private static RequestDispatcher singletonInstance = new RequestDispatcher();
    static Vector<RequestInterceptor> myInterceptors;

    //initialize vector
    private RequestDispatcher(){
        myInterceptors = new Vector<>();
    }

    public static RequestDispatcher getRequestDispatcher() {
        return singletonInstance;
    }

    //register concrete interceptor
    synchronized public void registerRequestInterceptor(RequestInterceptor i) {
        myInterceptors.addElement (i);
    }
    synchronized public void removeRequestInterceptor(RequestInterceptor i) {
        myInterceptors.removeElement (i);
    }

    //Calls hook method of all concrete interceptors in FIFO order, passing context object
    //Method signatures matches hook methods of interceptors for simplification
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
