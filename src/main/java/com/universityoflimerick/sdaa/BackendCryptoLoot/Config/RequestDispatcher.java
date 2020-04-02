package com.universityoflimerick.sdaa.BackendCryptoLoot.Config;

import javax.servlet.http.HttpServletRequest;
import java.util.Vector;

public class RequestDispatcher implements RequestInterceptor {
    private static RequestDispatcher singletonInstance = new RequestDispatcher();
    static Vector myInterceptors;
    private RequestDispatcher(){
        myInterceptors = new Vector();
    }

    public static RequestDispatcher getRequestDispatcher() {
        return singletonInstance;
    }

    synchronized public void registerRequestInterceptor(RequestInterceptor i) {
        if(myInterceptors == null)
            System.out.println("interceptors_ is null");
        if(i == null)
            System.out.println("RequestInterceptor i is null");
        myInterceptors.addElement (i);
    }
    synchronized public void removeRequestInterceptor(RequestInterceptor i) {
        myInterceptors.removeElement (i);
    }

    public void onPreMarshalRequest(HttpServletRequest context) {
        Vector interceptors;
        synchronized (this) { // Clone vector.
            interceptors = (Vector) myInterceptors.clone();
        }
        for (int i = 0; i < interceptors.size(); ++i) {
            RequestInterceptor ic = (RequestInterceptor) interceptors.elementAt(i);
            // Dispatch callback hook method.
            ic.onPreMarshalRequest(context);
        }
    }

    //    public void dispatchClientRequestInterceptorPreMarshall(UnmarshalledRequest context) {
//        Vector interceptors;
//        synchronized (this) { // Clone vector.
//            interceptors = (Vector) interceptors_.clone();
//        }
//        for (int i = 0; i < interceptors.size(); ++i) {
//            RequestInterceptor ic = (RequestInterceptor) interceptors.elementAt(i);
//            // Dispatch callback hook method.
//            ic.onPreMarshalRequest(context);
//        }
//    }
    //...
 }