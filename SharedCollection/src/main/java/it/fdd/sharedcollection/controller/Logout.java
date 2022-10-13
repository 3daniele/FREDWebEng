package it.fdd.sharedcollection.controller;

import it.fdd.framework.result.FailureResult;
import it.fdd.framework.security.SecurityLayer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class Logout extends SharedCollectionBaseController {
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            action_logout(request, response);
        } catch (IOException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);
        }
    }

    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        if (request.getAttribute("exception") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        } else {
            (new FailureResult(getServletContext())).activate((String) request.getAttribute("message"), request, response);
        }
    }

    private void action_logout(HttpServletRequest request, HttpServletResponse response) throws IOException {

        SecurityLayer.disposeSession(request);
        //se è stato trasmesso un URL di origine, torniamo a quell'indirizzo
        //if an origin URL has been transmitted, return to it
        if (request.getParameter("referrer") != null) {
            response.sendRedirect(request.getParameter("referrer"));
        } else {
            response.sendRedirect("home");
        }
    }
}
