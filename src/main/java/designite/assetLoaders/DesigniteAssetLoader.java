package designite.assetLoaders;

import designite.constants.Constants;
import designite.filesManager.AppProperties;

import javax.swing.*;
import java.io.File;
import java.util.Objects;

public class DesigniteAssetLoader {

    private Icon designiteIcon = new ImageIcon(
            Objects.requireNonNull(this.getClass().getClassLoader().getResource("Images/designite_icon_16.png")));

    public Icon getDesigniteIcon() {
        return designiteIcon;
    }

    public boolean isDesigniteJarExists() {
        AppProperties designiteProperties = new AppProperties();
        String designiteJarFilePath = designiteProperties.getProperty(Constants.DESIGNITE_JAR_FILE_PATH).toString();
        File designiteJarFile = new File(designiteJarFilePath.replace("\\", File.separator));
        return designiteJarFile.exists();
    }

    public String designiteJarFileLocation() {
        AppProperties designiteProperties = new AppProperties();
        String designiteJarFilePath = designiteProperties.getProperty(Constants.DESIGNITE_JAR_FILE_PATH).toString();
        return designiteJarFilePath.replace("\\", File.separator);
    }
}
