package designite.filesManager;

import designite.constants.Constants;
import org.apache.commons.lang.SystemUtils;

import java.io.File;
import java.nio.file.Paths;

public class SettingsFolder {
    private static String designiteDirectoryPath;

    public void createSettingsFolder(String directoryName) {
        String homeDirectory = getHomeDirectory();
        designiteDirectoryPath = Paths.get(homeDirectory, directoryName).toString();
        makeDirectory(directoryName);
    }

    private static void makeDirectory(String directoryName) {
        File directory = new File(directoryName);

        if (!directory.exists()) {
            if (!directory.mkdir())
                System.out.println("Could not create directory: " + directoryName);
        }
    }

    public static String getDesigniteDirectoryPath() {
        String homeDirectory = getHomeDirectory();
        designiteDirectoryPath = Paths.get(homeDirectory, Constants.DESIGNITE_SETTINGS_FOLDER).toString();
        makeDirectory(designiteDirectoryPath);
        return designiteDirectoryPath;
    }

    public static String getDesigniteLogDirectoryPath() {
        String homeDirectory = getHomeDirectory();
        String designiteLogDirectoryPath = Paths.get(homeDirectory,
                Constants.DESIGNITE_SETTINGS_FOLDER,
                Constants.DESIGNITE_LOG_FOLDER).toString();
        makeDirectory(designiteLogDirectoryPath);
        return designiteLogDirectoryPath;
    }

    public static String getDesigniteOuputDirectoryPath() {
        String homeDirectory = getHomeDirectory();
        String designiteOutputDirectoryPath = Paths.get(homeDirectory,
                Constants.DESIGNITE_SETTINGS_FOLDER,
                Constants.DESIGNTE_OUTPUT_FOLDER).toString();
        makeDirectory(designiteOutputDirectoryPath);
        return designiteOutputDirectoryPath;
    }
    private static String getHomeDirectory(){
        String homeDirectory = System.getProperty("user.home");
        if(SystemUtils.IS_OS_WINDOWS){
            homeDirectory = System.getenv("AppData");
        }
        return homeDirectory;
    }
    public static void makeProjectOutputPath(String projectPath) {
        makeDirectory(projectPath);
    }
}
