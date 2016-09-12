package org.catrobat.estimationplugin.admin;

import org.catrobat.estimationplugin.services.ProjectService;
import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.project.Project;
import com.atlassian.jira.project.ProjectManager;
import com.atlassian.jira.util.json.JSONArray;
import com.atlassian.jira.util.json.JSONException;
import com.atlassian.jira.util.json.JSONObject;
import com.atlassian.webresource.api.assembler.PageBuilderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;

import com.atlassian.sal.api.auth.LoginUriProvider;
import com.atlassian.sal.api.user.UserManager;
import com.atlassian.templaterenderer.TemplateRenderer;

import java.util.*;

import com.atlassian.sal.api.pluginsettings.PluginSettings;
import com.atlassian.sal.api.pluginsettings.PluginSettingsFactory;

public class MyPluginServlet extends HttpServlet {
    private static final String PLUGIN_STORAGE_KEY = "org.catrobat.estimationplugin";
    private static final Logger log = LoggerFactory.getLogger(MyPluginServlet.class);
    private final UserManager userManager;
    private final LoginUriProvider loginUriProvider;
    private final TemplateRenderer templateRenderer;
    private final PluginSettingsFactory pluginSettingsFactory;
    private final PageBuilderService pageBuilderService;

    public MyPluginServlet(UserManager userManager,
                           LoginUriProvider loginUriProvider, TemplateRenderer templateRenderer, PluginSettingsFactory pluginSettingsFactory,
                            PageBuilderService pageBuilderservice) {
        this.userManager = userManager;
        this.loginUriProvider = loginUriProvider;
        this.templateRenderer = templateRenderer;
        this.pluginSettingsFactory = pluginSettingsFactory;
        this.pageBuilderService = pageBuilderservice;
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String username = userManager.getRemoteUsername(request);
        if (username == null || !userManager.isSystemAdmin(username)) {
            redirectToLogin(request, response);
            return;
        }

        pageBuilderService.assembler().resources().requireWebResource("catraobat.estimation:estimationplugin-resources");

        Map<String, Object> context = new HashMap<String, Object>();

        PluginSettings pluginSettings = pluginSettingsFactory.createGlobalSettings();

        response.setContentType("text/html;charset=utf-8");

        Collection<Project> projects = ComponentAccessor.getProjectManager().getProjectObjects();
        JSONObject projectsSugg = new JSONObject();
        try {
            projectsSugg.put("label", "All Projects");
            JSONArray projectList = new JSONArray();
            for (Project pr : projects) {
                JSONObject projectJSON = new JSONObject();
                projectJSON.put("label", pr.getName());
                projectJSON.put("value", pr.getId().toString());
                projectList.put(projectJSON);
            }
            projectsSugg.put("items", projectList);

            JSONArray projectwrapper = new JSONArray();
            projectwrapper.put(projectsSugg);
            context.put("projectSugg", projectwrapper);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        context.put("projects", projects);

        templateRenderer.render("admin.vm",context, response.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse response)
            throws ServletException, IOException {
        PluginSettings pluginSettings = pluginSettingsFactory.createGlobalSettings();

        boolean valid_input = true;

        Object selected_project = req.getParameter("pid");
        if(selected_project.toString().equals(""))
            valid_input = false;
        Object dueDate = req.getParameter("duedate");
        if(dueDate.toString().equals(""))
            valid_input = false;
        Object based_select = req.getParameter("based_select");
        if(based_select.toString().equals(""))
           valid_input = false;
        Object based_month = req.getParameter("based_month");
        if(based_month.toString().equals(""))
            valid_input = false;
        Object selected_method = req.getParameter("method-select");

        String[] selected_pools = req.getParameterValues("workflow-pools");

        String method = selected_method.toString().toLowerCase().replace(" ","");
        int programmers = Integer.parseInt(req.getParameter("programmers").toString());

        if(!valid_input) {
            response.sendError(404);
            //response.sendRedirect("invalidInput");
            return;
        }

//        ProjectManager manger = ComponentAccessor.getProjectManager();
//        String project_name = manger.getProjectObj((long)Integer.parseInt(selected_project.toString())).getName().toLowerCase();

       /* if(checkForExisstingSettings(project_name,method)) {
            response.sendError(888, "settings do already exist");
            return;
        }*/

        String project_name = ProjectService.getProjectFromId(Integer.parseInt(selected_project.toString()));

        SettingsObject new_settings = new SettingsObject(selected_project,programmers,based_select, selected_pools);

        pluginSettings.put(PLUGIN_STORAGE_KEY + "."+method+"." +project_name, new_settings.serialize());


        response.sendRedirect("admin");
    }

    private void redirectToLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect(loginUriProvider.getLoginUri(getUri(request)).toASCIIString());
    }

    private URI getUri(HttpServletRequest request) {
        StringBuffer builder = request.getRequestURL();
        if (request.getQueryString() != null) {
            builder.append("?");
            builder.append(request.getQueryString());
        }
        return URI.create(builder.toString());
    }

    private boolean checkForExisstingSettings(String project, String method)
    {
        PluginSettings pluginSettings = pluginSettingsFactory.createGlobalSettings();
        Object value = pluginSettings.get(PLUGIN_STORAGE_KEY+"."+method+"."+project);
        return (value != null);
    }
}