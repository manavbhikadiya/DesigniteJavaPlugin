package designite.projectOpenActions;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.fileEditor.FileEditorManagerListener;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.startup.StartupActivity;
import com.intellij.util.messages.MessageBus;
import com.intellij.util.messages.MessageBusConnection;
import designite.assetLoaders.DesigniteAssetLoader;
import designite.constants.Constants;
import designite.filesManager.AppProperties;
import designite.lineMarkerProvider.ProjectSmellsInfo;
import designite.lineMarkerProvider.SmellsInfoProvider;
import designite.logger.DesigniteLogger;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.util.logging.Level;

import static designite.constants.Constants.ANALYZE_ON_STARTUP;

public class ProjectOpenListener implements StartupActivity {

//    public void projectClosed() {
//        ToolWindowFactory.toolWindow = null;
//        //SmellsInfoProvider.resetInstance();
//    }

    @Override
    public void runActivity(@NotNull Project project) {
        DesigniteLogger.getLogger().log(Level.INFO, "Project open: " + project.getName());
        setDefaultSettingsForPlugin();
        MessageBus bus = ApplicationManager.getApplication().getMessageBus();
        MessageBusConnection connection = bus.connect();
        connection.subscribe(FileEditorManagerListener.FILE_EDITOR_MANAGER, new FileOpenListener());

        DesigniteAssetLoader designiteAssets = new DesigniteAssetLoader();

        if (!designiteAssets.isDesigniteJarExists()) {
            DesigniteLogger.getLogger().log(Level.SEVERE, "Could not find DesigniteJava.");
        } else {
            AppProperties properties = new AppProperties();
            if (Boolean.parseBoolean((String) properties.getProperty(ANALYZE_ON_STARTUP))) {
                DesigniteLogger.getLogger().log(Level.INFO, "Invoking DesigniteJava.");
                Task taskDesignite = new Task.Backgroundable(project, "Designite") {
                    @Override
                    public void run(@NotNull ProgressIndicator progressIndicator) {
                        invokeDesignite(project, progressIndicator);
                    }
                };
                ProgressManager.getInstance().run(taskDesignite);
            }
        }
        DesigniteLogger.getLogger().log(Level.INFO, "Done.");
    }

    private void setDefaultSettingsForPlugin() {
        AppProperties<Serializable> designiteProperties = new AppProperties<>();
        File settingsFilePath = new File(designiteProperties.getSettingFilePath());
        if (!settingsFilePath.exists()) {
            // Writing default values
            designiteProperties.writeProperty(Constants.ANALYZE_ON_STARTUP, "true");
            designiteProperties.writeProperty(Constants.DESIGNITE_JAR_FILE_PATH, "");
            designiteProperties.writeProperty(Constants.XMX_VALUE, "");
            designiteProperties.writeProperty(Constants.LICENSE_KEY, "");
            designiteProperties.buildProperties();
        }
    }

    public void invokeDesignite(Project project, @NotNull ProgressIndicator progressIndicator) {
        progressIndicator.setIndeterminate(false);

        String openedProjectLocation = project.getBasePath();
        DesigniteAssetLoader designiteAssetLoader = new DesigniteAssetLoader();
        ProjectSmellsInfo designiteFileLoader = SmellsInfoProvider.getInstance(project);

        progressIndicator.setFraction(0.1);

        try {
            DesigniteLogger.getLogger().log(Level.INFO, "Designite Analyzing ...");
            ProcessBuilder processBuilder = new ProcessBuilder("java",
                    "-jar", designiteAssetLoader.designiteJarFileLocation(),
                    "-i", openedProjectLocation, "-o", designiteFileLoader.getDesigniteOutputFolder());
            //processBuilder.redirectOutput(temp);
            Process process = processBuilder.start();

            progressIndicator.setFraction(0.25);

            process.waitFor();
            
            progressIndicator.setFraction(0.5);

            InputStream inputMessage = process.getInputStream();
            InputStream errorMessage = process.getErrorStream();

            byte[] inputBytes = new byte[inputMessage.available()];
            inputMessage.read(inputBytes, 0, inputBytes.length);
            if (inputBytes.length > 0)
                DesigniteLogger.getLogger().log(Level.INFO, "Designite results ...\n" + new String(inputBytes));

            byte[] errorBytes = new byte[errorMessage.available()];
            errorMessage.read(errorBytes, 0, errorBytes.length);
            if (errorBytes.length > 0)
                DesigniteLogger.getLogger().log(Level.WARNING, "error: " + new String(errorBytes));

            progressIndicator.setFraction(0.6);

            SmellsInfoProvider.resetInstance(project);

            progressIndicator.setFraction(1.0);

        } catch (Exception e) {
            DesigniteLogger.getLogger().log(Level.WARNING, "Exception occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
