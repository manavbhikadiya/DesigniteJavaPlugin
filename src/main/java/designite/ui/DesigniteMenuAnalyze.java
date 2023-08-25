package designite.ui;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.util.IconLoader;
import designite.assetLoaders.DesigniteAssetLoader;
import designite.constants.Constants;
import designite.filesManager.AppProperties;
import designite.filesManager.SettingsFolder;
import designite.logger.DesigniteLogger;
import designite.projectOpenActions.ProjectOpenListener;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.logging.Level;

import static designite.constants.Constants.*;


public class DesigniteMenuAnalyze extends AnAction {

    public DesigniteMenuAnalyze() {
        super("Analyze code", "Analyze source code now using DesigniteJava", null);
//                IconLoader.getIcon("Images/analysis.png")); //It is not looking good
    }

    @Override
    public void actionPerformed(AnActionEvent event) {
        DesigniteAssetLoader designiteAssetLoader = new DesigniteAssetLoader();
        if(!designiteAssetLoader.isDesigniteJarExists()) {
            int response = JOptionPane.showConfirmDialog(null,
                    DJ_DOWNLOAD_Q_TEXT,
                    "DesigniteJava",
                    JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION) {
                JOptionPane.showMessageDialog(null, DJ_DOWNLOAD_TEXT, "DesigniteJava - Download",
                        JOptionPane.INFORMATION_MESSAGE,
                        IconLoader.getIcon("Images/designite_logo.png", DesigniteMenuAnalyze.class));
                File djFilepath = Paths.get(SettingsFolder.getDesigniteDirectoryPath(), Constants.DESIGNITEJAVA_JAR_FILENAME).toFile();
                try {
                    URL downloadURL = new URL(Constants.DOWNLOAD_URL);
                    FileUtils.copyURLToFile(downloadURL, djFilepath);
                } catch (MalformedURLException e) {
                    DesigniteLogger.getLogger().log(Level.SEVERE, "Malformed URL " + e.getMessage());
                    e.printStackTrace();
                    return;
                } catch (IOException e) {
                    DesigniteLogger.getLogger().log(Level.SEVERE, "IOException occurred while downloading DesigniteJava " + e.getMessage());
                    e.printStackTrace();
                    return;
                }
                //download is successful; set this path
                AppProperties<Serializable> designiteProperties = new AppProperties<>();
                designiteProperties.writeProperty(Constants.DESIGNITE_JAR_FILE_PATH, djFilepath.toString());
                designiteProperties.buildProperties();
            }
            //check again (in case of error)
            if(!designiteAssetLoader.isDesigniteJarExists())
                return;
        }
        ProjectOpenListener projectAction = new ProjectOpenListener();
        JOptionPane.showMessageDialog(null,
                ANALYSIS_TEXT, "DesigniteJava - Analyze",
                JOptionPane.INFORMATION_MESSAGE,
                IconLoader.getIcon("Images/designite_logo.png", DesigniteMenuAnalyze.class));

        if (event != null && event.getProject() != null) {
            Task taskDesignite = new Task.Backgroundable(event.getProject(), "Designite") {
                @Override
                public void run(@NotNull ProgressIndicator progressIndicator) {
                    projectAction.invokeDesignite(event.getProject(), progressIndicator);
                }
            };
            ProgressManager.getInstance().run(taskDesignite);
        }
    }
}
