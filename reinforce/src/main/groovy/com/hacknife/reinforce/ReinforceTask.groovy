package com.hacknife.reinforce


import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.Project
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

public class ReinforceTask extends DefaultTask {


    @TaskAction
    public void run() throws Exception {
        ConfigExtension extension = project.getExtensions().findByType(ConfigExtension.class);
        PropertyFinder finder = new PropertyFinder(project, extension)
        finder.invalid()
    }
}
