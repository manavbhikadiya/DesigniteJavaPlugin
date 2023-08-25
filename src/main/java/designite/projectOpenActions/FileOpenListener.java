package designite.projectOpenActions;

import com.intellij.openapi.fileEditor.FileEditorManagerEvent;
import com.intellij.openapi.fileEditor.FileEditorManagerListener;
import com.intellij.openapi.vfs.VirtualFile;
import designite.ui.toolWindow.ClassInfoToolWindow;
import designite.ui.toolWindow.ToolWindowFactory;
import org.jetbrains.annotations.NotNull;

public class FileOpenListener implements FileEditorManagerListener {
    @Override
    public void selectionChanged(@NotNull FileEditorManagerEvent event) {
        ClassInfoToolWindow toolWindow = ToolWindowFactory.getInstance(event.getManager().getProject());
        if (toolWindow != null) {
            VirtualFile file = event.getNewFile();
            if (file != null)
                toolWindow.update(file.getPath());
        }
    }
}
