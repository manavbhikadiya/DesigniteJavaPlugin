package designite.lineMarkerProvider;

import com.intellij.codeHighlighting.Pass;
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerInfo;
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerProvider;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.editor.markup.GutterIconRenderer;
import com.intellij.openapi.util.IconLoader;
import com.intellij.openapi.util.NotNullLazyValue;
import com.intellij.psi.*;
import com.intellij.util.FunctionUtil;
import designite.assetLoaders.DesigniteAssetLoader;
import designite.logger.DesigniteLogger;
import designite.models.*;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class LineMarker extends RelatedItemLineMarkerProvider {
    DesigniteAssetLoader designiteAssets = new DesigniteAssetLoader();
    //ProjectSmellsInfo smellsInfoProvider = null;

    protected void collectNavigationMarkers(@NotNull PsiElement element,
                                            Collection<? super RelatedItemLineMarkerInfo> result) {
        //DesigniteLogger.getLogger().log(Level.INFO, "Invoked.");
        ProjectSmellsInfo smellsInfoProvider = SmellsInfoProvider.getInstance(element.getProject());
        //if (smellsInfoProvider.getProject() != null)
        //    DesigniteLogger.getLogger().log(Level.INFO, "fetching info for " + smellsInfoProvider.getProject().getName());

        if (element instanceof PsiJavaFile) {
            packageLineMarker(element, smellsInfoProvider, result);
        }

        if (element instanceof PsiClass) {
            classLineMarker(element, smellsInfoProvider, result);
        }

        if (element instanceof PsiMethod) {
            methodLineMarker(element, smellsInfoProvider, result);
        }
    }

    private void packageLineMarker(PsiElement element, ProjectSmellsInfo smellsInfoProvider,
                              Collection<? super RelatedItemLineMarkerInfo> result){
        PsiJavaFile psiJavaFile = (PsiJavaFile) element;
        PsiPackageStatement packageStatement = psiJavaFile.getPackageStatement();
        if(packageStatement == null)
            return;
        List<ArchitectureSmell> architectureSmellList = smellsInfoProvider.getArchitectureSmellList();
        List<CodeSmell> filteredArchitectureSmellList = architectureSmellList
                .stream()
                .filter(smell -> packageStatement.getPackageName().equals(smell.getPkg()))
                .collect(Collectors.toList());
        if (filteredArchitectureSmellList.size() > 0) {
            MyLineMarkerInfo info = new MyLineMarkerInfo(element, designiteAssets,
                    filteredArchitectureSmellList, "architecture");
            result.add(info);
        }
    }

    private void classLineMarker(PsiElement element, ProjectSmellsInfo smellsInfoProvider,
                                 Collection<? super RelatedItemLineMarkerInfo> result){
        PsiClass psiClass = (PsiClass) element;
        List<DesignSmell> designSmellList = smellsInfoProvider.getDesignSmellList();
        List<TestabilitySmell> testabilitySmellList = smellsInfoProvider.getTestabilitySmellList();
        if (psiClass.getQualifiedName() == null)
            return;
        List<CodeSmell> filteredDesignSmellList = designSmellList
                .stream()
                .filter(smell -> psiClass.getQualifiedName().equals(smell.getPkg() + "." + smell.getClassName()))
                .collect(Collectors.toList());
        List<CodeSmell> filteredTestabilitySmellList = testabilitySmellList
                .stream()
                .filter(smell -> psiClass.getQualifiedName().equals(smell.getPkg() + "." + smell.getClassName()))
                .collect(Collectors.toList());
        List<CodeSmell> filteredList = new ArrayList<>(filteredDesignSmellList);
        filteredList.addAll(filteredTestabilitySmellList);
        if (filteredList.size() > 0) {
            MyLineMarkerInfo info = new MyLineMarkerInfo(element, designiteAssets,
                    filteredList, "design/testability");
            result.add(info);
        }
    }

    private void methodLineMarker(PsiElement element, ProjectSmellsInfo smellsInfoProvider,
                                  Collection<? super RelatedItemLineMarkerInfo> result){
        List<ImplementationSmell> implementationSmellList = smellsInfoProvider.getImplementationSmellsList();
        List<TestSmell> testSmellList = smellsInfoProvider.getTestSmellsList();
        PsiMethod psiMethod = (PsiMethod) element;
        String currentClass = Objects.requireNonNull(psiMethod.getContainingClass()).getQualifiedName();
        if (currentClass == null)
            return;
        try {
            List<CodeSmell> filteredImplSmellsList = implementationSmellList
                    .stream()
                    .filter(smell -> currentClass.equals(smell.getPkg() + "." + smell.getClassName()) &&
                            psiMethod.getName().equals(smell.getMethodName()))
                    .collect(Collectors.toList());
            List<CodeSmell> filteredTestSmellsList = testSmellList
                    .stream()
                    .filter(smell -> currentClass.equals(smell.getPkg() + "." + smell.getClassName()) &&
                            psiMethod.getName().equals(smell.getMethodName()))
                    .collect(Collectors.toList());
            List<CodeSmell> filteredList = new ArrayList<>(filteredImplSmellsList);
            filteredList.addAll(filteredTestSmellsList);
            if (filteredList.size() > 0) {
                MyLineMarkerInfo info = new MyLineMarkerInfo(element, designiteAssets,
                        filteredList, "implementation/test");
                result.add(info);
            }
        } catch (Exception ex) {
            DesigniteLogger.getLogger().log(Level.WARNING, ex.getMessage());
        }
    }

    private static class MyLineMarkerInfo extends RelatedItemLineMarkerInfo<PsiElement> {
        private final List<CodeSmell> smellList;

        private MyLineMarkerInfo(@NotNull PsiElement element,
                                 DesigniteAssetLoader designiteAssetLoader,
                                 List<CodeSmell> smellList,
                                 String smellType) {
            super(element,
                    element.getTextRange(),
                    designiteAssetLoader.getDesigniteIcon(),
                    Pass.LOCAL_INSPECTIONS,
                    FunctionUtil.constant(smellList.size() + " " + smellType + " smell(s) detected."),
                    null,
                    GutterIconRenderer.Alignment.RIGHT,
                    NotNullLazyValue.createConstantValue(Collections.emptyList())
            );
            this.smellList = smellList;
        }

        @Override
        public GutterIconRenderer createGutterRenderer() {
            if (myIcon == null) return null;
            return new LineMarkerGutterIconRenderer<PsiElement>(this) {
                @Override
                public AnAction getClickAction() {
                    return new SimplePopDialogAction(smellList);
                }
            };
        }
    }

    public static class SimplePopDialogAction extends AnAction {
        private final List<CodeSmell> smellList;

        public SimplePopDialogAction(List<CodeSmell> smellList) {
            this.smellList = smellList;
        }

        @Override
        public void actionPerformed(@NotNull AnActionEvent e) {
            String infoMessage = "<html><body>";
            for (CodeSmell smell : smellList) {
                infoMessage += "<p style='width: 400px;'><b>" + smell.getSmellType() + "</b> - " +
                        "<b>" + smell.getName() + "</b>: " +
                        smell.getDescription().replace(';', ' ') + "</p>";
            }
            infoMessage += "</body></html>";
            JLabel label = new JLabel();
            label.setText(infoMessage);
            JScrollPane scrollPane = new JScrollPane(label);
            scrollPane.setPreferredSize(new Dimension(600, 150)); // set preferred size
            scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
            JOptionPane.showMessageDialog(null, scrollPane, "DesigniteJava: Detected smells",
                    JOptionPane.WARNING_MESSAGE,
                    IconLoader.getIcon("Images/designite_logo.png", LineMarker.class));
        }
    }
}


