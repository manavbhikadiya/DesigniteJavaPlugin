package designite.projectOpenActions;

import com.intellij.openapi.project.Project;

public interface Callback {
    void refresh(Project project);
}
