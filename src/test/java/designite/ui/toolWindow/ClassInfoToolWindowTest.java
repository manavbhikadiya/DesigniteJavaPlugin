package designite.ui.toolWindow;

import designite.lineMarkerProvider.ProjectSmellsInfo;
import designite.models.*;
import org.junit.Test;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class ClassInfoToolWindowTest {

    @Test
    public void testPopulateArchitectureSmells() {
        TypeMetrics typeMetrics = new TypeMetrics();
        typeMetrics.PackageName = "com.example.package";

        ArchitectureSmell architectureSmell1 = new ArchitectureSmell();
        architectureSmell1.setName("Smell 1");
        architectureSmell1.setDescription("Smell 1 description");
        architectureSmell1.setPkg("com.example.package");
        ArchitectureSmell architectureSmell2 = new ArchitectureSmell();
        architectureSmell2.setName("Smell 2");
        architectureSmell2.setDescription("Smell 2 description");
        architectureSmell2.setPkg("com.example.package");
        List<ArchitectureSmell> architectureSmellList = new ArrayList<>();
        architectureSmellList.add(architectureSmell1);
        architectureSmellList.add(architectureSmell2);

        ProjectSmellsInfo smellsInfoProvider = mock(ProjectSmellsInfo.class);
        when(smellsInfoProvider.getArchitectureSmellList()).thenReturn(architectureSmellList);
        DefaultMutableTreeNode currentClass = new DefaultMutableTreeNode();

        ClassInfoToolWindow classInfoToolWindow = new ClassInfoToolWindow();
        classInfoToolWindow.populateArchitectureSmells(smellsInfoProvider, typeMetrics, currentClass);

        DefaultMutableTreeNode expectedArchSmellsListText = new DefaultMutableTreeNode("Architecture smells");
        JLabel label1 = new JLabel("Smell 1<font color='#3399ff'> [in package: com.example.package]:</font> Smell 1 description");
        JLabel label2 = new JLabel("Smell 2<font color='#3399ff'> [in package: com.example.package]:</font> Smell 2 description");
        DefaultMutableTreeNode expectedArchSmell1 = new DefaultMutableTreeNode(label1);
        DefaultMutableTreeNode expectedArchSmell2 = new DefaultMutableTreeNode(label2);
        expectedArchSmellsListText.add(expectedArchSmell1);
        expectedArchSmellsListText.add(expectedArchSmell2);

        DefaultMutableTreeNode expectedCurrentClass = new DefaultMutableTreeNode();
        expectedCurrentClass.add(expectedArchSmellsListText);

        assertNodeEquals(expectedCurrentClass, currentClass);
    }

    @Test
    public void testPopulateDesignSmells() {
        TypeMetrics typeMetrics = new TypeMetrics();
        typeMetrics.ClassName = "Class A";
        typeMetrics.PackageName = "com.example.package";

        DesignSmell designSmell1 = new DesignSmell();
        designSmell1.setName("Smell 1");
        designSmell1.setDescription("Smell 1 description");
        designSmell1.setClassName("Class A");
        designSmell1.setPkg("com.example.package");
        DesignSmell designSmell2 = new DesignSmell();
        designSmell2.setName("Smell 2");
        designSmell2.setDescription("Smell 2 description");
        designSmell2.setClassName("Class A");
        designSmell2.setPkg("com.example.package");
        List<DesignSmell> designSmellList = new ArrayList<>();
        designSmellList.add(designSmell1);
        designSmellList.add(designSmell2);

        ProjectSmellsInfo smellsInfoProvider = mock(ProjectSmellsInfo.class);
        when(smellsInfoProvider.getDesignSmellList()).thenReturn(designSmellList);
        DefaultMutableTreeNode currentClass = new DefaultMutableTreeNode();

        ClassInfoToolWindow classInfoToolWindow = new ClassInfoToolWindow();
        classInfoToolWindow.populateDesignSmells(smellsInfoProvider, typeMetrics, currentClass);

        DefaultMutableTreeNode expectedDesignSmellsListText = new DefaultMutableTreeNode("Design smells");
        DefaultMutableTreeNode expectedDesignSmell1 = new DefaultMutableTreeNode("Smell 1: Smell 1 description");
        DefaultMutableTreeNode expectedDesignSmell2 = new DefaultMutableTreeNode("Smell 2: Smell 2 description");
        expectedDesignSmellsListText.add(expectedDesignSmell1);
        expectedDesignSmellsListText.add(expectedDesignSmell2);

        DefaultMutableTreeNode expectedCurrentClass = new DefaultMutableTreeNode();
        expectedCurrentClass.add(expectedDesignSmellsListText);

        assertNodeEquals(expectedCurrentClass, currentClass);
    }

    @Test
    public void testPopulateTestabilitySmells() {
        TypeMetrics typeMetrics = new TypeMetrics();
        typeMetrics.ClassName = "Class A";
        typeMetrics.PackageName = "com.example.package";

        TestabilitySmell testabilitySmell1 = new TestabilitySmell();
        testabilitySmell1.setName("Smell 1");
        testabilitySmell1.setDescription("Smell 1 description");
        testabilitySmell1.setClassName("Class A");
        testabilitySmell1.setPkg("com.example.package");
        TestabilitySmell testabilitySmell2 = new TestabilitySmell();
        testabilitySmell2.setName("Smell 2");
        testabilitySmell2.setDescription("Smell 2 description");
        testabilitySmell2.setClassName("Class A");
        testabilitySmell2.setPkg("com.example.package");
        List<TestabilitySmell> testabilitySmellList = new ArrayList<>();
        testabilitySmellList.add(testabilitySmell1);
        testabilitySmellList.add(testabilitySmell2);

        ProjectSmellsInfo smellsInfoProvider = mock(ProjectSmellsInfo.class);
        when(smellsInfoProvider.getTestabilitySmellList()).thenReturn(testabilitySmellList);
        DefaultMutableTreeNode currentClass = new DefaultMutableTreeNode();

        ClassInfoToolWindow classInfoToolWindow = new ClassInfoToolWindow();
        classInfoToolWindow.populateTestabilitySmells(smellsInfoProvider, typeMetrics, currentClass);

        DefaultMutableTreeNode expTestabilitySmellsListText = new DefaultMutableTreeNode("Testability smells");
        DefaultMutableTreeNode expectedTestabilitySmell1 = new DefaultMutableTreeNode("Smell 1: Smell 1 description");
        DefaultMutableTreeNode expectedTestabilitySmell2 = new DefaultMutableTreeNode("Smell 2: Smell 2 description");
        expTestabilitySmellsListText.add(expectedTestabilitySmell1);
        expTestabilitySmellsListText.add(expectedTestabilitySmell2);

        DefaultMutableTreeNode expectedCurrentClass = new DefaultMutableTreeNode();
        expectedCurrentClass.add(expTestabilitySmellsListText);

        assertNodeEquals(expectedCurrentClass, currentClass);
    }

    @Test
    public void testPopulateImplementationSmells() {
        TypeMetrics typeMetrics = new TypeMetrics();
        typeMetrics.PackageName = "com.example.package";
        typeMetrics.ClassName = "Class A";

        ImplementationSmell implementationSmell1 = new ImplementationSmell();
        implementationSmell1.setName("Smell 1");
        implementationSmell1.setDescription("Smell 1 description");
        implementationSmell1.setMethodName("Method A");
        implementationSmell1.setClassName("Class A");
        implementationSmell1.setPkg("com.example.package");
        ImplementationSmell implementationSmell2 = new ImplementationSmell();
        implementationSmell2.setName("Smell 2");
        implementationSmell2.setDescription("Smell 2 description");
        implementationSmell2.setMethodName("Method B");
        implementationSmell2.setClassName("Class A");
        implementationSmell2.setPkg("com.example.package");
        List<ImplementationSmell> implementationSmellList = new ArrayList<>();
        implementationSmellList.add(implementationSmell1);
        implementationSmellList.add(implementationSmell2);

        ProjectSmellsInfo smellsInfoProvider = mock(ProjectSmellsInfo.class);
        when(smellsInfoProvider.getImplementationSmellsList()).thenReturn(implementationSmellList);
        DefaultMutableTreeNode currentClass = new DefaultMutableTreeNode();

        ClassInfoToolWindow classInfoToolWindow = new ClassInfoToolWindow();
        classInfoToolWindow.populateImplSmells(smellsInfoProvider, typeMetrics, currentClass);

        DefaultMutableTreeNode expectedImplSmellsListText = new DefaultMutableTreeNode("Implementation smells");
        String label1Text = "Smell 1<font color='#3399ff'> [in method: Method A and class: com.example.package.Class A]";
        label1Text += ":</font> Smell 1 description";
        String label2Text = "Smell 2<font color='#3399ff'> [in method: Method B and class: com.example.package.Class A]";
        label2Text += ":</font> Smell 2 description";
        JLabel label1 = new JLabel(label1Text);
        JLabel label2 = new JLabel(label2Text);
        DefaultMutableTreeNode expectedImplSmell1 = new DefaultMutableTreeNode(label1);
        DefaultMutableTreeNode expectedImplSmell2 = new DefaultMutableTreeNode(label2);
        expectedImplSmellsListText.add(expectedImplSmell1);
        expectedImplSmellsListText.add(expectedImplSmell2);

        DefaultMutableTreeNode expectedCurrentClass = new DefaultMutableTreeNode();
        expectedCurrentClass.add(expectedImplSmellsListText);

        assertNodeEquals(expectedCurrentClass, currentClass);
    }

    @Test
    public void testPopulateTestSmells() {
        TypeMetrics typeMetrics = new TypeMetrics();
        typeMetrics.PackageName = "com.example.package";
        typeMetrics.ClassName = "Class A";

        TestSmell testSmell1 = new TestSmell();
        testSmell1.setName("Smell 1");
        testSmell1.setDescription("Smell 1 description");
        testSmell1.setMethodName("Method A");
        testSmell1.setClassName("Class A");
        testSmell1.setPkg("com.example.package");
        TestSmell testSmell2 = new TestSmell();
        testSmell2.setName("Smell 2");
        testSmell2.setDescription("Smell 2 description");
        testSmell2.setMethodName("Method B");
        testSmell2.setClassName("Class A");
        testSmell2.setPkg("com.example.package");
        List<TestSmell> testSmellList = new ArrayList<>();
        testSmellList.add(testSmell1);
        testSmellList.add(testSmell2);

        ProjectSmellsInfo smellsInfoProvider = mock(ProjectSmellsInfo.class);
        when(smellsInfoProvider.getTestSmellsList()).thenReturn(testSmellList);
        DefaultMutableTreeNode currentClass = new DefaultMutableTreeNode();

        ClassInfoToolWindow classInfoToolWindow = new ClassInfoToolWindow();
        classInfoToolWindow.populateTestSmells(smellsInfoProvider, typeMetrics, currentClass);

        DefaultMutableTreeNode expectedTestSmellsListText = new DefaultMutableTreeNode("Test smells");
        String label1Text = "Smell 1<font color='#3399ff'> [in method: Method A and class: com.example.package.Class A]";
        label1Text += ":</font> Smell 1 description";
        String label2Text = "Smell 2<font color='#3399ff'> [in method: Method B and class: com.example.package.Class A]";
        label2Text += ":</font> Smell 2 description";
        JLabel label1 = new JLabel(label1Text);
        JLabel label2 = new JLabel(label2Text);
        DefaultMutableTreeNode expectedTestSmell1 = new DefaultMutableTreeNode(label1);
        DefaultMutableTreeNode expectedTestSmell2 = new DefaultMutableTreeNode(label2);
        expectedTestSmellsListText.add(expectedTestSmell1);
        expectedTestSmellsListText.add(expectedTestSmell2);

        DefaultMutableTreeNode expectedCurrentClass = new DefaultMutableTreeNode();
        expectedCurrentClass.add(expectedTestSmellsListText);

        assertNodeEquals(expectedCurrentClass, currentClass);
    }

    private void assertNodeEquals(DefaultMutableTreeNode expectedNode, DefaultMutableTreeNode actualNode) {
        if(expectedNode.getUserObject() != null && actualNode.getUserObject() != null) {
            assertEquals(expectedNode.getUserObject().toString(), actualNode.getUserObject().toString());
        }
        assertEquals(expectedNode.getChildCount(), actualNode.getChildCount());
        for (int i = 0; i < expectedNode.getChildCount(); i++) {
            assertNodeEquals((DefaultMutableTreeNode) expectedNode.getChildAt(i),
                    (DefaultMutableTreeNode) actualNode.getChildAt(i));
        }
    }
}
