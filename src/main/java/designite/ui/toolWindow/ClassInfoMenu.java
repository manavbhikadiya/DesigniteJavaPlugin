package designite.ui.toolWindow;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.util.IconLoader;
import org.jetbrains.annotations.NotNull;

public class ClassInfoMenu extends AnAction {
    public ClassInfoMenu() {
        super("_DesigniteJava", "Show class metrics and detected smells using DesigniteJava",
                IconLoader.getIcon("/Images/designite_icon_16.png", ClassInfoMenu.class));
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {

    }
}
