package designite.ui;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;

import javax.swing.*;
import java.awt.*;

public class DesigniteMenuSettings extends AnAction {
    public DesigniteMenuSettings() {
        super("Settings...", "Configure DesigniteJava", null);
//                    IconLoader.getIcon("Images/preferences.png")); // it is not looking good
    }

    @Override
    public void actionPerformed(AnActionEvent event) {
//        Project project = event.getData(PlatformDataKeys.PROJECT);
        EventQueue.invokeLater(() -> {
            try {
                SettingsDialog dialog = new SettingsDialog();
                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                dialog.initialize();
                dialog.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}



