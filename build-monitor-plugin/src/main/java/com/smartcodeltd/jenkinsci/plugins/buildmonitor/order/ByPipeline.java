package com.smartcodeltd.jenkinsci.plugins.buildmonitor.order;

import hudson.model.AbstractProject;

import java.util.Comparator;
import java.util.List;

/**
 * Created by mycronic-praktikant on 2016-04-26.
 */
public class ByPipeline implements Comparator<AbstractProject<?, ?>>{

    @Override
    public int compare(AbstractProject<?, ?> a, AbstractProject<?, ?> b) {
        if (getPipelineRoot(a).equals(getPipelineRoot(b))) {
            if (getPipelineLevel(a) < getPipelineLevel(b)) {
                return -1;
            } else if (getPipelineLevel(a) > getPipelineLevel(b)) {
                return 1;
            } else {
                return 0;
            }
        } else {
            return 1;
        }
    }
     private String getPipelineRoot(AbstractProject<?, ?> project) {
         String name;

         List<AbstractProject> upstreamProjects = project.getUpstreamProjects();
         if (upstreamProjects.isEmpty()) {
             name = project.getFullName();
         } else {
             name = getPipelineRoot(upstreamProjects.get(0));
         }
         return name;
     }

    private int getPipelineLevel(AbstractProject<?, ?> project) {
        int level;

        List<AbstractProject> upstreamProjects = project.getUpstreamProjects();
        if (upstreamProjects.isEmpty()) {
            level = 1;
        } else {
            level = 1 + getPipelineLevel(upstreamProjects.get(0));
        }

        return level;
    }
}
