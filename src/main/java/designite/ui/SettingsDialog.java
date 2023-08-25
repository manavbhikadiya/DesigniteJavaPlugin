package designite.ui;

import com.intellij.openapi.util.IconLoader;
import com.intellij.ui.components.JBTabbedPane;
import designite.assetLoaders.DesigniteAssetLoader;
import designite.constants.Constants;
import designite.filesManager.AppProperties;
import designite.logger.DesigniteLogger;
import org.apache.commons.io.IOUtils;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

class SettingsDialog extends JDialog {
    private JTextField pathTextfield;
    private JTextField xmxTextField;
    private JCheckBox analyzeCodeCheckBox;
    private JButton browseButton;
    private JLabel presentLicenseLabel;
    private JButton okButton;
    private JTextField licenseKeyTextfield;

    SettingsDialog() {
        setModal(true);
        setResizable(false);
        setBounds(100, 100, 450, 270);
        setTitle("DesigniteJava - Settings");
        getContentPane().setLayout(new BorderLayout());
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new FlowLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
//        contentPanel.setBorder(BorderFactory.createTitledBorder(new EmptyBorder(5, 5, 5, 5), "DesigniteJava - Settings"));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        {
            JTabbedPane tabbedPane = new JBTabbedPane(JTabbedPane.TOP);
            contentPanel.add(tabbedPane);
            tabbedPane.setBounds(0, 0, 450, 280);
            tabbedPane.setPreferredSize(new Dimension(445, 200));
            JComponent generalPanel = new JPanel();
            tabbedPane.addTab("General", null, generalPanel, "General settings for DesigniteJava");
            analyzeCodeCheckBox = new JCheckBox("Analyze code everytime when a project is loaded or IDE starts");
            analyzeCodeCheckBox.setToolTipText("Specify whether you would like to run DesigniteJava everytime a project is opened or when the IDE starts");
            analyzeCodeCheckBox.setName("analyzeOnStartupCheckbox");

            JLabel pathLabel = new JLabel("DesigniteJava.jar path");
            pathLabel.setVerticalAlignment(SwingConstants.BOTTOM);
            pathTextfield = new JTextField();
            pathLabel.setLabelFor(pathTextfield);
            pathTextfield.setToolTipText("Specify path of DesigniteJava.jar");
            pathTextfield.setName("pathTextbox");
            pathTextfield.setColumns(5);

            browseButton = new JButton("...");
            browseButton.setToolTipText("Browse the path of DesigniteJava.jar");
            browseButton.setName("browseButton");

            JLabel lblxmxValue = new JLabel("-Xmx value");

            xmxTextField = new JTextField();
            xmxTextField.setColumns(10);
            GroupLayout gl_generalPanel = new GroupLayout(generalPanel);
            gl_generalPanel.setHorizontalGroup(
                    gl_generalPanel.createParallelGroup(Alignment.LEADING)
                            .addGroup(gl_generalPanel.createSequentialGroup()
                                    .addContainerGap()
                                    .addGroup(gl_generalPanel.createParallelGroup(Alignment.LEADING, false)
                                            .addComponent(pathLabel, GroupLayout.PREFERRED_SIZE, 257, GroupLayout.PREFERRED_SIZE)
                                            .addGroup(gl_generalPanel.createSequentialGroup()
                                                    .addComponent(pathTextfield, GroupLayout.PREFERRED_SIZE, 361, GroupLayout.PREFERRED_SIZE)
                                                    .addPreferredGap(ComponentPlacement.RELATED)
                                                    .addComponent(browseButton, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)))
                                    .addContainerGap())
                            .addGroup(gl_generalPanel.createSequentialGroup()
                                    .addContainerGap()
                                    .addComponent(lblxmxValue)
                                    .addGap(12)
                                    .addComponent(xmxTextField, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE)
                                    .addContainerGap(234, Short.MAX_VALUE))
                            .addComponent(analyzeCodeCheckBox, GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)
            );
            gl_generalPanel.setVerticalGroup(
                    gl_generalPanel.createParallelGroup(Alignment.LEADING)
                            .addGroup(gl_generalPanel.createSequentialGroup()
                                    .addComponent(analyzeCodeCheckBox, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
                                    .addGap(8)
                                    .addComponent(pathLabel)
                                    .addGroup(gl_generalPanel.createParallelGroup(Alignment.LEADING)
                                            .addGroup(gl_generalPanel.createSequentialGroup()
                                                    .addPreferredGap(ComponentPlacement.RELATED)
                                                    .addComponent(browseButton, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE))
                                            .addGroup(gl_generalPanel.createSequentialGroup()
                                                    .addGap(7)
                                                    .addComponent(pathTextfield, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(gl_generalPanel.createParallelGroup(Alignment.LEADING)
                                            .addGroup(gl_generalPanel.createSequentialGroup()
                                                    .addPreferredGap(ComponentPlacement.RELATED)
                                                    .addComponent(xmxTextField, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE))
                                            .addGroup(gl_generalPanel.createSequentialGroup()
                                                    .addGap(15)
                                                    .addComponent(lblxmxValue)))
                                    .addContainerGap(40, Short.MAX_VALUE))
            );
            generalPanel.setLayout(gl_generalPanel);
            tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
            JComponent licPanel = new JPanel();
            tabbedPane.addTab("License", null, licPanel, "License settings for DesigniteJava");

            JLabel label = new JLabel("License key");

            licenseKeyTextfield = new JTextField();
            licenseKeyTextfield.setToolTipText("Specify license key for DesigniteJava and validate (if not already)");
            licenseKeyTextfield.setName("licenseKeyTextbox");
            licenseKeyTextfield.setColumns(10);

            JButton registerButton = new JButton("Register");
            registerButton.setToolTipText("Register the license key");
            registerButton.setName("registerButton");
            registerButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    boolean status = registerLicense(licenseKeyTextfield.getText());
                    if (!status)
                        licenseKeyTextfield.setText("");
                }
            });

            presentLicenseLabel = new JLabel("Present license");
            GroupLayout gl_licPanel = new GroupLayout(licPanel);
            gl_licPanel.setHorizontalGroup(
                    gl_licPanel.createParallelGroup(Alignment.LEADING)
                            .addGroup(gl_licPanel.createSequentialGroup()
                                    .addContainerGap()
                                    .addGroup(gl_licPanel.createParallelGroup(Alignment.TRAILING)
                                            .addGroup(gl_licPanel.createSequentialGroup()
                                                    .addComponent(label, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE)
                                                    .addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(licenseKeyTextfield, GroupLayout.PREFERRED_SIZE, 216, GroupLayout.PREFERRED_SIZE)
                                                    .addPreferredGap(ComponentPlacement.RELATED)
                                                    .addComponent(registerButton, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                                                    .addGap(17))
                                            .addGroup(gl_licPanel.createSequentialGroup()
                                                    .addComponent(presentLicenseLabel)
                                                    .addContainerGap(324, Short.MAX_VALUE))))
            );
            gl_licPanel.setVerticalGroup(
                    gl_licPanel.createParallelGroup(Alignment.LEADING)
                            .addGroup(gl_licPanel.createSequentialGroup()
                                    .addGroup(gl_licPanel.createParallelGroup(Alignment.LEADING)
                                            .addGroup(gl_licPanel.createSequentialGroup()
                                                    .addGap(31)
                                                    .addComponent(label)
                                                    .addGap(18)
                                                    .addComponent(presentLicenseLabel))
                                            .addGroup(gl_licPanel.createSequentialGroup()
                                                    .addGap(25)
                                                    .addGroup(gl_licPanel.createParallelGroup(Alignment.TRAILING, false)
                                                            .addComponent(registerButton, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                            .addComponent(licenseKeyTextfield, Alignment.LEADING))))
                                    .addContainerGap(73, Short.MAX_VALUE))
            );
            licPanel.setLayout(gl_licPanel);
            tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);
        }
        {
            JPanel buttonPane = new JPanel();
            buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
            getContentPane().add(buttonPane, BorderLayout.SOUTH);
            {
                okButton = new JButton("OK");
                okButton.setActionCommand("OK");
                buttonPane.add(okButton);
                getRootPane().setDefaultButton(okButton);
            }
        }
    }

    private boolean registerLicense(String licKey) {
        DesigniteAssetLoader designiteAssetLoader = new DesigniteAssetLoader();
        if(!designiteAssetLoader.isDesigniteJarExists()){
            DesigniteLogger.getLogger().log(Level.SEVERE, "Could not find DesigniteJava.");
            JOptionPane.showMessageDialog(null, "It seems that the path of DesigniteJava.jar has not been set yet.\n " +
                    "Please set the path first (from the 'Settings' dialogbox) and then attempt to register license key.",
                    "DesigniteJava",
                    JOptionPane.INFORMATION_MESSAGE,
                    IconLoader.getIcon("Images/designite_logo.png", SettingsDialog.class));
            return false;
        }

        ProcessBuilder processBuilder = new ProcessBuilder("java",
                "-jar", designiteAssetLoader.designiteJarFileLocation(), "-r", licKey);
        try {
            String output = IOUtils.toString(processBuilder.start().getInputStream(), "UTF-8");
            DesigniteLogger.getLogger().log(Level.INFO, "Result of license registration attempt: " + output);
            if (output.startsWith("License registered successfully.")){
                JOptionPane.showMessageDialog(null, "License registered successfully.",
                        "DesigniteJava",
                        JOptionPane.INFORMATION_MESSAGE,
                        IconLoader.getIcon("Images/designite_logo.png", SettingsDialog.class));
                return true;
            }
            else
            {
                JOptionPane.showMessageDialog(null, "License key could not be registered.\n" +
                        output,
                        "DesigniteJava",
                        JOptionPane.ERROR_MESSAGE,
                        IconLoader.getIcon("Images/designite_logo.png", SettingsDialog.class));
                return false;
            }
        } catch (IOException e) {
            System.out.println("Exception occurred while registering license key for DesigniteJava. " + e.getMessage());
        }
        return false;
    }

    void initialize() {
        AppProperties<Serializable> designiteProperties = new AppProperties<>();
        populateGeneralSettingValues(designiteProperties);
        populateLicenseSettingValues(designiteProperties);
        okButtonListener(designiteProperties);
    }

    private void okButtonListener(AppProperties<Serializable> designiteProperties) {
        okButton.addActionListener(e -> {
            File settingsFilePath = new File(designiteProperties.getSettingFilePath());
            designiteProperties.buildProperties();

            if (settingsFilePath.exists()) {
                designiteProperties.writeProperty(Constants.ANALYZE_ON_STARTUP, analyzeCodeCheckBox.isSelected());
                designiteProperties.writeProperty(Constants.DESIGNITE_JAR_FILE_PATH, pathTextfield.getText());
                designiteProperties.writeProperty(Constants.XMX_VALUE, xmxTextField.getText());
                designiteProperties.writeProperty(Constants.LICENSE_KEY, licenseKeyTextfield.getText());
                designiteProperties.buildProperties();
            }
            dispose();
        });
    }

    private void populateLicenseSettingValues(AppProperties<Serializable> designiteProperties) {
        String licKey = designiteProperties.getProperty(Constants.LICENSE_KEY).toString();
        String licMessage;
        if ((licKey == null) || licKey.isEmpty() || licKey.equals("null"))
            licMessage = "Present license: Trial";
        else {
            licenseKeyTextfield.setText(licKey);
            if (licKey.startsWith("JA")) //academic license
                licMessage = "Present license: Academic";
            else if (licKey.startsWith("JP")) //Professional license
                licMessage = "Present license: Professional";
            else
                licMessage = "Present license: Unknown";
        }
        presentLicenseLabel.setText(licMessage);
    }

    private void populateGeneralSettingValues(AppProperties<Serializable> designiteProperties) {
        JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Jar files", "jar", "Jar files");
        fileChooser.setFileFilter(filter);
        analyzeCodeCheckBox.setSelected(
                Boolean.parseBoolean(designiteProperties.getProperty(Constants.ANALYZE_ON_STARTUP).toString()));
        String pathString = designiteProperties.getProperty(Constants.DESIGNITE_JAR_FILE_PATH).toString();
        if (pathString.equals("null"))
            pathString = "";
        pathTextfield.setText(pathString);
        browseButton.addActionListener(event -> {
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                if(validateFile(selectedFile.getAbsolutePath()))
                pathTextfield.setText(selectedFile.getAbsolutePath());
                else {
                    JOptionPane.showMessageDialog(null,
                            "It seems that the selected file is not a valid DesigniteJava.jar. \n" +
                                    "Please download the latest version of DesigniteJava (Enterprise) \n" +
                                    "from https://www.designite-tools.com/designitejava/ and try again.",
                    "DesigniteJava",
                            JOptionPane.INFORMATION_MESSAGE,
                            IconLoader.getIcon("Images/designite_logo.png", SettingsDialog.class));
                }
            }
        });
        String xmx = designiteProperties.getProperty(Constants.XMX_VALUE).toString();
        if (xmx == null || xmx.isEmpty() || xmx.equals("null"))
            xmx = "";
        xmxTextField.setText(xmx);
    }

    private boolean validateFile(String absolutePath) {
        ProcessBuilder processBuilder = new ProcessBuilder("java",
                "-jar", absolutePath, "-v");
        try {
            String output = IOUtils.toString(processBuilder.start().getInputStream(), "UTF-8");
            if (output.startsWith("DesigniteJava Enterprise"))
                return true;
        } catch (IOException e) {
            System.out.println("Exception occurred while invoking DesigniteJava.jar. " + e.getMessage());
            return false;
        }
        return false;
    }
}
