package designite.ui;

import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.util.IconLoader;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DesigniteMenu extends ActionGroup {
    public DesigniteMenu() {
        super("_DesigniteJava", "Analyze source code using DesigniteJava",
                IconLoader.getIcon("Images/designite_icon_16.png", DesigniteMenu.class));
    }

    @NotNull
    @Override
    public AnAction[] getChildren(@Nullable AnActionEvent e) {
        return new AnAction[]{
                new DesigniteMenuAnalyze(),
                new DesigniteMenuSettings()
        };
    }
}
