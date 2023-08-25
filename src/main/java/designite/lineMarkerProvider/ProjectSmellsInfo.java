package designite.lineMarkerProvider;

import com.intellij.openapi.project.Project;
import designite.constants.Constants;
import designite.filesManager.SettingsFolder;
import designite.logger.DesigniteLogger;
import designite.models.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;


public class ProjectSmellsInfo {
    private final String projectName;
    private final List<ImplementationSmell> implementationSmellsList = new ArrayList<>();
    private final List<DesignSmell> designSmellsList = new ArrayList<>();
    private final List<ArchitectureSmell> architectureSmellList = new ArrayList<>();
    private final List<TypeMetrics> typeMetricsList = new ArrayList<>();
    private final List<TestSmell> testSmellsList = new ArrayList<>();
    private final List<TestabilitySmell> testabilitySmellList = new ArrayList<>();
    private Project project;
    private int totalImplementationSmells = 0;
    private int totalDesignSmells = 0;
    private int totalTestSmells = 0;
    private int totalArchitectureSmells = 0;
    private int totalTestabilitySmells = 0;
    public ProjectSmellsInfo(Project project) {
        this.project = project;
        if (project != null)
            projectName = project.getName();
        else
            projectName = "";
        if (project != null && project.isInitialized()) {
            readImplSmells();
            readDesignSmells();
            readTestabilitySmells();
            readArchitectureSmells();
            readTestSmells();
            readTypeMetrics();
        }
    }

    public Project getProject(){
        return project;
    }
    public boolean isProjectAnalyzed() {
        File designiteOutputFolder = new File(
                String.valueOf(Paths.get(SettingsFolder.getDesigniteOuputDirectoryPath(), projectName)));
        return designiteOutputFolder.exists();
    }

