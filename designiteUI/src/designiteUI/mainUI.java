package designiteUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileSystemView;
import java.awt.FlowLayout;

public class mainUI {
    private JTextField licenseField;
    private JTextField pathField;
    JCheckBox analyzeCheckBox = new JCheckBox();
    private boolean Analyze = analyzeCheckBox.isSelected();
    public static String jarFilePath;

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					mainUI window = new mainUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public mainUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
        JPanel dialogPanel = new JPanel(new BorderLayout());
        JTextField textField;
        JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

        frame = new JFrame();
        frame.getContentPane();

        JCheckBox analyzeCheckBox = new JCheckBox();
        analyzeCheckBox.setBounds(6, 6, 21, 21);
      //  AppProperties<java.io.Serializable> designiteProperties = new AppProperties<>();
        //analyzeCheckBox.setSelected(Boolean.valueOf(designiteProperties.getProperty(Constants.ANALYZE_ON_STARTUP).toString()));

        frame.getContentPane().setLayout(null);
        frame.getContentPane().add(analyzeCheckBox);

        JLabel lblAnalyze = new JLabel("Analyze code everytime when a project is loaded or IDE starts");
        lblAnalyze.setBounds(33, 8, 430, 19);
        lblAnalyze.setFont(new Font("Tahoma", Font.PLAIN, 11));
        frame.getContentPane().add(lblAnalyze);

        JLabel label = new JLabel("DesigniteJava.jar path");
        label.setBounds(33, 38, 155, 19);
        label.setFont(new Font("Tahoma", Font.PLAIN, 11));
        frame.getContentPane().add(label);

        pathField = new JTextField();
        pathField.setBounds(149, 37, 251, 20);
        frame.getContentPane().add(pathField);
        pathField.setColumns(10);

        /*pathField.addActionListener(event -> {
            if(pathField.getText() != null)
                System.out.println(pathField.getText());
        });*/

        JButton btnBrowse = new JButton("...");
        btnBrowse.setBounds(410, 36, 45, 23);
        frame.getContentPane().add(btnBrowse);
        btnBrowse.addActionListener(event -> {
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                //System.out.println(selectedFile.getAbsolutePath());
                pathField.setText(selectedFile.getAbsolutePath());
            }
        });

        JLabel lblDesigniteLogo = new JLabel();
        lblDesigniteLogo.setBounds(341, 42, 0, 0);
       // lblDesigniteLogo.setIcon(new ImageIcon(Objects.requireNonNull(this.getClass().getClassLoader().getResource("/Images/designite_logo.png"))));
        frame.getContentPane().add(lblDesigniteLogo);
       // pathField.setText(designiteProperties.getProperty(Constants.DESIGNITE_JAR_FILE_PATH).toString());

        JLabel lblLicense = new JLabel("License key");
        lblLicense.setBounds(33, 68, 79, 19);
        lblLicense.setFont(new Font("Tahoma", Font.PLAIN, 11));
        frame.getContentPane().add(lblLicense);

        textField = new JTextField();
        textField.setBounds(95, 68, 197, 20);
        frame.getContentPane().add(textField);
        textField.setColumns(10);

        JButton btnRegister = new JButton("Validate");
        btnRegister.setBounds(328, 68, 73, 23);
        frame.getContentPane().add(btnRegister);

        JButton btnOK = new JButton("OK");
        btnOK.setBounds(333, 103, 122, 23);
        frame.getContentPane().add(btnOK);

        btnOK.addActionListener(e -> {
//	            String homePath = System.getProperty("user.home");
//	            File settingsFilePath = new File(homePath
//	                    +File.separator+Constants.DESIGNITE_SETTINGS_FOLDER
//	                    +File.separator + Constants.DESIGNITE_SETTINGS_FILE);
//	            AppProperties pathProperties = new AppProperties();
//
//	            pathProperties.buildProperties();
//
//	            if(settingsFilePath.exists()) {
//	            System.out.println("Analyze when project is open: "+analyzeCheckBox.isSelected());
//	            setAnalyzeSelection(analyzeCheckBox.isSelected());
//	            designiteProperties.writeProperty(Constants.ANALYZE_ON_STARTUP,analyzeCheckBox.isSelected());
//	            designiteProperties.buildProperties();
//	            if(!pathField.getText().isEmpty()) {
//	                designiteProperties.writeProperty(Constants.DESIGNITE_JAR_FILE_PATH,pathField.getText());
//	                designiteProperties.buildProperties();
//	                System.out.println(designiteProperties.getProperty(Constants.DESIGNITE_JAR_FILE_PATH));
//	            }
//	            else {
//	                System.out.println("path is empty");
//	            }}
//	            frame.dispose();
        });

        frame.setBounds(100, 100,  477, 166);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.setAlwaysOnTop(true);
	}

}
