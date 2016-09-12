package org.catrobat.estimationplugin.services;

import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.project.ProjectManager;

/**
 * Created by dominik on 08.05.16.
 */
public class ProjectService {

    public ProjectService()
    {

    }

    public static String getProjectFromId(int id)
    {
        ProjectManager manger = ComponentAccessor.getProjectManager();
        return manger.getProjectObj((long)id).getName().toLowerCase();
    }

    public static long getProjectIdfromName(String project)
    {
        ProjectManager manger = ComponentAccessor.getProjectManager();
        return manger.getProjectObjByName(project).getId();
    }
}
