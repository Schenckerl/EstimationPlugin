package org.catrobat.estimationplugin.admin;

import com.atlassian.templaterenderer.TemplateRenderer;

import javax.servlet.http.HttpServlet;
import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
/**
 * Created by Dominik on 19.04.2016.
 */
public class InvalidInputServlet extends HttpServlet {

    private final TemplateRenderer renderer;

    public InvalidInputServlet(TemplateRenderer templateRenderer) {
        renderer = templateRenderer;
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        renderer.render("invalidInput.vm",response.getWriter());
    }
}
