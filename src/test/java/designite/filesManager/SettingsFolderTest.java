package designite.filesManager;

import designite.constants.Constants;
import org.apache.commons.lang.SystemUtils;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class SettingsFolderTest {
    public static final String TEST_DESIGNITE = ".test.designite";
//    String homePath = System.getProperty("user.home");

//    SettingsFolder folder = new SettingsFolder();

//    @Test
//    public void getDesigniteDirectoryPath() {
//        File designiteDirectory = new File(folder.getDesigniteDirectoryPath());
//        File designiteTestDirectory = new File(getHomeDirectory() + File.separator + TEST_DESIGNITE);
//
//        //assert Test to check existence of original directory
//        assertEquals(true, designiteDirectory.exists());
//
//        // Creating a test directory
//        folder.createSettingsFolder(designiteTestDirectory.getAbsolutePath());
//
//        //asset Test to check existence of test directory
//        assertEquals(true, designiteTestDirectory.exists());
//    }
//
//    @Test
//    public void getDesigniteLogDirectoryPath() {
//        File designiteLogDirectory = new File(folder.getDesigniteLogDirectoryPath());
//
//        //assert Test to check existence of original log directory
//        assertEquals(true, designiteLogDirectory.exists());
//
//        // Creating a test log folder
//        File designiteTestLogDirectory = new File(getHomeDirectory() + File.separator + TEST_DESIGNITE + Constants.DESIGNITE_LOG_FOLDER);
//
//        // Testing Existence of the test log directory
//        assertEquals(true, designiteTestLogDirectory.exists());
//    }

    private static String getHomeDirectory() {
        String homeDirectory = System.getProperty("user.home");
        if (SystemUtils.IS_OS_WINDOWS) {
            homeDirectory = System.getenv("AppData");
        }
        return homeDirectory;
    }
}