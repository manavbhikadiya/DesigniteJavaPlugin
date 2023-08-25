package designiteUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class SettingsUI extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField pathTextfield;
	private JTextField textField;
	private JTextField licenseKeyTextfield;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			SettingsUI dialog = new SettingsUI();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public SettingsUI() {
		setModal(true);
		setResizable(false);
		setBounds(100, 100, 450, 270);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
			contentPanel.add(tabbedPane);
			tabbedPane.setBounds(0, 0, 450, 280);
			tabbedPane.setPreferredSize(new Dimension(445, 200));
			JComponent generalPanel = new JPanel();
			tabbedPane.addTab("General", null, generalPanel, "General settings for DesigniteJava");
				JCheckBox analyzeCodeCheckBox = new JCheckBox("Analyze code everytime when a project is loaded or IDE starts");
				analyzeCodeCheckBox.setToolTipText("Specify whether you would like to run DesigniteJava everytime a project is opened or when the IDE starts");
				analyzeCodeCheckBox.setName("analyzeOnStartupCheckbox");
				

				JLabel pathLabel = new JLabel("DesigniteJava.jar path");
				pathLabel.setVerticalAlignment(SwingConstants.BOTTOM);
//				pathLabel.setLocation(10, 20);
				pathTextfield = new JTextField();
				pathLabel.setLabelFor(pathTextfield);
				pathTextfield.setToolTipText("Specify path of DesigniteJava.jar");
				pathTextfield.setName("pathTextbox");
				pathTextfield.setColumns(5);
//				pathTextfield.setSize(200, 100);
//				textField.setLocation(40, 40);
				JButton browseButton = new JButton("...");
				browseButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
					}
				});
				browseButton.setToolTipText("Browse the path of DesigniteJava.jar");
				browseButton.setName("browseButton");
				
				JLabel lblxmxValue = new JLabel("-Xmx value");
				
				textField = new JTextField();
				textField.setColumns(10);
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
							.addComponent(textField, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE)
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
									.addComponent(textField, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE))
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
			
			JButton validateButton = new JButton("Validate");
			validateButton.setToolTipText("Validates the license key");
			validateButton.setName("validateButton");
			
			JLabel presentLicenseLabel = new JLabel("Present license");
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
								.addComponent(validateButton, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
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
									.addComponent(validateButton, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}
	private JComponent makeTextPanel(String text) {
        JPanel panel = new JPanel(false);
        return panel;
    }
}
