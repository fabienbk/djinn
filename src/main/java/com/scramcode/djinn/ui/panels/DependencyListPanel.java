package com.scramcode.djinn.ui.panels;

import java.awt.event.ActionEvent;
import java.util.ResourceBundle;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import com.scramcode.djinn.db.data.JavaItem;
import com.scramcode.djinn.model.GraphGranularityComboBoxModel.GranularityLevel;
import com.scramcode.djinn.ui.Application;
import com.scramcode.djinn.ui.table.JavaItemTableFactory;
import java.awt.Font;

public class DependencyListPanel extends JPanel {
	private final Action groupByClassAction = new GroupByClassAction();
	private final Action groupByPackageAction = new GroupByPackageAction();
	private final Action groupByLocationAction = new GroupByLocationAction();
	private final Action groupByProjectAction = new GroupByProjectAction();
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private final Action showInternalReferencesAction = new ShowInternalReferencesAction();
	private final Action showExternalReferencesAction = new ShowExternalReferencesAction();

	private JTable referencesTable;
	private GranularityLevel granularityLevel = GranularityLevel.CLASS;
	private JavaItem containerFilterItem = null;
	private JLabel containerFilterName;
	/**
	 * Create the panel.
	 */
	public DependencyListPanel() {
						
		JToolBar toolBar = new JToolBar();
		toolBar.setOrientation(SwingConstants.VERTICAL);
		toolBar.setFloatable(false);
		
		JLabel dependenciesLabel = new JLabel(ResourceBundle.getBundle("com.scramcode.djinn.ui.i18n.messages").getString("DependencyDetailsPanel.lblNewLabel.text_2")); //$NON-NLS-1$ //$NON-NLS-2$
		dependenciesLabel.setFont(new Font("DejaVu Sans", Font.BOLD, 12));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		JLabel containerFilterLabel = new JLabel(ResourceBundle.getBundle("com.scramcode.djinn.ui.i18n.messages").getString("DependencyDetailsPanel.lblFilterByContainer.text")); //$NON-NLS-1$ //$NON-NLS-2$
		containerFilterLabel.setFont(new Font("DejaVu Sans", Font.BOLD, 12));
		
		containerFilterName = new JLabel(ResourceBundle.getBundle("com.scramcode.djinn.ui.i18n.messages").getString("DependencyDetailsPanel.lblNewLabel.text_3")); //$NON-NLS-1$ //$NON-NLS-2$
		GroupLayout gl_destinationPanel = new GroupLayout(this);
		gl_destinationPanel.setHorizontalGroup(
			gl_destinationPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_destinationPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(dependenciesLabel, GroupLayout.PREFERRED_SIZE, 147, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(containerFilterLabel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(containerFilterName)
					.addContainerGap(586, Short.MAX_VALUE))
				.addGroup(gl_destinationPanel.createSequentialGroup()
					.addComponent(toolBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 841, Short.MAX_VALUE))
		);
		gl_destinationPanel.setVerticalGroup(
			gl_destinationPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_destinationPanel.createSequentialGroup()
					.addGap(6)
					.addGroup(gl_destinationPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(dependenciesLabel)
						.addComponent(containerFilterLabel)
						.addComponent(containerFilterName))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_destinationPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(toolBar, GroupLayout.DEFAULT_SIZE, 267, Short.MAX_VALUE)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 267, Short.MAX_VALUE))
					.addContainerGap())
		);
		
		referencesTable = JavaItemTableFactory.createJTable();
		scrollPane.setViewportView(referencesTable);
		
		JToggleButton groupByClassButton = new JToggleButton(ResourceBundle.getBundle("com.scramcode.djinn.ui.i18n.messages").getString("DependencyDetailsPanel2.tglbtnNewToggleButton.text")); //$NON-NLS-1$ //$NON-NLS-2$
		groupByClassButton.setSelected(true);
		buttonGroup.add(groupByClassButton);
		groupByClassButton.setAction(groupByClassAction);
		toolBar.add(groupByClassButton);
		
		JToggleButton groupByPackageButton = new JToggleButton(ResourceBundle.getBundle("com.scramcode.djinn.ui.i18n.messages").getString("DependencyDetailsPanel2.tglbtnNewToggleButton_1.text")); //$NON-NLS-1$ //$NON-NLS-2$
		buttonGroup.add(groupByPackageButton);
		groupByPackageButton.setAction(groupByPackageAction);
		toolBar.add(groupByPackageButton);
		
		JToggleButton groupByLocationButton = new JToggleButton(ResourceBundle.getBundle("com.scramcode.djinn.ui.i18n.messages").getString("DependencyDetailsPanel2.tglbtnNewToggleButton_2.text")); //$NON-NLS-1$ //$NON-NLS-2$
		buttonGroup.add(groupByLocationButton);
		groupByLocationButton.setAction(groupByLocationAction);
		toolBar.add(groupByLocationButton);
		
		JToggleButton groupByProjectButton = new JToggleButton(ResourceBundle.getBundle("com.scramcode.djinn.ui.i18n.messages").getString("DependencyDetailsPanel2.tglbtnNewToggleButton.text_1")); //$NON-NLS-1$ //$NON-NLS-2$
		buttonGroup.add(groupByProjectButton);
		groupByProjectButton.setAction(groupByProjectAction);
		toolBar.add(groupByProjectButton);
		
		JSeparator separator = new JSeparator();
		toolBar.add(separator);
		
		JToggleButton showInternalReferencesButton = new JToggleButton(ResourceBundle.getBundle("com.scramcode.djinn.ui.i18n.messages").getString("DependencyDetailsPanel2.tglbtnNewToggleButton.text_2")); //$NON-NLS-1$ //$NON-NLS-2$
		showInternalReferencesButton.setSelected(true);
		showInternalReferencesButton.setAction(showInternalReferencesAction);
		toolBar.add(showInternalReferencesButton);
		
		JToggleButton showExternalReferencesButton = new JToggleButton("New toggle button");
		showExternalReferencesButton.setSelected(true);
		showExternalReferencesButton.setAction(showExternalReferencesAction);
		toolBar.add(showExternalReferencesButton);
		
		setLayout(gl_destinationPanel);

	}

	public JTable getReferencesTable() {
		return referencesTable;
	}
	public GranularityLevel getGranularity() {
		return granularityLevel ;
	}
	
	private class GroupByClassAction extends AbstractAction {
		public GroupByClassAction() {
			putValue(SMALL_ICON, new ImageIcon(DependencyListPanel.class.getResource("/org.eclipse.jdt.ui/icons/full/obj16/class_obj.gif")));
			putValue(SHORT_DESCRIPTION, "Group dependencies by Class");
			setEnabled(true);			
		}
		public void actionPerformed(ActionEvent e) {
			granularityLevel = GranularityLevel.CLASS;
			Application.getInstance().getDependencyDetailsPanelController().updateDependencyList();
		}
	}
	private class GroupByPackageAction extends AbstractAction {
		public GroupByPackageAction() {
			putValue(SMALL_ICON, new ImageIcon(DependencyListPanel.class.getResource("/org.eclipse.jdt.ui/icons/full/obj16/package_obj.gif")));
			putValue(SHORT_DESCRIPTION, "Group dependencies by Package");
		}
		public void actionPerformed(ActionEvent e) {
			granularityLevel = GranularityLevel.PACKAGE;
			Application.getInstance().getDependencyDetailsPanelController().updateDependencyList();
		}
	}
	private class GroupByLocationAction extends AbstractAction {
		public GroupByLocationAction() {
			putValue(SMALL_ICON, new ImageIcon(DependencyListPanel.class.getResource("/org.eclipse.ant.ui/icons/full/obj16/jar_l_obj.gif")));
			putValue(SHORT_DESCRIPTION, "Group dependencies by Jar/Directory");
		}
		public void actionPerformed(ActionEvent e) {
			granularityLevel = GranularityLevel.JAR;
			Application.getInstance().getDependencyDetailsPanelController().updateDependencyList();
		}
	}
	private class GroupByProjectAction extends AbstractAction {
		public GroupByProjectAction() {
			putValue(SMALL_ICON, new ImageIcon(DependencyListPanel.class.getResource("/org.eclipse.ui/icons/full/obj16/elements_obj.gif")));
			putValue(SHORT_DESCRIPTION, "Group dependencies by Project");
		}
		public void actionPerformed(ActionEvent e) {
			granularityLevel = GranularityLevel.PROJECT;
			Application.getInstance().getDependencyDetailsPanelController().updateDependencyList();
		}
	}
	private class ShowInternalReferencesAction extends AbstractAction {
		public ShowInternalReferencesAction() {
			putValue(SMALL_ICON, new ImageIcon(DependencyListPanel.class.getResource("/org.eclipse.jdt.ui/icons/full/elcl16/ch_callers.gif")));
			putValue(SHORT_DESCRIPTION, "Show internal workspace dependencies");
		}
		public void actionPerformed(ActionEvent e) {			
			Application.getInstance().getDependencyDetailsPanelController().updateDependencyList();
		}
	}
	private class ShowExternalReferencesAction extends AbstractAction {
		public ShowExternalReferencesAction() {
			putValue(SMALL_ICON, new ImageIcon(DependencyListPanel.class.getResource("/org.eclipse.jdt.ui/icons/full/elcl16/ch_callees.gif")));
			putValue(SHORT_DESCRIPTION, "Show external workspace dependencies");
		}
		public void actionPerformed(ActionEvent e) {
			Application.getInstance().getDependencyDetailsPanelController().updateDependencyList();
		}
	}
	public JLabel getContainerFilterName() {
		return containerFilterName;
	}
	
	public void setContainerFilterName(JavaItem javaItem) {
		this.containerFilterItem = javaItem;
		if (javaItem != null) {
			containerFilterName.setIcon(javaItem.getSmallIcon());
			containerFilterName.setText(javaItem.getLabel());
		}
		else {
			containerFilterName.setText("none");
			containerFilterName.setIcon(null);
		}
	}
	
	public JavaItem getContainerFilterItem() {
		return containerFilterItem;
	}
}
