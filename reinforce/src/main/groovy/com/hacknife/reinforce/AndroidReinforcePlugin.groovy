package com.hacknife.reinforce

 import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.ProjectConfigurationException

class AndroidReinforcePlugin implements Plugin<Project> {
    public static final String sPluginExtensionName = "reinforceConfig"
    public static final String groupReinforce = "android reinforce"

    @Override
    void apply(Project project) {
        if (!project.plugins.hasPlugin("com.android.application")) {
            throw new ProjectConfigurationException("Plugin requires the 'com.android.application' plugin to be configured.", null);
        }
        project.extensions.create(sPluginExtensionName, ConfigExtension, project)

        createVersionTask(project)
        createReinforceTask(project)
    }


    static def createVersionTask(Project project) {
        project.tasks.create('version', VersionTask) {
            group groupReinforce
        }
    }

    static def createReinforceTask(Project project) {
        project.tasks.create('reinforce', ReinforceTask) {
            group groupReinforce
        }
    }

}

