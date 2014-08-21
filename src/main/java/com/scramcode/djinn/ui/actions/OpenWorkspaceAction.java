package com.scramcode.djinn.ui.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.scramcode.djinn.ui.Application;
import com.scramcode.djinn.ui.dialogs.WorkspaceEditorDialogController;
import com.scramcode.djinn.ui.i18n.Images;
import com.scramcode.djinn.ui.i18n.Messages;

public class OpenWorkspaceAction extends AbstractAction {
	
	private static final long serialVersionUID = 1L;

	public OpenWorkspaceAction(boolean enabled) {
        super(Messages.getString("OpenWorkspaceAction.label"), Images.getIcon("OpenWorkspaceAction.icon") );        
        setEnabled(enabled);
	}

	public void actionPerformed(ActionEvent event) {		
		WorkspaceEditorDialogController dialog = Application.getInstance().getWorkspaceEditorDialogController();		
		dialog.isEdit = true;
		dialog.setVisible(true);		
	}

}
