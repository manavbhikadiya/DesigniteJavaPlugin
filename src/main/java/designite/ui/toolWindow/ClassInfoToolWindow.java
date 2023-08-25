package designite.ui.toolWindow;

import com.intellij.ide.util.PsiNavigationSupport;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiPackage;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.treeStructure.Tree;
import designite.lineMarkerProvider.ProjectSmellsInfo;
import designite.lineMarkerProvider.SmellsInfoProvider;
import designite.models.*;
import designite.ui.Charts.MetricXYChart;
import org.jetbrains.annotations.NotNull;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChart;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static designite.constants.Constants.TOOL_WINDOW_ANALYSIS_TEXT;
import static designite.constants.Constants.TOOL_WINDOW_TEXT;

public class ClassInfoToolWindow {
    private ToolWindowFactory toolWindowFactory;
    private String currentFocusedFile;
    private Project project;

    public ClassInfoToolWindow() {
    }

    public ClassInfoToolWindow(ToolWindowFactory toolWindowFactory) {
        this.toolWindowFactory = toolWindowFactory;
        currentFocusedFile = "";
    }

    @NotNull
    public synchronized JTree getTree(Project project) {
        ProjectSmellsInfo smellsInfoProvider = SmellsInfoProvider.getInstance(project);
        List<TypeMetrics> typeMetricsList = smellsInfoProvider.getTypeMetrics();
//        JOptionPane.showMessageDialog(null, "total: " + typeMetricsList.size(), "DesigniteJava: Total type metrics",
//                JOptionPane.WARNING_MESSAGE);
        if (currentFocusedFile.isEmpty()) {
            DefaultMutableTreeNode currentClassDoc = new DefaultMutableTreeNode("DesigniteJava code quality window");
            currentClassDoc.add(new DefaultMutableTreeNode(TOOL_WINDOW_TEXT));
            currentClassDoc.add(new DefaultMutableTreeNode(TOOL_WINDOW_ANALYSIS_TEXT));
            return new Tree(currentClassDoc);
        } else {
            List<TypeMetrics> filteredList = typeMetricsList
                    .stream()
                    .filter(metrics -> currentFocusedFile.equals(metrics.FilePath))
                    .collect(Collectors.toList());
//            JOptionPane.showMessageDialog(null, "total: " + filteredList.size() + "\ncurrentFocusedFile: " + currentFocusedFile + "\nfirst FilePath: " + typeMetricsList.get(0).FilePath, "DesigniteJava: Total type metrics",
//                    JOptionPane.WARNING_MESSAGE);
            DefaultMutableTreeNode currentDoc = new DefaultMutableTreeNode("Code Quality information about the selected document");

            DefaultMutableTreeNode firstClass = null;
            for (TypeMetrics typeMetrics : filteredList) {
                JLabel label = new JLabel("Class Info - <font color='#3399ff'>"+typeMetrics.PackageName+"."+typeMetrics.ClassName+"</font>");
                DefaultMutableTreeNode currentClass = new DefaultMutableTreeNode(label);
                populateArchitectureSmells(smellsInfoProvider, typeMetrics, currentClass);
                populateDesignSmells(smellsInfoProvider, typeMetrics, currentClass);
                populateTestabilitySmells(smellsInfoProvider, typeMetrics, currentClass);
                populateImplSmells(smellsInfoProvider, typeMetrics, currentClass);
                populateTestSmells(smellsInfoProvider, typeMetrics, currentClass);
                populateMetrics(typeMetrics, currentClass);
                currentDoc.add(currentClass);
                if (firstClass == null)
                    firstClass = currentClass;
            }
            Tree tree = new Tree(currentDoc);
            if (firstClass != null)
                tree.expandPath(new TreePath(firstClass.getPath()));
            else {
//                if (!smellsInfoProvider.isProjectAnalyzed()) {
//                    DesigniteMenuAnalyze menuAnalyze = new DesigniteMenuAnalyze();
//                    menuAnalyze.actionPerformed(null);
//                }
                DefaultMutableTreeNode currentClassDoc = new DefaultMutableTreeNode("DesigniteJava code quality window");
                currentClassDoc.add(new DefaultMutableTreeNode(TOOL_WINDOW_TEXT));
                currentClassDoc.add(new DefaultMutableTreeNode(TOOL_WINDOW_ANALYSIS_TEXT));
                tree = new Tree(currentClassDoc);
            }
            tree.setCellRenderer(new HyperlinkTreeCellRenderer());
            DefaultTreeModel model = new DefaultTreeModel(currentDoc);
            tree.setModel(model);
            tree = handleEvents(tree);
            return tree;
        }
    }


