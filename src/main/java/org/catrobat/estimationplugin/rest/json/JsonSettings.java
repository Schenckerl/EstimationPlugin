package org.catrobat.estimationplugin.rest.json;

import org.catrobat.estimationplugin.admin.SettingsObject;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)

public class JsonSettings {
    @XmlElement private String project;
    @XmlElement private String[] workflow_pools;
    @XmlElement private long projectid;


    public JsonSettings(SettingsObject settings, long projectid)
    {
        project = settings.getProject_().toString();
        workflow_pools = settings.getWorkflow_pools_();
        this.projectid = projectid;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String[] getWorkflow_pools() {
        return workflow_pools;
    }

    public void setWorkflow_pools(String[] workflow_pools) {
        this.workflow_pools = workflow_pools;
    }

    public long getProjectid() {
        return projectid;
    }

    public void setProjectid(int projectid) {
        this.projectid = projectid;
    }
}
