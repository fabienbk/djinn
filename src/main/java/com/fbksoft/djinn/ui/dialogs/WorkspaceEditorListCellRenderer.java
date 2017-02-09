package com.fbksoft.djinn.ui.dialogs;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import com.fbksoft.djinn.db.data.JavaItem;
import com.fbksoft.djinn.db.data.Location;
import com.fbksoft.djinn.db.data.Project;
import com.fbksoft.djinn.ui.i18n.Images;

public class WorkspaceEditorListCellRenderer extends JPanel implements ListCellRenderer {

	private static final long serialVersionUID = 1L;
	private JLabel itemNameLabel;
	private JLabel pathLabel;
	private JLabel iconLabel;


	public WorkspaceEditorListCellRenderer() {
		setBackground(Color.WHITE);
		
		iconLabel = new JLabel();		
		itemNameLabel = new JLabel("New label");		
		pathLabel = new JLabel("/home/fbenoit/djinn");
		
		pathLabel.setForeground(Color.LIGHT_GRAY);
		setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));
		add(iconLabel);
		add(itemNameLabel);
		add(pathLabel);
	}
	

	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		itemNameLabel.setText(((JavaItem)value).getLabel());

		if (isSelected) {
			setBackground(javax.swing.UIManager.getDefaults().getColor("Table.selectionBackground"));
			itemNameLabel.setForeground(javax.swing.UIManager.getDefaults().getColor("Table.selectionForeground"));
		}
		else {
			setBackground(Color.WHITE);
			itemNameLabel.setForeground(Color.BLACK);
		}
		
		if (value instanceof Project) {
			pathLabel.setText(((Project) value).getDirectory().getAbsolutePath());	
			iconLabel.setIcon(Images.getIcon("ImportEclipseProjectAction.icon"));
		}
		else if (value instanceof Location) {
			pathLabel.setText(((Location) value).getAbsolutePath());
			if (((Location) value).getType() == Location.DIR_LOCATION_TYPE) {
				iconLabel.setIcon(Images.getIcon("ClassDirectory.icon"));
			}
			else if (((Location) value).getType() == Location.JAR_LOCATION_TYPE) {
				iconLabel.setIcon(Images.getIcon("Jar.icon"));
			}						
		}		
		else {
			pathLabel.setText("");
		}		
		return this;
	}
}