package designite.lineMarkerProvider;

import com.intellij.openapi.project.Project;

import java.util.Hashtable;


public class SmellsInfoProvider{
    private static Hashtable<Project, ProjectSmellsInfo> instances = new Hashtable<>();

    /*public static ProjectSmellsInfo getInstance() {
        Project activeProject = IntelliJ_Utils.getCurrentProject();
        return getInstance(activeProject);
    }*/

    public static ProjectSmellsInfo getInstance(Project project) {
        ProjectSmellsInfo instance = null;
        if (project !=null){
            instance = instances.get(project);
            if (instance != null)
                return instance;
            instance = new ProjectSmellsInfo(project);
            instances.put(project, instance);
            return instance;
        }
        else
            return new ProjectSmellsInfo(project);
    }



    /*public boolean isProjectInitialized() {
        //I take a very simple approach. first time when this method is called it returns false but after that true
        if (!isInitialized){
            isInitialized = true;
            DesigniteLogger.getLogger().log(Level.INFO, "isProjectInitialized - returning false");
            return false;
        }
        DesigniteLogger.getLogger().log(Level.INFO, "isProjectInitialized - returning true");
        return true;
    }*/


    public static void resetInstance(Project project) {
        //DesigniteLogger.getLogger().log(Level.INFO, "Reset invoked.");
        ProjectSmellsInfo instance = instances.get(project);
        if (instance != null)
            instances.remove(project);
    }

}
