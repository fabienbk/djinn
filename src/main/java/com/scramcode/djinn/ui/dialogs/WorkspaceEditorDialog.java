package com.scramcode.djinn.ui.dialogs;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

public class WorkspaceEditorDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JButton addEclipseProjectButton;
	private JButton addClassDirectoryButton;
	private JButton addJarFileButton;
	private JButton clearButton;
	private JList list;
	private JButton okButton;
	private JButton cancelButton;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			WorkspaceEditorDialog dialog = new WorkspaceEditorDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public WorkspaceEditorDialog() {
		setTitle("Workspace Creator");
		setBounds(100, 100, 590, 349);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 0, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		addEclipseProjectButton = new JButton("Add Eclipse Projects");
						
		clearButton = new JButton("Clear");
				
		addClassDirectoryButton = new JButton("Add Class Directories");
		
		addJarFileButton = new JButton("Add Jar Files");
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		GroupLayout glContentPanel = new GroupLayout(contentPanel);
		glContentPanel.setHorizontalGroup(
			glContentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(glContentPanel.createSequentialGroup()
					.addGroup(glContentPanel.createParallelGroup(Alignment.LEADING, false)
						.addComponent(addEclipseProjectButton, GroupLayout.PREFERRED_SIZE, 226, GroupLayout.PREFERRED_SIZE)
						.addComponent(addClassDirectoryButton, GroupLayout.PREFERRED_SIZE, 226, GroupLayout.PREFERRED_SIZE)
						.addComponent(addJarFileButton, GroupLayout.PREFERRED_SIZE, 226, GroupLayout.PREFERRED_SIZE)
						.addComponent(clearButton, GroupLayout.PREFERRED_SIZE, 226, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 342, Short.MAX_VALUE))
		);
		glContentPanel.setVerticalGroup(
			glContentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(glContentPanel.createSequentialGroup()
					.addComponent(addEclipseProjectButton)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(addClassDirectoryButton)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(addJarFileButton)
					.addPreferredGap(ComponentPlacement.RELATED, 170, Short.MAX_VALUE)
					.addComponent(clearButton))
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE)
		);
		
		list = new JList();
		scrollPane.setViewportView(list);
		contentPanel.setLayout(glContentPanel);

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		okButton = new JButton("OK");
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);
		cancelButton = new JButton("Cancel");
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);
	}
	
	public JButton getAddEclipseProjectButton() {
		return addEclipseProjectButton;
	}
	public JButton getAddClassDirectoryButton() {
		return addClassDirectoryButton;
	}
	public JButton getAddJarFileButton() {
		return addJarFileButton;
	}
	public JButton getClearButton() {
		return clearButton;
	}
	public JList getList() {
		return list;
	}
	public JButton getOkButton() {
		return okButton;
	}
	public JButton getCancelButton() {
		return cancelButton;
	}
}
