package org.catrobat.estimationplugin.rest;

/**
 * Created by dominik on 29.04.16.
 */

import com.atlassian.clover.reporters.json.JSONArray;
import com.atlassian.clover.reporters.json.JSONException;
import com.atlassian.clover.reporters.json.JSONObject;
import org.catrobat.estimationplugin.rest.json.JsonSettings;
import org.catrobat.estimationplugin.services.ProjectService;
import com.atlassian.plugin.Plugin;
import com.atlassian.sal.api.pluginsettings.PluginSettingsFactory;
import org.catrobat.estimationplugin.admin.SettingsObject;
import org.catrobat.estimationplugin.services.SettingsService;


import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/")
@Produces({MediaType.APPLICATION_JSON})

public class EstimationRest {
    private SettingsObject settingsObject;
    private PluginSettingsFactory pluginSettingsFactory;
    private SettingsService settingsService;

    public EstimationRest(PluginSettingsFactory pluginSettingsFactory)
    {
        this.pluginSettingsFactory = pluginSettingsFactory;
        this.settingsService = new SettingsService(pluginSettingsFactory);
    }

    @GET
    @Path("/check_settings/{projectID}/{method}")
    public Response getCurrentSettings(@Context HttpServletRequest request,
                                       @PathParam("projectID") int projectid,
                                       @PathParam("method") String method)
    {
        String projectname = ProjectService.getProjectFromId(projectid);
        SettingsObject currentsettings = settingsService.getexistingSettings(method,projectname);
        if(currentsettings == null)
            return Response.status(404).build();
        JsonSettings jsonSettings = new JsonSettings(currentsettings,projectid);

        return Response.ok(jsonSettings).build();
    }
    @POST
    @Path("/save_settings/{projectID}/{method}")
    public Response saveSettings(@Context HttpServletRequest request,
                                 @PathParam("projectID") int projectid,
                                 @PathParam("method") String method)
    {
        request.getParameterMap();

        JSONObject cool = new JSONObject();
        JSONArray obj = new JSONArray();
        obj.put("88");
        try {
            cool.put("yay", obj);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return Response.ok(cool).build();
    }
}
