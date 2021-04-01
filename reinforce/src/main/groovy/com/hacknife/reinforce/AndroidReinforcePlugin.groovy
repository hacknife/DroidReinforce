package com.hacknife.reinforce

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.ProjectConfigurationException

import java.util.function.Consumer

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
        createInstallTask(project)
        createReinforceTask(project)

    }


    static def createVersionTask(Project project) {
        project.tasks.create('version', VersionTask) {
            group groupReinforce
        }
    }

    static def createInstallTask(Project project) {
        File output = new File("${project.getProjectDir().parentFile}${File.separator}.reinforce${File.separator}.output")
        if (!output.exists()) return
        File[] apps = BaseTask.listApk(output)
        for (File apk in apps) {
            if (!apk.name.endsWith("_legu_zipalign_signed.apk")) continue
            StringBuilder builder = new StringBuilder()
            List<String> ds = new ArrayList<>()
            File dir = apk.parentFile
            while (dir != null && dir.path != output.path) {
                ds.add(dir.name)
                dir = dir.parentFile
            }
            ds = ds.reverse()
            builder.append("install ")
            for (String d : ds) {
                builder.append(d).append(" ")
            }
            builder.append(BaseTask.prefixName(apk).replace("_legu_zipalign_signed",""))
            project.tasks.create(builder.toString(), InstallTask) {
                group groupReinforce
            }
        }
    }

    static def createReinforceTask(Project project) {
        project.tasks.create('reinforce', ReinforceTask) {
            group groupReinforce
        }
    }

}

