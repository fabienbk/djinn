package com.scramcode.djinn.ui.actions;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import org.yaml.snakeyaml.Yaml;

import com.scramcode.djinn.db.data.DataHelper;
import com.scramcode.djinn.db.data.Workspace;
import com.scramcode.djinn.db.data.WorkspaceDto;
import com.scramcode.djinn.ui.i18n.Images;
import com.scramcode.djinn.ui.i18n.Messages;

public class SaveWorkspaceAction extends AbstractAction {
	
	public String WORKSPACE_EXTENSION = ".yaml";
	
	public SaveWorkspaceAction(boolean enabled) {
        super(Messages.getString("SaveWorkspaceAction.label"), Images.getIcon("SaveWorkspaceAction.icon") );        
        setEnabled(enabled);
	}

	@Override
	public void actionPerformed(ActionEvent event) {		
		JFileChooser fileChooser = new JFileChooser(".") {
			@Override
			public void approveSelection() {
				File f = getSelectedFile();
				if (f.exists()) {
					int result = JOptionPane.showConfirmDialog(this, "Do you want to overwrite the existing file?", "File already exists", JOptionPane.YES_NO_CANCEL_OPTION);
					switch (result) {
						case JOptionPane.YES_OPTION:
							super.approveSelection();
							return;
						case JOptionPane.NO_OPTION:
							return;
						case JOptionPane.CLOSED_OPTION:
							cancelSelection();
							return;
						case JOptionPane.CANCEL_OPTION:
							cancelSelection();
							return;
					}
				}
				super.approveSelection();
			}
		};
		fileChooser.setDialogTitle("Specify a file to save");   
    fileChooser.setDialogType( JFileChooser.SAVE_DIALOG );
		fileChooser.setMultiSelectionEnabled(false);
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setFileFilter(new FileFilter() {					
			@Override
			public String getDescription() {
				return "Yaml files (*"+WORKSPACE_EXTENSION+")";
			}
			@Override
			public boolean accept(File file) {
				return file.getName().endsWith(WORKSPACE_EXTENSION) ;
			}
		});
		int userSelection = fileChooser.showSaveDialog(null);
		if (userSelection == JFileChooser.APPROVE_OPTION) {
			File fileToSave = addExtensionIfNecessary(fileChooser.getSelectedFile());
			System.out.println("Save as file: " + fileToSave.getAbsolutePath());
		
			WorkspaceDto dto = new WorkspaceDto();
			Workspace workspace = DataHelper.getWorkspace();
			dto.topLevelItems = workspace.getTopLevelItems();
			dto.locations     = workspace.getLocations();
			dto.projects      = workspace.getProjects();

			Yaml yaml = new Yaml();
			StringWriter writer = new StringWriter();
			yaml.dump(dto, writer);
			System.out.println(writer.toString());

			FileWriter fw = null;
			try {
				fw = new FileWriter(fileToSave);
				fw.write(writer.toString());
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (fw != null) {
					try {
						fw.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}			
			
		}		
	}

	private File addExtensionIfNecessary(File file) {
		String path = file.getAbsolutePath();
		if (path.endsWith(WORKSPACE_EXTENSION)) {
			return file;
		}
		file = new File(path + WORKSPACE_EXTENSION);
		return file;
	}

}
