package org.catrobat.estimationplugin.reports;

import com.atlassian.jira.plugin.report.impl.AbstractReport;
import com.atlassian.jira.util.ParameterUtils;
import com.atlassian.jira.web.action.ProjectActionSupport;
import com.atlassian.sal.api.pluginsettings.PluginSettings;
import com.atlassian.sal.api.pluginsettings.PluginSettingsFactory;
import com.atlassian.jira.project.ProjectManager;
import org.apache.commons.collections.map.HashedMap;
import org.catrobat.estimationplugin.admin.SettingsObject;

import java.util.HashMap;
import java.util.Map;

public class EstimationPluginBackward extends AbstractReport {

    private final PluginSettingsFactory pluginSettingsFactory;
    private final ProjectManager projectManager;

    public EstimationPluginBackward(PluginSettingsFactory pluginSettingsFactory, ProjectManager projectManager)
    {
        this.pluginSettingsFactory = pluginSettingsFactory;
        this.projectManager = projectManager;
    }

    public String generateReportHtml(ProjectActionSupport projectActionSupport, Map params) throws Exception {

        PluginSettings settings =pluginSettingsFactory.createGlobalSettings();

        Long projectId = ParameterUtils.getLongParam(params, "selectedProjectId");
        Map<String, Object> velocityParams = new HashMap<String, Object>();
        velocityParams.put("projectName", projectManager.getProjectObj(projectId).getName());
        Object project_settings = settings.get("org.catrobat.estimationplugin.backwardcalculation.musicdroid");
        if(project_settings == null)
            velocityParams.put("settings", "invalid something went wrong");
        else {
            SettingsObject settings_object = new SettingsObject(project_settings.toString());
            velocityParams.put("settings", settings_object.serialize());
        }
        return descriptor.getHtml("view",velocityParams);
    }
}
