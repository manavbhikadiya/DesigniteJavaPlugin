package designite.ui.toolWindow;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.IconLoader;
import com.intellij.openapi.wm.*;
import com.intellij.ui.content.*;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.Hashtable;

public class ToolWindowFactory implements com.intellij.openapi.wm.ToolWindowFactory {
    public ToolWindow toolWindow;
    public static final String TAB_PROJECT_SMELLS_METRICS = "Project Smells Metrics";
    public static final String TAB_CODE_QUALITY_INFORMATION = "Code Quality Information";

    private static Hashtable<Project, ClassInfoToolWindow> classInfoToolWindowDict = new Hashtable<>();

    private ClassInfoToolWindow myToolWindow;
    private ProjectSmellsMetrics projectSmellsMetrics;
    private Content codeQualityInformationContent;
    private Content projectSmellsInformationContent;
    public ToolWindowFactory()
    {
        myToolWindow = new ClassInfoToolWindow(this);
        projectSmellsMetrics = new ProjectSmellsMetrics();
    }

    public void addProjectSmellsMetricsTab(Project project, ToolWindow toolWindow) {
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        projectSmellsInformationContent = contentFactory.createContent(projectSmellsMetrics.getContent(project), TAB_PROJECT_SMELLS_METRICS, true);
        projectSmellsInformationContent.setIcon(IconLoader.getIcon("Images/pie-chart.png", ToolWindowFactory.class));
        projectSmellsInformationContent.putUserData(ToolWindow.SHOW_CONTENT_ICON, Boolean.TRUE);
        toolWindow.getContentManager().addContent(projectSmellsInformationContent);
    }

    public void addCodeQualityInformationTab(Project project, ToolWindow toolWindow) {
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        codeQualityInformationContent = contentFactory.createContent(myToolWindow.getContent(project), TAB_CODE_QUALITY_INFORMATION, true);
        codeQualityInformationContent.setIcon(IconLoader.getIcon("Images/clean-code.png", ToolWindowFactory.class));
        codeQualityInformationContent.putUserData(ToolWindow.SHOW_CONTENT_ICON, Boolean.TRUE);
        toolWindow.getContentManager().addContent(codeQualityInformationContent);
    }

    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        addThisInstance(project);
        this.toolWindow = toolWindow;
        addCodeQualityInformationTab(project, toolWindow);
        addProjectSmellsMetricsTab(project, toolWindow);
        toolWindow.setType(ToolWindowType.DOCKED,null);
    }

    private void addThisInstance(Project project) {
        classInfoToolWindowDict.computeIfAbsent(project, k -> myToolWindow);
    }

    public static ClassInfoToolWindow getInstance(Project project) {
        if (project !=null){
            return classInfoToolWindowDict.get(project);
        }
        return null;
    }
    public static void resetToolWindow(Project project){
        if (project !=null){
            classInfoToolWindowDict.remove(project);
        }
    }
    void setContent(JPanel panel) {
        if (toolWindow == null)
            return;
        toolWindow.getContentManager().removeAllContents(false);
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        codeQualityInformationContent = contentFactory.createContent(panel, TAB_CODE_QUALITY_INFORMATION, true);
        codeQualityInformationContent.setIcon(IconLoader.getIcon("Images/clean-code.png", ToolWindowFactory.class));
        codeQualityInformationContent.putUserData(ToolWindow.SHOW_CONTENT_ICON, Boolean.TRUE);
        toolWindow.getContentManager().addContent(codeQualityInformationContent);
        toolWindow.getContentManager().addContent(projectSmellsInformationContent);
    }
}