package designite.ui.Tables;

import com.intellij.openapi.util.IconLoader;
import designite.constants.Constants;
import designite.models.*;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.util.List;

public class SmellTable {

    public JPanel showArchitectureSmells(List<ArchitectureSmell> architectureSmellList, JPanel panel){
        JTable table = createTable(panel);
        DefaultTableModel model = new DefaultTableModel();
        table.setModel(model);
        model.addColumn("Smell Type");
        model.addColumn("Package");
        model.addColumn("Description");

        TableColumnModel columnModel =  table.getColumnModel();
        TableColumn smellTypeColumn = columnModel.getColumn(0);
        TableColumn packageColumn = columnModel.getColumn(1);
        TableColumn descriptionColumn = columnModel.getColumn(2);

        smellTypeColumn.setHeaderRenderer(new IconHeaderRenderer(IconLoader.getIcon(Constants.SMELL_TYPES_ICON)));
        packageColumn.setHeaderRenderer(new IconHeaderRenderer(IconLoader.getIcon(Constants.FOLDER_ICON)));
        descriptionColumn.setHeaderRenderer(new IconHeaderRenderer(IconLoader.getIcon(Constants.DESCRIPTION_ICON)));

        for (int i = 1 ;i < architectureSmellList.size(); i++) {
            Object[] row = new Object[3];

            row[0] = architectureSmellList.get(i).getName();
            row[1] = architectureSmellList.get(i).getPkg();
            row[2] = architectureSmellList.get(i).getDescription();

            model.addRow(row);
        }
        return showTable(panel, table);
    }

    public JPanel showDesignSmells(List<DesignSmell> designSmellList, JPanel panel){
        JTable table = createTable(panel);
        DefaultTableModel model = new DefaultTableModel();
        table.setModel(model);
        model.addColumn("Smell Type");
        model.addColumn("Package");
        model.addColumn("Class Name");
        model.addColumn("Description");

        TableColumnModel columnModel =  table.getColumnModel();
        TableColumn smellTypeColumn = columnModel.getColumn(0);
        TableColumn packageColumn = columnModel.getColumn(1);
        TableColumn classNameColumn = columnModel.getColumn(2);
        TableColumn descriptionColumn = columnModel.getColumn(3);

        smellTypeColumn.setHeaderRenderer(new IconHeaderRenderer(IconLoader.getIcon(Constants.SMELL_TYPES_ICON)));
        packageColumn.setHeaderRenderer(new IconHeaderRenderer(IconLoader.getIcon(Constants.FOLDER_ICON)));
        classNameColumn.setHeaderRenderer(new IconHeaderRenderer(IconLoader.getIcon(Constants.CLASS_ICON)));
        descriptionColumn.setHeaderRenderer(new IconHeaderRenderer(IconLoader.getIcon(Constants.DESCRIPTION_ICON)));

        for (int i = 1; i < designSmellList.size(); i++) {
            Object[] row = new Object[4];

            row[0] = designSmellList.get(i).getName();
            row[1] = designSmellList.get(i).getPkg();
            row[2] = designSmellList.get(i).getClassName();
            row[3] = designSmellList.get(i).getDescription();

            model.addRow(row);
        }
        return showTable(panel, table);
    }

    public JPanel showImplementationSmells(List<ImplementationSmell> implementationSmellList, JPanel panel){
        JTable table = createTable(panel);
        DefaultTableModel model = new DefaultTableModel();
        table.setModel(model);
        model.addColumn("Smell Type");
        model.addColumn("Package");
        model.addColumn("Class Name");
        model.addColumn("Method");
        model.addColumn("Description");

        TableColumnModel columnModel =  table.getColumnModel();
        TableColumn smellTypeColumn = columnModel.getColumn(0);
        TableColumn packageColumn = columnModel.getColumn(1);
        TableColumn classNameColumn = columnModel.getColumn(2);
        TableColumn methodColumn = columnModel.getColumn(3);
        TableColumn descriptionColumn = columnModel.getColumn(4);

        smellTypeColumn.setHeaderRenderer(new IconHeaderRenderer(IconLoader.getIcon(Constants.SMELL_TYPES_ICON)));
        packageColumn.setHeaderRenderer(new IconHeaderRenderer(IconLoader.getIcon(Constants.FOLDER_ICON)));
        classNameColumn.setHeaderRenderer(new IconHeaderRenderer(IconLoader.getIcon(Constants.CLASS_ICON)));
        methodColumn.setHeaderRenderer(new IconHeaderRenderer(IconLoader.getIcon(Constants.BRACKET_ICON)));
        descriptionColumn.setHeaderRenderer(new IconHeaderRenderer(IconLoader.getIcon(Constants.DESCRIPTION_ICON)));

        for (int i = 1; i < implementationSmellList.size(); i++) {
            Object[] row = new Object[5];

            row[0] = implementationSmellList.get(i).getName();
            row[1] = implementationSmellList.get(i).getPkg();
            row[2] = implementationSmellList.get(i).getClassName();
            row[3] = implementationSmellList.get(i).getMethodName();
            row[4] = implementationSmellList.get(i).getDescription();

            model.addRow(row);
        }
        return showTable(panel, table);
    }