    private void readImplSmells() {
        File designiteOutputFolder = new File(
                String.valueOf(Paths.get(SettingsFolder.getDesigniteOuputDirectoryPath(), projectName)));

        if (designiteOutputFolder.exists()) {
            String implCsvFile = Paths.get(SettingsFolder.getDesigniteOuputDirectoryPath(),
                    projectName,
                    Constants.IMPLEMENTATION_SMELLS_CSV).toString();
            BufferedReader reader;
            try {
                reader = new BufferedReader(new FileReader(implCsvFile));
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] column = line.split(",");
                    if (column.length > 5) {
                        ImplementationSmell smell = new ImplementationSmell();
                        smell.setName(column[4]);
                        smell.setProject(column[0]);
                        smell.setPkg(column[1]);
                        smell.setClassName(column[2]);
                        smell.setMethodName(column[3]);
                        smell.setDescription(column[5]);
                        smell.setSmellType("Implementation Smell");
                        totalImplementationSmells++;
                        implementationSmellsList.add(smell);
                    }
                }
            } catch (IOException e) {
                DesigniteLogger.getLogger().log(Level.WARNING, e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public List<ImplementationSmell> getImplementationSmellsList() {
        return implementationSmellsList;
    }

    public List<DesignSmell> getDesignSmellList() {
        return designSmellsList;
    }

    public List<ArchitectureSmell> getArchitectureSmellList() {
        return architectureSmellList;
    }

    public List<TestSmell> getTestSmellsList() {
        return testSmellsList;
    }

    public List<TestabilitySmell> getTestabilitySmellList() {
        return testabilitySmellList;
    }

    private void readTypeMetrics() {
        File designiteOutputFolder = new File(
                String.valueOf(Paths.get(SettingsFolder.getDesigniteOuputDirectoryPath(), projectName)));
        if (designiteOutputFolder.exists()) {
            String typeMetricsCsv = Paths.get(SettingsFolder.getDesigniteOuputDirectoryPath(),
                    projectName,
                    Constants.TYPE_METRICS_CSV).toString();
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new FileReader(typeMetricsCsv));
                String line;
                boolean header = true;
                while ((line = reader.readLine()) != null) {
                    if (header) {
                        header = false;
                        continue;
                    }
                    String[] column = line.split(",");
                    if (column.length < 15)
                        continue;
                    TypeMetrics typeMetrics = new TypeMetrics();
                    typeMetrics.ProjectName = column[0];
                    typeMetrics.PackageName = column[1];
                    typeMetrics.ClassName = column[2];
                    typeMetrics.NOF = column[3];
                    typeMetrics.NOPF = column[4];
                    typeMetrics.NOM = column[5];
                    typeMetrics.NOPM = column[6];
                    typeMetrics.LOC = column[7];
                    typeMetrics.WMC = column[8];
                    typeMetrics.NC = column[9];
                    typeMetrics.DIT = column[10];
                    if (column[11].length() > 4)
                        typeMetrics.LCOM = column[11].substring(0, 4);
                    else
                        typeMetrics.LCOM = column[11];
                    typeMetrics.FANIN = column[12];
                    typeMetrics.FANOUT = column[13];
                    typeMetrics.FilePath = column[14];
                    typeMetricsList.add(typeMetrics);
                }
            } catch (IOException e) {
                DesigniteLogger.getLogger().log(Level.WARNING, e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public String getDesigniteOutputFolder() {
        File designiteOutputFolder = new File(
                String.valueOf(Paths.get(SettingsFolder.getDesigniteOuputDirectoryPath(), projectName)));
        if (!designiteOutputFolder.exists()) {
            SettingsFolder.makeProjectOutputPath(
                    Paths.get(SettingsFolder.getDesigniteOuputDirectoryPath(), projectName).toString());
        }
        return Paths.get(SettingsFolder.getDesigniteOuputDirectoryPath(), projectName).toString();
    }

    private void readDesignSmells() {
        File designiteOutputFolder = new File(
                String.valueOf(Paths.get(SettingsFolder.getDesigniteOuputDirectoryPath(), projectName)));

        if (designiteOutputFolder.exists()) {
            String designCsvFile = Paths.get(SettingsFolder.getDesigniteOuputDirectoryPath(),
                    projectName,
                    Constants.DESIGN_SMELLS_CSV).toString();
            BufferedReader reader;
            try {
                reader = new BufferedReader(new FileReader(designCsvFile));
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] column = line.split(",");
                    if (column.length > 4) {
                        DesignSmell smell = new DesignSmell();
                        smell.setName(column[3]);
                        smell.setProject(column[0]);
                        smell.setPkg(column[1]);
                        smell.setClassName(column[2]);
                        smell.setDescription(column[4]);
                        smell.setSmellType("Design Smell");
                        totalDesignSmells++;
                        designSmellsList.add(smell);
                    }
                }
            } catch (IOException e) {
                DesigniteLogger.getLogger().log(Level.WARNING, e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private void readTestSmells() {
        File designiteOutputFolder = new File(
                String.valueOf(Paths.get(SettingsFolder.getDesigniteOuputDirectoryPath(), projectName)));

        if (designiteOutputFolder.exists()) {
            String implCsvFile = Paths.get(SettingsFolder.getDesigniteOuputDirectoryPath(),
                    projectName,
                    Constants.TEST_SMELLS_CSV).toString();
            BufferedReader reader;
            try {
                reader = new BufferedReader(new FileReader(implCsvFile));
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] column = line.split(",");
                    if (column.length > 5) {
                        TestSmell smell = new TestSmell();
                        smell.setName(column[4]);
                        smell.setProject(column[0]);
                        smell.setPkg(column[1]);
                        smell.setClassName(column[2]);
                        smell.setMethodName(column[3]);
                        smell.setDescription(column[5]);
                        smell.setSmellType("Test Smell");
                        totalTestSmells++;
                        testSmellsList.add(smell);
                    }
                }
            } catch (IOException e) {
                DesigniteLogger.getLogger().log(Level.WARNING, e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private void readArchitectureSmells() {
        File designiteOutputFolder = new File(
                String.valueOf(Paths.get(SettingsFolder.getDesigniteOuputDirectoryPath(), projectName)));

        if (designiteOutputFolder.exists()) {
            String ArchitectureCsvFile = Paths.get(SettingsFolder.getDesigniteOuputDirectoryPath(),
                    projectName,
                    Constants.ARCHITECTURE_SMELLS_CSV).toString();
            BufferedReader reader;
            try {
                reader = new BufferedReader(new FileReader(ArchitectureCsvFile));
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] column = line.split(",");
                    if (column.length >= 4) {
                        ArchitectureSmell smell = new ArchitectureSmell();
                        smell.setName(column[2]);
                        smell.setProject(column[0]);
                        smell.setPkg(column[1]);
                        smell.setDescription(column[3]);
                        smell.setSmellType("Architectural Smell");
                        totalArchitectureSmells++;
                        architectureSmellList.add(smell);
                    }
                }
            } catch (IOException e) {
                DesigniteLogger.getLogger().log(Level.WARNING, e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private void readTestabilitySmells() {
        File designiteOutputFolder = new File(
                String.valueOf(Paths.get(SettingsFolder.getDesigniteOuputDirectoryPath(), projectName)));

        if (designiteOutputFolder.exists()) {
            String designCsvFile = Paths.get(SettingsFolder.getDesigniteOuputDirectoryPath(),
                    projectName,
                    Constants.TESTABILITY_SMELLS_CSV).toString();
            BufferedReader reader;
            try {
                reader = new BufferedReader(new FileReader(designCsvFile));
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] column = line.split(",");
                    if (column.length > 4) {
                        TestabilitySmell smell = new TestabilitySmell();
                        smell.setName(column[3]);
                        smell.setProject(column[0]);
                        smell.setPkg(column[1]);
                        smell.setClassName(column[2]);
                        smell.setDescription(column[4]);
                        smell.setSmellType("Testability Smell");
                        totalTestabilitySmells++;
                        testabilitySmellList.add(smell);
                    }
                }
            } catch (IOException e) {
                DesigniteLogger.getLogger().log(Level.WARNING, e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public List<TypeMetrics> getTypeMetrics() {
        return typeMetricsList;
    }

    public int getTotalImplementationSmells() {
        return totalImplementationSmells;
    }

    public int getTotalDesignSmells() {
        return totalDesignSmells;
    }

    public int getTotalArchitectureSmells() {
        return totalArchitectureSmells;
    }

    public int getTotalTestabilitySmells() {
        return totalTestabilitySmells;
    }

    public int getTotalTestSmells() {
        return totalTestSmells;
    }

}
