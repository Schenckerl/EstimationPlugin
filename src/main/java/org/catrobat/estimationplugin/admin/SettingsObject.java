package org.catrobat.estimationplugin.admin;


import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.issue.CustomFieldManager;
import com.atlassian.jira.project.ProjectManager;

/**
 * Created by Dominik on 15.04.2016.
 */
public class SettingsObject {

    private int programmers_;
    private Object project_;
    private Object based_on_;
    private String[] workflow_pools_;

    public SettingsObject(Object project, int programmers, Object based_on, String[] workflow_pools)
    {
        project_ = project;
        programmers_ = programmers;
        based_on_ = based_on;
        workflow_pools_ = workflow_pools;
    }
    public SettingsObject(String to_reconstruct)
    {

    }

    public String serialize()
    {
        String selected_pools = "pools-";
        for(int i = 0; i < workflow_pools_.length; i++) {
            selected_pools += workflow_pools_[i];
            if (i < workflow_pools_.length - 1)
                selected_pools+="-";
        }
        String serilized_string = project_.toString()+";"+programmers_+";"+based_on_.toString()+";"+selected_pools;
        return serilized_string;
    }
}
