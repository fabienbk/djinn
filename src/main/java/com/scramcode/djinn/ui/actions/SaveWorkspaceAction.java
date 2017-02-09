package com.scramcode.djinn.ui.actions;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scramcode.djinn.db.data.DataHelper;
import com.scramcode.djinn.db.data.Workspace;
import com.scramcode.djinn.db.data.WorkspaceDto;
import com.scramcode.djinn.ui.i18n.Images;
import com.scramcode.djinn.ui.i18n.Messages;

public class SaveWorkspaceAction extends AbstractAction {
	
	protected static String WORKSPACE_EXTENSION = ".json";
	
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
		fileChooser.setDialogTitle("Specify a file to save the workspace");   
    fileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
		fileChooser.setMultiSelectionEnabled(false);
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setFileFilter(new FileFilter() {					
			@Override
			public String getDescription() {
				return WORKSPACE_EXTENSION+" files";
			}
			@Override
			public boolean accept(File file) {
				return file.getName().endsWith(WORKSPACE_EXTENSION) ;
			}
		});
		int userSelection = fileChooser.showSaveDialog(null);
		if (userSelection == JFileChooser.APPROVE_OPTION) {
			File fileToSave = addExtensionIfNecessary(fileChooser.getSelectedFile());
		
			WorkspaceDto dto = new WorkspaceDto();
			Workspace workspace = DataHelper.getWorkspace();
			dto.topLevelItems = workspace.getTopLevelItems();

	    ObjectMapper mapper = new ObjectMapper();
      String json = "";
			try {
				json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(dto);
			} catch (JsonProcessingException ex) {
				ex.printStackTrace();
			}
			
			FileWriter fw = null;
			try {
				fw = new FileWriter(fileToSave);
				fw.write(json);
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

	protected static File addExtensionIfNecessary(File file) {
		String path = file.getAbsolutePath();
		if (path.endsWith(WORKSPACE_EXTENSION)) {
			return file;
		}
		file = new File(path + WORKSPACE_EXTENSION);
		return file;
	}

}
