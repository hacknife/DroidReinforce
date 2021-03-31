package com.hacknife.reinforce

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.ProjectConfigurationException

class Reinforce implements Plugin<Project> {
    public static final String sPluginExtensionName = "androidReinforce"

    @Override
    void apply(Project project) {
        if (!project.plugins.hasPlugin("com.android.application")) {
            throw new ProjectConfigurationException("Plugin requires the 'com.android.application' plugin to be configured.", null)
        }
        project.extensions.create('reinforceConfig', ReinforceExtension)
        reinforceTask(project)

    }


    static void reinforceTask(Project project) {
        project[sPluginExtensionName].items.all { _item ->
            project.tasks.create("androidReinforce${_item.name.capitalize()}", JiaGuLeGuTask) {
                description "乐固加固"
                group "reinforce"
                jiaGuLeGuInfo _item
            }
        }
    }

}