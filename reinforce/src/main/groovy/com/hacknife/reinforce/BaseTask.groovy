package com.hacknife.reinforce

import org.gradle.api.DefaultTask


class BaseTask extends DefaultTask {


    def generateDirectory() {
        File reinforce = new File(project.getProjectDir().parentFile, ".reinforce")
        File output = new File(reinforce, ".output")
        File tools = new File(reinforce, ".tools")
        reinforce.mkdirs()
        output.mkdirs()
        tools.mkdirs()
    }

    def reinforceDir() {
        new File(project.getProjectDir().parentFile, ".reinforce")
    }

    def outputDir() {
        new File(reinforceDir(), ".output")
    }

    def toolDir() {
        new File(reinforceDir(), ".tools")
    }

    static File[] listApk(File dir) {
        List<File> list = new ArrayList<>()
        for (File file in dir.listFiles()) {
            if (file.isFile()) {
                if (file.name.endsWith(".apk")) list.add(file)
            } else {
                list.addAll(listApk(file))
            }
        }
        return list.toArray()
    }

    static String prefixName(File file) {
        file.name.replace(".apk", "")
    }

    static File makeOutApkDir(File outDir, File apkDir, File apkPath) {
        List<String> ds = new ArrayList<>()
        File dir = apkPath.parentFile
        while (dir != null && dir.path != apkDir.path) {
            ds.add(dir.name)
            dir = dir.parentFile
        }
        ds = ds.reverse()
        File out = new File(outDir.path)
        for (String d in ds) {
            out = new File(out.path, d)
            out.mkdirs()
        }
        return out
    }


    static File download(String url, String path, Closure closure) {
        File apkFile = new File(path)
        if (apkFile.exists()) {
            if (apkFile.size() >= 3522686.0)
                return apkFile
        }
        InputStream stream = null;
        FileOutputStream fos = null;
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            stream = connection.getInputStream();

            fos = new FileOutputStream(apkFile);

            float total = 0f;
            byte[] bt = new byte[1024 * 4];
            int length = 0;
            float progress = 0f
            while ((length = stream.read(bt)) != -1) {
                fos.write(bt, 0, length);
                total += length;
                if (closure != null) {
                    if ((total / 3522686.0) - progress > 0.01f) {
                        progress = total / 3522686.0
                        closure(progress)
                    }
                }
            }
            fos.close();
            stream.close();
            return apkFile;

        } catch (Exception ex) {
            if (apkFile.exists()) {
                apkFile.delete();
            }
            ex.printStackTrace()
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (Exception ex1) {
                ex1.printStackTrace();
            }
            try {
                if (stream != null) {
                    stream.close();
                }
            } catch (Exception ex1) {
                ex1.printStackTrace();
            }
        }

        return null;

    }


}