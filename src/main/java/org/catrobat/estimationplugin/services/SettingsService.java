package org.catrobat.estimationplugin.services;

import com.atlassian.sal.api.pluginsettings.PluginSettingsFactory;
import com.atlassian.sal.api.pluginsettings.PluginSettings;
import org.catrobat.estimationplugin.admin.SettingsObject;

import javax.annotation.Nullable;


/**
 * Created by dominik on 08.05.16.
 */
public class SettingsService {

    private static final String PLUGIN_STORAGE_KEY = "org.catrobat.estimationplugin";
    private PluginSettingsFactory pluginSettingsFactory;

    public SettingsService(PluginSettingsFactory pluginSettingsFactory)
    {
        this.pluginSettingsFactory = pluginSettingsFactory;
    }
    @Nullable
    public SettingsObject getexistingSettings(String method, String project) {
        PluginSettings pluginSettings = pluginSettingsFactory.createGlobalSettings();
        Object value = pluginSettings.get(PLUGIN_STORAGE_KEY + "." + method + "." + project);
        if(value == null)
            return null;
        return new SettingsObject(value.toString());
    }
}