    public Tree handleEvents(Tree tree) {
        tree.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                e.getComponent().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                e.getComponent().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                TreePath path = tree.getPathForLocation(e.getX(), e.getY());
                if (path != null) {
                    DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
                    Object nodeInfo =  node.getUserObject();
                    if (nodeInfo instanceof JLabel) {
                        JLabel label = (JLabel) nodeInfo;
                        String text = label.getText().replaceAll("\\<.*?\\>", "");
                        checkText(text);
                    }
                }
            }
        });
        return tree;
    }

    private void checkText(String text) {
        if (text.contains("in method")) {
            Pattern pattern = Pattern.compile("method: (\\w+) and class: ([\\w.]+)");
            Matcher matcher = pattern.matcher(text);
            if (matcher.find()) {
                String methodName = matcher.group(1);
                String className = matcher.group(2);
                PsiClass psiClass = JavaPsiFacade.getInstance(project).findClass(className, GlobalSearchScope.everythingScope(project));
                if (psiClass != null) {
                    PsiMethod[] methods = psiClass.findMethodsByName(methodName,false);
                    PsiNavigationSupport.getInstance().createNavigatable(project, psiClass.getContainingFile().getVirtualFile(), methods[0].getTextRange().getStartOffset()).navigate(true);
                }
            }
        }  else if (text.contains("in package")) {
            Pattern pattern = Pattern.compile("\\[in package: (.*?)\\]");
            Matcher matcher = pattern.matcher(text);
            if (matcher.find()) {
                String packageName = matcher.group(1);
                PsiPackage psiPackage = JavaPsiFacade.getInstance(project).findPackage(packageName);
                PsiClass[] className = psiPackage.getClasses();
                PsiClass psiClass = className[0];
                PsiNavigationSupport.getInstance().createNavigatable(project, psiClass.getContainingFile().getVirtualFile(), psiClass.getTextRange().getStartOffset()).navigate(true);
            }
        } else {
            String className = text.split("Class Info - ")[1];
            PsiClass psiClass = JavaPsiFacade.getInstance(project).findClass(className, GlobalSearchScope.everythingScope(project));
            if (psiClass != null) {
                PsiNavigationSupport.getInstance().createNavigatable(project, psiClass.getContainingFile().getVirtualFile(), psiClass.getTextRange().getStartOffset()).navigate(true);
            }
        }
    }

    private boolean isProjectInitiated() {
        ProjectManager projectManager = ProjectManager.getInstance();
        Project[] projects = projectManager.getOpenProjects();
        Project project = null;
        if (projects.length > 0) {
            project = projects[projects.length - 1];
            return project.isOpen();
        }
        return false;
    }

    private void populateMetrics(TypeMetrics typeMetrics, DefaultMutableTreeNode currentClass) {
        DefaultMutableTreeNode metricsList = new DefaultMutableTreeNode("Metrics");
        DefaultMutableTreeNode[] metrics = {
                new DefaultMutableTreeNode("Lines of code: " + typeMetrics.LOC),
                new DefaultMutableTreeNode("Lack of cohesion of methods: " + typeMetrics.LCOM),
                new DefaultMutableTreeNode("Weighted methods per class: " + typeMetrics.WMC),
                new DefaultMutableTreeNode("Number of fields: " + typeMetrics.NOF),
                new DefaultMutableTreeNode("Number of public fields: " + typeMetrics.NOPF),
                new DefaultMutableTreeNode("Number of methods: " + typeMetrics.NOM),
                new DefaultMutableTreeNode("Number of public methods: " + typeMetrics.NOPM),
                new DefaultMutableTreeNode("Fan-in: " + typeMetrics.FANIN),
                new DefaultMutableTreeNode("Fan-out: " + typeMetrics.FANOUT),
                new DefaultMutableTreeNode("Depth of inheritance tree: " + typeMetrics.DIT),
                new DefaultMutableTreeNode("Number of children (subtypes): " + typeMetrics.NC)
        };
        for (int i = 0; i <= metrics.length - 1; i++) {
            metricsList.add(metrics[i]);
        }
        currentClass.add(metricsList);
    }

    public void populateDesignSmells(ProjectSmellsInfo smellsInfoProvider, TypeMetrics typeMetrics, DefaultMutableTreeNode currentClass) {
        List<DesignSmell> designSmellList = smellsInfoProvider.getDesignSmellList();
        List<DesignSmell> filteredSmellList = designSmellList
                .stream()
                .filter(smell -> smell.getClassName().equals(typeMetrics.ClassName) && smell.getPkg().equals(typeMetrics.PackageName))
                .collect(Collectors.toList());
        if (filteredSmellList.size() > 0) {
            DefaultMutableTreeNode designSmellsListText = new DefaultMutableTreeNode("Design smells");

            for (DesignSmell designSmell : filteredSmellList) {
                DefaultMutableTreeNode aDesignSmell = new DefaultMutableTreeNode(designSmell.getName() + ": " + designSmell.getDescription());
                designSmellsListText.add(aDesignSmell);
            }
            currentClass.add(designSmellsListText);
        }
    }

    public void populateArchitectureSmells(ProjectSmellsInfo smellsInfoProvider, TypeMetrics typeMetrics, DefaultMutableTreeNode currentClass) {
        List<ArchitectureSmell> architectureSmellList = smellsInfoProvider.getArchitectureSmellList();
        List<ArchitectureSmell> filteredSmellList = architectureSmellList
                .stream()
                .filter(smell -> smell.getPkg().equals(typeMetrics.PackageName))
                .collect(Collectors.toList());
        if (filteredSmellList.size() > 0) {
            DefaultMutableTreeNode archSmellsListText = new DefaultMutableTreeNode("Architecture smells");

            for (ArchitectureSmell archSmell : filteredSmellList) {
                String smellName = archSmell.getName();
                String packageName = archSmell.getPkg();
                String description = archSmell.getDescription();
                String smellLabel = smellName + "<font color='#3399ff'> [in package: " + packageName +"]:</font> " + description;
                JLabel label = new JLabel(smellLabel);
                DefaultMutableTreeNode aArchSmell = new DefaultMutableTreeNode(label);
                archSmellsListText.add(aArchSmell);
            }
            currentClass.add(archSmellsListText);
        }
    }

    public void populateTestabilitySmells(ProjectSmellsInfo smellsInfoProvider, TypeMetrics typeMetrics, DefaultMutableTreeNode currentClass) {
        List<TestabilitySmell> testabilitySmellList = smellsInfoProvider.getTestabilitySmellList();
        List<TestabilitySmell> filteredSmellList = testabilitySmellList
                .stream()
                .filter(smell -> smell.getClassName().equals(typeMetrics.ClassName) && smell.getPkg().equals(typeMetrics.PackageName))
                .collect(Collectors.toList());
        if (filteredSmellList.size() > 0) {
            DefaultMutableTreeNode testabilitySmellsListText = new DefaultMutableTreeNode("Testability smells");

            for (TestabilitySmell testabilitySmell : filteredSmellList) {
                String smellName = testabilitySmell.getName();
                String description = testabilitySmell.getDescription();
                String smellObj = smellName + ": " + description;
                DefaultMutableTreeNode aTestabilitySmell = new DefaultMutableTreeNode(smellObj);
                testabilitySmellsListText.add(aTestabilitySmell);
            }
            currentClass.add(testabilitySmellsListText);
        }
    }

    public void populateImplSmells(ProjectSmellsInfo smellsInfoProvider, TypeMetrics typeMetrics, DefaultMutableTreeNode currentClass) {
        List<ImplementationSmell> implementationSmellList = smellsInfoProvider.getImplementationSmellsList();
        List<ImplementationSmell> filteredImplSmellList = implementationSmellList
                .stream()
                .filter(smell -> smell.getClassName().equals(typeMetrics.ClassName) && smell.getPkg().equals(typeMetrics.PackageName))
                .collect(Collectors.toList());
        if (filteredImplSmellList.size() > 0) {
            DefaultMutableTreeNode implSmellsListText = new DefaultMutableTreeNode("Implementation smells");

            for (ImplementationSmell implSmell : filteredImplSmellList) {
                JLabel label = new JLabel(implSmell.getName() + "<font color='#3399ff'> [in method: " + implSmell.getMethodName() +" and class: "+implSmell.getPkg()+"."+implSmell.getClassName()+ "]:</font> " + implSmell.getDescription());
                DefaultMutableTreeNode aImplSmell = new DefaultMutableTreeNode(label);
                implSmellsListText.add(aImplSmell);
            }
            currentClass.add(implSmellsListText);
        }
    }

    public void populateTestSmells(ProjectSmellsInfo smellsInfoProvider, TypeMetrics typeMetrics, DefaultMutableTreeNode currentClass) {
        List<TestSmell> testSmellList = smellsInfoProvider.getTestSmellsList();
        List<TestSmell> filteredTestSmellList = testSmellList
                .stream()
                .filter(smell -> smell.getClassName().equals(typeMetrics.ClassName) && smell.getPkg().equals(typeMetrics.PackageName))
                .collect(Collectors.toList());
        if (filteredTestSmellList.size() > 0) {
            DefaultMutableTreeNode testSmellsListText = new DefaultMutableTreeNode("Test smells");

            for (TestSmell testSmell : filteredTestSmellList) {
                JLabel label = new JLabel(testSmell.getName() + "<font color='#3399ff'> [in method: " + testSmell.getMethodName() +" and class: "+testSmell.getPkg()+"."+testSmell.getClassName()+ "]:</font> " + testSmell.getDescription());
                DefaultMutableTreeNode aTestSmell = new DefaultMutableTreeNode(label);
                testSmellsListText.add(aTestSmell);
            }
            currentClass.add(testSmellsListText);
        }
    }

    public JPanel getContent(Project project) {
        JTree designiteTree = getTree(project);
        this.project = project;
        JBScrollPane scrollPane = new JBScrollPane(designiteTree);
        JPanel panel = new JPanel(new GridLayout(0, 2));
        MetricXYChart metricXYChart = new MetricXYChart();
        XYChart xyChart = metricXYChart.getXYChart(project);
        XChartPanel xChartPanel = new XChartPanel(xyChart);
        panel.add(scrollPane);
        panel.add(xChartPanel);
        return panel;
    }

    public void update(String path) {
        if (project != null) {
            currentFocusedFile = path.replace("/", File.separator);
            toolWindowFactory.setContent(getContent(project));
        }
    }
}