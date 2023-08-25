package designite.ui.toolWindow;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.IconLoader;
import com.intellij.ui.IdeBorderFactory;
import com.intellij.ui.SideBorder;
import designite.lineMarkerProvider.ProjectSmellsInfo;
import designite.lineMarkerProvider.SmellsInfoProvider;
import designite.models.*;
import designite.ui.Charts.MetricPieChart;
import designite.ui.Charts.MetricXYChart;
import designite.ui.Tables.SmellTable;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ProjectSmellsMetrics {
    private Project project;
    private final String IMPLEMENTATION_SMELL = "Implementation";
    private final String DESIGN_SMELL = "Design";
    private final String ARCHITECTURE_SMELL = "Architecture";
    private final String TEST_SMELL = "Test";
    private final String TESTABILITY_SMELL = "Testability";
    private final String PROJECT_LEVEL_SMELL = "Project Level";
    private SmellTable smellTable;
    private JPanel containerPanel, leftPanel, rightPanel, implementationSmellPane, designSmellPane, architectureSmellPane, testSmellPane, testabilitySmellPane, projectLevelSmellPane;

    public ProjectSmellsMetrics() {
        smellTable = new SmellTable();
        implementationSmellPane = new JPanel(new GridLayout(1,2));
        projectLevelSmellPane = new JPanel(new GridLayout(1,1));
        designSmellPane = new JPanel();
        architectureSmellPane = new JPanel();
        testSmellPane = new JPanel();
        testabilitySmellPane = new JPanel();
        createLeftPanel();
        createRightPanel();
    }

    private void createLeftPanel() {
        leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBorder(IdeBorderFactory.createBorder(SideBorder.RIGHT));
    }
    private void createRightPanel() {
        rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBorder(IdeBorderFactory.createBorder(SideBorder.LEFT));
    }
    private void createProjectLevelSmellPane() {
        ProjectSmellsInfo smellsInfoProvider = SmellsInfoProvider.getInstance(project);
        projectLevelSmellPane.removeAll();
        JPanel pieChartPanel = createProjectLevelPieChart();
//        JPanel barChartPanel = createXYChart();
//        pieChartPanel.setBorder(IdeBorderFactory.createBorder(SideBorder.RIGHT));
        projectLevelSmellPane.add(pieChartPanel);
//        projectLevelSmellPane.add(barChartPanel);
        rightPanel.removeAll();
        List<TypeMetrics> typeMetricsList = smellsInfoProvider.getTypeMetrics();
        rightPanel = smellTable.showTypeMetrics(typeMetricsList, rightPanel);
    }

    public JPanel createProjectLevelPieChart() {
        MetricPieChart pieChart = new MetricPieChart();
        PieChart chart = pieChart.getDonutPieChart(project);
        XChartPanel xChartPanel = new XChartPanel(chart);
        return xChartPanel;
    }

    public JPanel createXYChart() {
        MetricXYChart metricXYChart = new MetricXYChart();
        XYChart xyChart = metricXYChart.getXYChart(project);
        XChartPanel xChartPanel = new XChartPanel(xyChart);
        return  xChartPanel;
    }

    public void updateRightPanelContent(String tab) {
        ProjectSmellsInfo smellsInfoProvider = SmellsInfoProvider.getInstance(project);
        switch (tab) {
            case IMPLEMENTATION_SMELL:
                List<ImplementationSmell> defaultImplementationSmellsList = smellsInfoProvider.getImplementationSmellsList();
                rightPanel.removeAll();
                rightPanel = smellTable.showImplementationSmells(defaultImplementationSmellsList, rightPanel);
                break;
            case DESIGN_SMELL:
                List<DesignSmell> designSmellList = smellsInfoProvider.getDesignSmellList();
                rightPanel.removeAll();
                rightPanel = smellTable.showDesignSmells(designSmellList, rightPanel);
                break;
            case ARCHITECTURE_SMELL:
                List<ArchitectureSmell> architectureSmellList = smellsInfoProvider.getArchitectureSmellList();
                rightPanel.removeAll();
                rightPanel = smellTable.showArchitectureSmells(architectureSmellList, rightPanel);
                break;
            case TEST_SMELL:
                List<TestSmell> testSmellList = smellsInfoProvider.getTestSmellsList();
                rightPanel.removeAll();
                rightPanel = smellTable.showTestSmells(testSmellList, rightPanel);
                break;
            case TESTABILITY_SMELL:
                List<TestabilitySmell> testabilitySmellList = smellsInfoProvider.getTestabilitySmellList();
                rightPanel.removeAll();
                rightPanel = smellTable.showTestabilitySmells(testabilitySmellList, rightPanel);
                break;
            default:
                List<TypeMetrics> typeMetricsList = smellsInfoProvider.getTypeMetrics();
                rightPanel.removeAll();
                rightPanel = smellTable.showTypeMetrics(typeMetricsList, rightPanel);
                break;
        }

    }

    private void updateLeftPanelContent() {
        SmellsPane smellsPane = new SmellsPane(project);

        createProjectLevelSmellPane();
        implementationSmellPane = smellsPane.createImplementationSmellPane(implementationSmellPane);
        designSmellPane = smellsPane.createDesignSmellPane(designSmellPane);
        architectureSmellPane = smellsPane.createArchitectureSmellPane(architectureSmellPane);
        testSmellPane = smellsPane.createTestSmellPane(testSmellPane);
        testabilitySmellPane = smellsPane.createTestabilityPane(testabilitySmellPane);
    }

    private JTabbedPane createJTabbedPane() {
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.add(PROJECT_LEVEL_SMELL, projectLevelSmellPane);
        tabbedPane.addTab(IMPLEMENTATION_SMELL, implementationSmellPane);
        tabbedPane.addTab(DESIGN_SMELL, designSmellPane);
        tabbedPane.addTab(ARCHITECTURE_SMELL, architectureSmellPane);
        tabbedPane.add(TEST_SMELL, testSmellPane);
        tabbedPane.add(TESTABILITY_SMELL, testabilitySmellPane);
        tabbedPane.setIconAt(0,IconLoader.getIcon("Images/statistics.png", ProjectSmellsMetrics.class));
        tabbedPane.setIconAt(1,IconLoader.getIcon("Images/hammer.png", ProjectSmellsMetrics.class));
        tabbedPane.setIconAt(2,IconLoader.getIcon("Images/sketch.png", ProjectSmellsMetrics.class));
        tabbedPane.setIconAt(3,IconLoader.getIcon("Images/3d.png", ProjectSmellsMetrics.class));
        tabbedPane.setIconAt(4,IconLoader.getIcon("Images/flask.png", ProjectSmellsMetrics.class));
        tabbedPane.setIconAt(5,IconLoader.getIcon("Images/shield.png", ProjectSmellsMetrics.class));

        tabbedPane.addChangeListener(e -> {
            int tabIndex = tabbedPane.getSelectedIndex();
            String tabTitle = tabbedPane.getTitleAt(tabIndex);
            updateRightPanelContent(tabTitle);
        });
        return tabbedPane;
    }

    private JPanel createTabPanel() {
        updateLeftPanelContent();
        JTabbedPane tabbedPane = createJTabbedPane();
        ProjectSmellsInfo smellsInfoProvider = SmellsInfoProvider.getInstance(project);
        containerPanel = new JPanel();
        if (smellsInfoProvider.isProjectAnalyzed()) {
            containerPanel.setLayout(new GridLayout(1,2));
            containerPanel.add(tabbedPane, BorderLayout.WEST);
            containerPanel.add(rightPanel, BorderLayout.CENTER);
        } else {
            containerPanel = new JPanel(new GridLayout(1, 1));
            JLabel emptyData = new JLabel("Project Not Analyzed.");
            emptyData.setIcon(IconLoader.getIcon("Images/magnifying-glass16px.png", ProjectSmellsMetrics.class));
            JPanel emptyPanel = new JPanel();
            emptyPanel.add(emptyData);
            containerPanel.add(emptyPanel, BorderLayout.CENTER);
        }
        return containerPanel;
    }

    public JPanel getContent(Project project) {
        this.project = project;
        JPanel panel = createTabPanel();
        return panel;
    }
}
