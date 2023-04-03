package org.test.api;

import com.gargoylesoftware.htmlunit.FormEncodingType;
import com.gargoylesoftware.htmlunit.HttpMethod;
import com.gargoylesoftware.htmlunit.WebResponse;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

public interface GlpiOperations {
    String getSessionToken() throws IOException ;
    WebResponse sendHttpRequest(URL url, HttpMethod httpMethod, String appToken,
                                String userToken, String sessionToken,
                                String body, String contentType,
                                FormEncodingType formEncodingType,
                                String accept, int responseCode,
                                String httpMethodToAllow) throws IOException ;

    void getGlpiAssetTypes(String sessionToken) throws IOException ;
    void addComputer(String sessionToken) throws IOException ;
    void removeComputer(String sessionToken) throws IOException ;
    WebResponse createTicket(String sessionToken, Ticket ticket) throws IOException ;
    JSONObject getJsonObject(WebResponse response);


}
