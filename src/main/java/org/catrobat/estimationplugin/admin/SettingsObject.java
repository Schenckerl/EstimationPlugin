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
        String[] settings = to_reconstruct.split(";");
        if(settings.length != 4) {
            setInvalid();
            return;
        }
        this.project_ = settings[0];
        this.programmers_= Integer.parseInt(settings[1]);
        this.based_on_ = settings[2];
        String[] pools = settings[3].split("-");
        this.workflow_pools_ = pools;
    }

    public String serialize()
    {
        String selected_pools = "";
        for(int i = 0; i < workflow_pools_.length; i++) {
            selected_pools += workflow_pools_[i];
            if (i < workflow_pools_.length - 1)
                selected_pools+="-";
        }
        String serilized_string = project_.toString()+";"+programmers_+";"+based_on_.toString()+";"+selected_pools+"";
        return serilized_string;
    }

    private void setInvalid()
    {
        this.programmers_ = -1;
        this.project_ = null;
        this.based_on_ = null;
        this.workflow_pools_ = null;
    }

    public int getProgrammers_() {
        return programmers_;
    }

    public void setProgrammers_(int programmers_) {
        this.programmers_ = programmers_;
    }

    public Object getProject_() {
        return project_;
    }

    public void setProject_(Object project_) {
        this.project_ = project_;
    }

    public String[] getWorkflow_pools_() {
        return workflow_pools_;
    }

    public void setWorkflow_pools_(String[] workflow_pools_) {
        this.workflow_pools_ = workflow_pools_;
    }
}
