package com.hacknife.reinforce

import org.gradle.api.Plugin
import org.gradle.api.Project

public class ReinforcePlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        project.task("androidReinforce") { reinforce(project) }
    }


    static void reinforce(Project project) {
        Map<String, String> reinforce = project.properties['reinforce'] as Map<String, String>
        if (reinforce == null) {
            println("not find properties reinforce")
        } else {
            String SecretId = reinforce['SecretId']
            String SecretKey = reinforce['SecretKey']
            println("reinforce >> SecretId:${SecretId} ,  SecretKey:${SecretKey} ")
        }
    }
}