package com.fbksoft.djinn.ui.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.fbksoft.djinn.ui.Application;
import com.fbksoft.djinn.ui.dialogs.WorkspaceEditorDialogController;
import com.fbksoft.djinn.ui.i18n.Images;
import com.fbksoft.djinn.ui.i18n.Messages;

public class NewWorkspaceAction extends AbstractAction {
	
	private static final long serialVersionUID = 1L;

	public NewWorkspaceAction(boolean enabled) {
        super(Messages.getString("NewWorkspaceAction.label"), Images.getIcon("NewWorkspaceAction.icon") );        
        setEnabled(enabled);
	}

	public void actionPerformed(ActionEvent event) {		
		WorkspaceEditorDialogController dialog = Application.getInstance().getWorkspaceEditorDialogController();		
		dialog.isEdit = false;
		dialog.setVisible(true);		
	}

}
