package com.scramcode.djinn.ui.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.scramcode.djinn.ui.Application;
import com.scramcode.djinn.ui.dialogs.WorkspaceEditorDialogController;
import com.scramcode.djinn.ui.i18n.Images;
import com.scramcode.djinn.ui.i18n.Messages;

public class SaveWorkspaceAction extends AbstractAction {
	
	private static final long serialVersionUID = 1L;

	public SaveWorkspaceAction(boolean enabled) {
        super(Messages.getString("SaveWorkspaceAction.label"), Images.getIcon("SaveWorkspaceAction.icon") );        
        setEnabled(enabled);
	}

	public void actionPerformed(ActionEvent event) {		
//		WorkspaceEditorDialogController dialog = Application.getInstance().getWorkspaceEditorDialogController();		
//		dialog.isEdit = true;
//		dialog.setVisible(true);		
	}

}
