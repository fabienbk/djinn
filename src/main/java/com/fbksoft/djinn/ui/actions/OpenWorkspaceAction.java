package com.fbksoft.djinn.ui.actions;

import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fbksoft.djinn.db.data.WorkspaceDto;
import static com.fbksoft.djinn.ui.actions.SaveWorkspaceAction.WORKSPACE_EXTENSION;
import com.fbksoft.djinn.ui.dialogs.WorkspaceEditorDialogController;
import com.fbksoft.djinn.ui.i18n.Images;
import com.fbksoft.djinn.ui.i18n.Messages;

public class OpenWorkspaceAction extends AbstractAction {
	
	public OpenWorkspaceAction(boolean enabled) {
        super(Messages.getString("OpenWorkspaceAction.label"), Images.getIcon("OpenWorkspaceAction.icon") );        
        setEnabled(enabled);
	}

	@Override
	public void actionPerformed(ActionEvent event) {		
		JFileChooser fileChooser = new JFileChooser(".") {
			@Override
			public void approveSelection() {
				File f = getSelectedFile();
				if (!f.exists()) {
					cancelSelection();
				}
				else {
					super.approveSelection();
				}
			}
		};
		fileChooser.setDialogTitle("Specify a file to load the workspace");   
		fileChooser.setMultiSelectionEnabled(false);
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setFileFilter(new FileFilter() {					
			@Override
			public String getDescription() {
				return WORKSPACE_EXTENSION+" files";
			}
			@Override
			public boolean accept(File file) {
				return file.getName().endsWith(SaveWorkspaceAction.WORKSPACE_EXTENSION) ;
			}
		});
		int userSelection = fileChooser.showOpenDialog(null);
		if (userSelection == JFileChooser.APPROVE_OPTION) {
			File fileToLoad = SaveWorkspaceAction.addExtensionIfNecessary(fileChooser.getSelectedFile());
			FileReader fr = null;
			BufferedReader br = null;
			try {
				fr = new FileReader(fileToLoad);
				br = new BufferedReader((fr));
				StringBuilder sb = new StringBuilder();
				String line = br.readLine();
				while (line != null) {
					sb.append(line);
					sb.append("\n");
					line = br.readLine();
				}
	
		    ObjectMapper mapper = new ObjectMapper();
        WorkspaceDto dto = mapper.readValue(sb.toString(), WorkspaceDto.class);

				WorkspaceEditorDialogController.createWorkspace(dto.topLevelItems, null, null);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (br != null) {
					try {
						br.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (fr != null) {
					try {
						fr.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}			
		}		
	}

}
