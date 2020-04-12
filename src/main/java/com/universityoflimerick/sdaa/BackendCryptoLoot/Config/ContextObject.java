package com.universityoflimerick.sdaa.BackendCryptoLoot.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Date;

public class ContextObject {
    String serverInfo = "";
    String host = "";
    int port;
    Clock clock;
    Date serverStartUpTime;
    LocalDateTime requestTime;
    String contextId = "";
    String displayName = "";
    String user = "";
    boolean authenticated;
    String headers = "";

    /**
     * ContextObject provides information about server and specific request
     * instantiated per requeset
     * @param serverInfo contains info about server
     */
    public ContextObject(String serverInfo) {
        this.serverInfo = serverInfo;
        user = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        clock = Clock.systemUTC();
        requestTime = LocalDateTime.now(clock);
        this.host = "localhost:";
        this.port = 8080;
        serverStartUpTime = new Date(ApplicationContextProvider.getApplicationContext().getStartupDate());
        contextId = ApplicationContextProvider.getApplicationContext().getId();
        displayName = ApplicationContextProvider.getApplicationContext().getDisplayName().replace("org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext@","");
        authenticated = SecurityContextHolder.getContext().getAuthentication().isAuthenticated();
    }

    public String getServerInfo() {
        return serverInfo;
    }

    public String getHeaders() {
        return headers;
    }

    public void setHeaders(String headers) {
        this.headers = headers;
    }

    public String getUser() {
        return user;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public String getHost () {
        return this.host;
    }
    public void setHost(String host){
        this.host = host;
    }

    public int getPort (){
        return this.port;
    }
    public void setPort (int newPort) {
        this.port = newPort;
    }

    public LocalDateTime getRequestTime() {
        return requestTime;
    }

    public Date getStartUpTime() {
        return serverStartUpTime;
    }

    public String getContextId() {
        return contextId;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return "localTime=" + requestTime + '\''+
                "host=" + host + '\'' +
                ", port=" + port +
                ", startUpTime=" + serverStartUpTime +
                ", contextId='" + contextId + '\'' +
                ", displayName='" + displayName + '\'' +
                '}';
    }
}