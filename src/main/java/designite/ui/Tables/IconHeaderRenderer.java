package designite.ui.Tables;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class IconHeaderRenderer extends JLabel implements TableCellRenderer {
    public IconHeaderRenderer(Icon icon) {
        setIcon(icon);
        setHorizontalAlignment(JLabel.CENTER);
        setVerticalAlignment(JLabel.CENTER);
        setPreferredSize(new Dimension(getPreferredSize().width, 50));
    }
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        setText(value.toString());
        return this;
    }
}
