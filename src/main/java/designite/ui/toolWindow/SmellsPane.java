package designite.ui.toolWindow;

import com.intellij.openapi.project.Project;
import com.intellij.ui.components.JBPanel;
import designite.lineMarkerProvider.ProjectSmellsInfo;
import designite.lineMarkerProvider.SmellsInfoProvider;
import designite.models.*;
import designite.ui.Charts.MetricPieChart;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.XChartPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SmellsPane {

    private Project project;

    public SmellsPane(Project project) {
        this.project = project;
    }

    public JPanel createImplementationSmellPane(JPanel implementationSmellPane) {
        ProjectSmellsInfo smellsInfoProvider = SmellsInfoProvider.getInstance(project);
        List<ImplementationSmell> implementationSmellsList = smellsInfoProvider.getImplementationSmellsList();
        HashMap<String, Integer> frequencyMap = new HashMap<>();
        List<Number> pieChartData = new ArrayList<Number>();
        List<String> pieChartLabels = new ArrayList<String>();
        for (int i = 1; i<implementationSmellsList.size();i++) {
            String smell = implementationSmellsList.get(i).getName();
            if (frequencyMap.containsKey(smell)) {
                frequencyMap.put(smell, frequencyMap.get(smell) + 1);
            }
            else {
                frequencyMap.put(smell, 1);
            }
        }
        for (String value : frequencyMap.keySet()) {
            pieChartData.add(frequencyMap.get(value));
            pieChartLabels.add(value);
        }
        implementationSmellPane.removeAll();
        JPanel pieChartPanel = createPieChart(pieChartData,pieChartLabels);
        implementationSmellPane.add(pieChartPanel);
        return implementationSmellPane;
    }

    public JPanel createDesignSmellPane(JPanel designSmellPane) {
        ProjectSmellsInfo smellsInfoProvider = SmellsInfoProvider.getInstance(project);
        List<DesignSmell> designSmellList = smellsInfoProvider.getDesignSmellList();
        HashMap<String, Integer> frequencyMap = new HashMap<>();
        List<Number> pieChartData = new ArrayList<Number>();
        List<String> pieChartLabels = new ArrayList<String>();
        for (int i = 1; i<designSmellList.size();i++) {
            String smell = designSmellList.get(i).getName();
            if (frequencyMap.containsKey(smell)) {
                frequencyMap.put(smell, frequencyMap.get(smell) + 1);
            }
            else {
                frequencyMap.put(smell, 1);
            }
        }
        for (String value : frequencyMap.keySet()) {
            pieChartData.add(frequencyMap.get(value));
            pieChartLabels.add(value);
        }
        designSmellPane.removeAll();
        designSmellPane = createPieChart(pieChartData,pieChartLabels);
        return designSmellPane;
    }

    public JPanel createArchitectureSmellPane(JPanel architectureSmellPane) {
        ProjectSmellsInfo smellsInfoProvider = SmellsInfoProvider.getInstance(project);
        List<ArchitectureSmell> architectureSmellList = smellsInfoProvider.getArchitectureSmellList();
        HashMap<String, Integer> frequencyMap = new HashMap<>();
        List<Number> pieChartData = new ArrayList<Number>();
        List<String> pieChartLabels = new ArrayList<String>();
        for (int i = 1; i<architectureSmellList.size();i++) {
            String smell = architectureSmellList.get(i).getName();
            if (frequencyMap.containsKey(smell)) {
                frequencyMap.put(smell, frequencyMap.get(smell) + 1);
            }
            else {
                frequencyMap.put(smell, 1);
            }
        }
        for (String value : frequencyMap.keySet()) {
            pieChartData.add(frequencyMap.get(value));
            pieChartLabels.add(value);
        }
        architectureSmellPane.removeAll();
        architectureSmellPane = createPieChart(pieChartData,pieChartLabels);

        return architectureSmellPane;
    }

    public JPanel createTestSmellPane(JPanel testSmellPane) {
        ProjectSmellsInfo smellsInfoProvider = SmellsInfoProvider.getInstance(project);
        List<TestSmell> testSmellsList = smellsInfoProvider.getTestSmellsList();
        HashMap<String, Integer> frequencyMap = new HashMap<>();
        List<Number> pieChartData = new ArrayList<Number>();
        List<String> pieChartLabels = new ArrayList<String>();
        for (int i = 1; i<testSmellsList.size();i++) {
            String smell = testSmellsList.get(i).getName();
            if (frequencyMap.containsKey(smell)) {
                frequencyMap.put(smell, frequencyMap.get(smell) + 1);
            }
            else {
                frequencyMap.put(smell, 1);
            }
        }
        for (String value : frequencyMap.keySet()) {
            pieChartData.add(frequencyMap.get(value));
            pieChartLabels.add(value);
        }
        testSmellPane.removeAll();
        testSmellPane =  createPieChart(pieChartData,pieChartLabels);
        return testSmellPane;
    }

    public JPanel createTestabilityPane(JPanel testabilitySmellPane) {
        ProjectSmellsInfo smellsInfoProvider = SmellsInfoProvider.getInstance(project);
        List<TestabilitySmell> testabilitySmellsList = smellsInfoProvider.getTestabilitySmellList();
        HashMap<String, Integer> frequencyMap = new HashMap<>();
        List<Number> pieChartData = new ArrayList<Number>();
        List<String> pieChartLabels = new ArrayList<String>();
        for (int i = 1; i<testabilitySmellsList.size();i++) {
            String smell = testabilitySmellsList.get(i).getName();
            if (frequencyMap.containsKey(smell)) {
                frequencyMap.put(smell, frequencyMap.get(smell) + 1);
            }
            else {
                frequencyMap.put(smell, 1);
            }
        }
        for (String value : frequencyMap.keySet()) {
            pieChartData.add(frequencyMap.get(value));
            pieChartLabels.add(value);
        }
        testabilitySmellPane.removeAll();
        testabilitySmellPane = createPieChart(pieChartData,pieChartLabels);
        return testabilitySmellPane;
    }

    public JPanel createPieChart(List<Number> chartData, List<String> chartLabels) {
        JPanel panel = new JBPanel<>(new BorderLayout());
        MetricPieChart pieChart = new MetricPieChart();
        PieChart chart = pieChart.getPieChart(chartData,chartLabels);
        XChartPanel xChartPanel = new XChartPanel(chart);
        panel.add(xChartPanel, BorderLayout.CENTER);
        return panel;
    }
}