    public JPanel showTypeMetrics(List<TypeMetrics> typeMetricsList, JPanel panel) {
        JTable table = createTable(panel);
        DefaultTableModel model = new DefaultTableModel();
        table.setModel(model);
        model.addColumn("Package Name");
        model.addColumn("Type Name");
        model.addColumn("NOF");
        model.addColumn("NOPF");
        model.addColumn("NOM");
        model.addColumn("NOPM");
        model.addColumn("LOC");
        model.addColumn("WMC");
        model.addColumn("NC");
        model.addColumn("DIT");
        model.addColumn("LCOM");
        model.addColumn("FANIN");
        model.addColumn("FANOUT");

        TableColumnModel columnModel =  table.getColumnModel();
        TableColumn packageName = columnModel.getColumn(0);
        TableColumn typeName = columnModel.getColumn(1);

        typeName.setHeaderRenderer(new IconHeaderRenderer(IconLoader.getIcon(Constants.SMELL_TYPES_ICON)));
        packageName.setHeaderRenderer(new IconHeaderRenderer(IconLoader.getIcon(Constants.FOLDER_ICON)));

        for (int i = 1; i < typeMetricsList.size(); i++) {
            Object[] row = new Object[13];

            row[0] = typeMetricsList.get(i).PackageName;
            row[1] = typeMetricsList.get(i).ClassName;
            row[2] = typeMetricsList.get(i).NOF;
            row[3] = typeMetricsList.get(i).NOPF;
            row[4] = typeMetricsList.get(i).NOM;
            row[5] = typeMetricsList.get(i).NOPM;
            row[6] = typeMetricsList.get(i).LOC;
            row[7] = typeMetricsList.get(i).WMC;
            row[8] = typeMetricsList.get(i).NC;
            row[9] = typeMetricsList.get(i).DIT;
            row[10] = typeMetricsList.get(i).LCOM;
            row[11] = typeMetricsList.get(i).FANIN;
            row[12] = typeMetricsList.get(i).FANOUT;

            model.addRow(row);
        }
        return showTable(panel,table);
    }

    public JPanel showTestabilitySmells(List<TestabilitySmell> testabilitySmellList, JPanel panel){
        JTable table = createTable(panel);
        DefaultTableModel model = new DefaultTableModel();
        table.setModel(model);
        model.addColumn("Smell Type");
        model.addColumn("Package");
        model.addColumn("Class");
        model.addColumn("Description");

        TableColumnModel columnModel =  table.getColumnModel();
        TableColumn smellTypeColumn = columnModel.getColumn(0);
        TableColumn packageColumn = columnModel.getColumn(1);
        TableColumn classNameColumn = columnModel.getColumn(2);
        TableColumn descriptionColumn = columnModel.getColumn(3);

        smellTypeColumn.setHeaderRenderer(new IconHeaderRenderer(IconLoader.getIcon(Constants.SMELL_TYPES_ICON)));
        packageColumn.setHeaderRenderer(new IconHeaderRenderer(IconLoader.getIcon(Constants.FOLDER_ICON)));
        classNameColumn.setHeaderRenderer(new IconHeaderRenderer(IconLoader.getIcon(Constants.CLASS_ICON)));
        descriptionColumn.setHeaderRenderer(new IconHeaderRenderer(IconLoader.getIcon(Constants.DESCRIPTION_ICON)));

        for (int i = 1; i < testabilitySmellList.size(); i++) {
            Object[] row = new Object[4];

            row[0] = testabilitySmellList.get(i).getName();
            row[1] = testabilitySmellList.get(i).getPkg();
            row[2] = testabilitySmellList.get(i).getClassName();
            row[3] = testabilitySmellList.get(i).getDescription();

            model.addRow(row);
        }
        return showTable(panel, table);
    }

    public JPanel showTestSmells(List<TestSmell> testSmellList, JPanel panel){
        JTable table = createTable(panel);
        DefaultTableModel model = new DefaultTableModel();
        table.setModel(model);
        model.addColumn("Smell Type");
        model.addColumn("Package");
        model.addColumn("Class Name");
        model.addColumn("Method");
        model.addColumn("Description");

        TableColumnModel columnModel =  table.getColumnModel();
        TableColumn smellTypeColumn = columnModel.getColumn(0);
        TableColumn packageColumn = columnModel.getColumn(1);
        TableColumn classNameColumn = columnModel.getColumn(2);
        TableColumn methodColumn = columnModel.getColumn(3);
        TableColumn descriptionColumn = columnModel.getColumn(4);

        smellTypeColumn.setHeaderRenderer(new IconHeaderRenderer(IconLoader.getIcon(Constants.SMELL_TYPES_ICON)));
        packageColumn.setHeaderRenderer(new IconHeaderRenderer(IconLoader.getIcon(Constants.FOLDER_ICON)));
        classNameColumn.setHeaderRenderer(new IconHeaderRenderer(IconLoader.getIcon(Constants.CLASS_ICON)));
        methodColumn.setHeaderRenderer(new IconHeaderRenderer(IconLoader.getIcon(Constants.BRACKET_ICON)));
        descriptionColumn.setHeaderRenderer(new IconHeaderRenderer(IconLoader.getIcon(Constants.DESCRIPTION_ICON)));

        for (int i = 1; i < testSmellList.size(); i++) {
            Object[] row = new Object[5];

            row[0] = testSmellList.get(i).getName();
            row[1] = testSmellList.get(i).getPkg();
            row[2] = testSmellList.get(i).getClassName();
            row[3] = testSmellList.get(i).getMethodName();
            row[4] = testSmellList.get(i).getDescription();

            model.addRow(row);
        }
        return showTable(panel, table);
    }
    private JPanel showTable(JPanel panel, JTable table){
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(900,150));
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        panel.setLayout(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.revalidate();
        panel.repaint();

        return panel;
    }
    private JTable createTable(JPanel panel){
        JTable table = new JTable() {
            public boolean editCellAt(int row, int column, java.util.EventObject e) {
                return false;
            }
        };
        table.setCellSelectionEnabled(false);
        table.setColumnSelectionAllowed(false);
        table.setRowSelectionAllowed(false);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, centerRenderer);
        panel.removeAll();
        return table;
    }
}