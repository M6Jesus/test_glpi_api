package org.test.api;

import com.gargoylesoftware.htmlunit.*;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * COPYRIGHT MATHIEU MULOT
 * @author Mathieu MULOT
 */
public class GlpiApiCall implements GlpiOperations{
    private static URL urlBase;

    static {
        try {
            urlBase = new URL("http://localhost/glpi_10_0_6/apirest.php");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public WebResponse sendHttpRequest (URL url, HttpMethod httpMethod, String appToken,
                                        String userToken, String sessionToken,
                                        String body, String contentType,
                                        FormEncodingType formEncodingType,
                                        String accept, int responseCode,
                                        String httpMethodToAllow)
                                        throws IOException {

        WebClient webClient = new WebClient(BrowserVersion.BEST_SUPPORTED);
        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setRedirectEnabled(true);
        webClient.getOptions().setCssEnabled(true);
        webClient.getOptions().setDoNotTrackEnabled(true);
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(true);

        WebRequest request = new WebRequest(url, httpMethod);

        if (formEncodingType!=null) {
            request.setEncodingType(formEncodingType);
        }
        request.setHttpMethod(httpMethod);

        webClient.addRequestHeader("App-Token",appToken);
        webClient.addRequestHeader("Authorization","user_token " + userToken);
        
        if (sessionToken!=null && !sessionToken.isBlank()) {
            webClient.addRequestHeader("Session-Token", sessionToken);
        }
        
        webClient.addRequestHeader("access-control-allow-origin","*");
        webClient.addRequestHeader("cache-control","no-store, no-cache, must-revalidate");
        webClient.addRequestHeader("connection","keep-alive");
        webClient.addRequestHeader("pragma","no-cache");

        if (accept!=null && !accept.isBlank()) {
            webClient.addRequestHeader("accept",accept);
        }
        if (contentType!=null && !contentType.isBlank()) {
            webClient.addRequestHeader("content-Type",contentType);
        }

        if (body!=null && !body.isBlank()) {
            request.setRequestBody(body);
        }
        System.out.println("******le params de la requetes request.getRequestParameters " + request.getRequestParameters());
        System.out.println("******le params de la requetes request.getParameters() " + request.getParameters());
        System.out.println("******le params de la requetes request.getRequestBody() " + request.getRequestBody());
        System.out.println("****** le request() " + request);

        Page page= webClient.getPage(request);
        WebResponse response = page.getWebResponse();

        System.out.println("******le response.getContentAsString() " + response.getContentAsString());

        if (response.getStatusCode() != responseCode) {
            throw new IOException("Failed : HTTP error code : " + response.getStatusCode());
        }
        webClient.close();

        return response;
    }

    public JSONObject getJsonObject(WebResponse response) {

        String output = response.getContentAsString();
        System.out.println(output);
        JSONObject jsonObject = new JSONObject(output);
        return jsonObject;
    }


    public String getSessionToken() throws IOException {

        URL url = new URL("http://localhost/glpi_10_0_6/apirest.php/initSession");
        WebResponse response = sendHttpRequest(url, HttpMethod.GET,
                "9dCPK2HR6Hck118pXe9Kz6xHfh9vuVmF8NxRRMZW",
                "pV0aKS5EZ9Jfp2gsjlj461xSWS6bSAqscc6fOjXn",//"ldCBa7mdyke7xdwEF7D68SLRuxjPIGkMacFwvy1O",
                null, null, null, null,
                "application/json",
                HttpURLConnection.HTTP_OK, null);

        // lecture du flux JSON
        JSONObject jsonObject = getJsonObject(response);
        String sessionToken = jsonObject.getString("session_token");
        return sessionToken;
    }

    public void getGlpiAssetTypes(String sessionToken) throws IOException {

        URL url = new URL("http://localhost/glpi_10_0_6/apirest.php/getGlpiConfig");

        WebResponse response = sendHttpRequest(url, HttpMethod.GET,
                "9dCPK2HR6Hck118pXe9Kz6xHfh9vuVmF8NxRRMZW",
                "ldCBa7mdyke7xdwEF7D68SLRuxjPIGkMacFwvy1O",
                sessionToken, null, null,
                null, "application/json",
                HttpURLConnection.HTTP_OK, null);

        JSONObject jsonObject = getJsonObject(response);
        JSONArray assetTypes = jsonObject.getJSONObject("cfg_glpi").getJSONArray("asset_types");

        System.out.println("\nRésultat parsing config GLPI, asset types :\n");

        int nbAssetTypes = assetTypes.length();

        for (int i = 0; i < nbAssetTypes; i++) {
            String assetType = assetTypes.getString(i);
            System.out.println(assetType);
        }
    }

    public void addComputer(String sessionToken) throws IOException {

        URL url = new URL("http://localhost/glpi_10_0_6/apirest.php/Computer");

        WebResponse response = sendHttpRequest(url, HttpMethod.POST,
                "9dCPK2HR6Hck118pXe9Kz6xHfh9vuVmF8NxRRMZW",
                "ldCBa7mdyke7xdwEF7D68SLRuxjPIGkMacFwvy1O", sessionToken,
                "{\"input\": {\"name\": \"pc5\", \"serial\": \"555\"}}",
                "application/json", null, null,
                HttpURLConnection.HTTP_CREATED, null);

        System.out.println(response.getContentAsString());
    }

    public void removeComputer(String sessionToken) throws IOException {

        URL url = new URL("http://localhost/glpi_10_0_6/apirest.php/Computer/12?force_purge=true");

        WebResponse response = sendHttpRequest(url, HttpMethod.DELETE,
                "9dCPK2HR6Hck118pXe9Kz6xHfh9vuVmF8NxRRMZW",
                "ldCBa7mdyke7xdwEF7D68SLRuxjPIGkMacFwvy1O",
                sessionToken, null, null, null, null,
                HttpURLConnection.HTTP_OK, null);

        System.out.println(response.getContentAsString());
    }

    public WebResponse createTicket(String sessionToken, Ticket ticket) throws IOException {
        URL url = new URL("http://localhost/glpi_10_0_6/apirest.php/Ticket/");
          WebResponse response = sendHttpRequest(url, HttpMethod.POST,
                "9dCPK2HR6Hck118pXe9Kz6xHfh9vuVmF8NxRRMZW",
                "ldCBa7mdyke7xdwEF7D68SLRuxjPIGkMacFwvy1O", sessionToken,
                "{\"input\": {\"name\": \""+ ticket.titre +"\", \"content\": " +
                        "\""+ ticket.description+"\",\"status\":\""+ticket.status+"\"," +
                        "\"urgency\":\""+ticket.urgency+"\"}}",
                "application/json", null, null,
                HttpURLConnection.HTTP_CREATED, null);

        return response;
    }

    public WebResponse GetTicket(String sessionToken, String location) throws IOException {
        URL url = new URL(location);
        WebResponse response = sendHttpRequest(url, HttpMethod.GET,
                "9dCPK2HR6Hck118pXe9Kz6xHfh9vuVmF8NxRRMZW",
                "ldCBa7mdyke7xdwEF7D68SLRuxjPIGkMacFwvy1O",
                sessionToken, null, null,
                null, "application/json",
                HttpURLConnection.HTTP_OK, null);
        return response;
    }

    public WebResponse DeleteATicket(String sessionToken, int id) throws IOException {
        String urlTicket = urlBase + "/Ticket/" + id;
        URL url = new URL(urlTicket);

        WebResponse response = sendHttpRequest(url, HttpMethod.DELETE,
                "9dCPK2HR6Hck118pXe9Kz6xHfh9vuVmF8NxRRMZW",
                "ldCBa7mdyke7xdwEF7D68SLRuxjPIGkMacFwvy1O",
                sessionToken, null, null, null, null,
                HttpURLConnection.HTTP_OK, null);

        System.out.println(response.getContentAsString());
        return response;
    }

    public WebResponse getListTickets(String sessionToken) throws IOException {
        URL url = new URL("http://localhost/glpi_10_0_6/apirest.php/Ticket/");
        WebResponse response = sendHttpRequest(url, HttpMethod.GET,
                "9dCPK2HR6Hck118pXe9Kz6xHfh9vuVmF8NxRRMZW",
                "ldCBa7mdyke7xdwEF7D68SLRuxjPIGkMacFwvy1O",
                sessionToken, null, null,
                null, "application/json",
                HttpURLConnection.HTTP_OK, null);
        return response;
    }

    public void DeleteAllTicket(String sessionToken) throws IOException {
        WebResponse liste = getListTickets(sessionToken);
        List<Integer> tableauIdTickets = new ArrayList<>();
        String output = liste.getContentAsString();
        JSONArray listeTicket = new JSONArray(output);
        System.out.println("/n************ taille de la liste "+ listeTicket.length() + "/n");
        for (int i = 0; i < listeTicket.length(); i++) {
            JSONObject ticket = listeTicket.getJSONObject(i);
            int id = ticket.getInt("id");
            tableauIdTickets.add(id);
        }
        if (tableauIdTickets.size() != 0) {
            for (int i : tableauIdTickets) {
                DeleteATicket(sessionToken, i);
            }
        } else{
            System.out.println("rien a effacer! ");
        }
    }

    public String findJsonString(WebResponse response, String key){
        JSONObject jsonObject = getJsonObject(response);
        String find = jsonObject.getString(key);
        return find;
    }
    public int findJsonInt(WebResponse response, String key){
        JSONObject jsonObject = getJsonObject(response);
        int find = jsonObject.getInt(key);
        return find;
    }

    public int getIdTicket(WebResponse response) {
        int id = findJsonInt(response, "id");
        System.out.println("**** id ticket créer " + id);
        return id;
    }


}
