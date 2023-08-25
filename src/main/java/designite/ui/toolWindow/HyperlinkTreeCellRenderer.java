package designite.ui.toolWindow;

import java.awt.*;
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

public class HyperlinkTreeCellRenderer extends DefaultTreeCellRenderer {
    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value,boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        Component component = super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
        if (value instanceof DefaultMutableTreeNode) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
            Object userObject = node.getUserObject();
            if (userObject instanceof JLabel) {
                JLabel label = (JLabel) userObject;
                String text = label.getText();
                setText("<html>"+text+"</html>");
                setToolTipText(text);
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                setBackgroundSelectionColor(null);
            }
        }
        return component;
    }
}
