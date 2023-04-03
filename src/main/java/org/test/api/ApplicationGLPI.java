package org.test.api;

import java.io.IOException;


public class ApplicationGLPI {
    public static void main(String[] args) throws IOException {
        GlpiApiCall glpiApiCall = new GlpiApiCall();

        System.out.println("**************** Token de session ****************");
        String sessionToken = glpiApiCall.getSessionToken();
        System.out.println("Token de session : " + sessionToken + "\n");

        System.out.println("**************** Conf GLPI - asset types ****************");
        glpiApiCall.getGlpiAssetTypes(sessionToken);

        System.out.println("**************** Ajout PC ****************");
        glpiApiCall.addComputer(sessionToken);

        System.out.println("**************** Suppression PC ****************");
        glpiApiCall.removeComputer(sessionToken);

        System.out.println("**************** creation d'un ticket ****************");
        Ticket tic = new Ticket("titre ticket pamela", "ceci est la description", 1, 2);
        glpiApiCall.createTicket(sessionToken, tic);
    }
}
