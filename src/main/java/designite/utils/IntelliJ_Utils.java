package designite.utils;

import com.intellij.ide.DataManager;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.wm.WindowManager;
import designite.logger.DesigniteLogger;

import java.awt.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;

public class IntelliJ_Utils {
    /*public static Project getActiveProject() {
        Project[] projects = ProjectManager.getInstance().getOpenProjects();
        for (Project project : projects) {
            Window window = WindowManager.getInstance().suggestParentWindow(project);
            if (window != null && window.isActive()) {
                return project;
            }
        }
        return null;
    }*/

    //from which project, this code (plugin) is running
    public static Project getCurrentProject() {
        try {
            DesigniteLogger.getLogger().log(Level.INFO, "Invoked.");
            DataContext dataContext = DataManager.getInstance().getDataContextFromFocusAsync().blockingGet(2000);
            if (dataContext != null) {
                Project project = dataContext.getData(DataKeys.PROJECT);
                if (project != null)
                    DesigniteLogger.getLogger().log(Level.INFO, "Complete... " + project.getName());
                return project;
            }
            DesigniteLogger.getLogger().log(Level.INFO, "Complete... ");
            return null;
        } catch (TimeoutException | ExecutionException toe) {
            return null;
        }
    }
}

