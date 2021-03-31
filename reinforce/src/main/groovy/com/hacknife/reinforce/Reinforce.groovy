package com.hacknife.reinforce

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.ProjectConfigurationException

class Reinforce implements Plugin<Project> {

    @Override
    void apply(Project project) {
        if (!project.plugins.hasPlugin("com.android.application")) {
            throw new ProjectConfigurationException("Plugin requires the 'com.android.application' plugin to be configured.", null)
        }
        ReinforceExtension extension = project.extensions.create('reinforceConfig', PublishExtension)
        project.afterEvaluate {
            extension.validate()
            new ReinforceConfiguration(extension).configure(project)
        }
    }


}

//java -jar apksigner.jar sign --ks /f/Project/Android/EdifierHome/edifier_key.keystore  --ks-key-alias edifier --ks-pass pass:edifier.2019 --key-pass pass:edifier.2019 --out /c/Users/tech137/Desktop/edif.apk /f/Project/Android/EdifierHome/app/build/outputs/apk/normal/debug/edifierhome_C1_V4.0_T202103310934_S1617154488491_normalDebug.apk