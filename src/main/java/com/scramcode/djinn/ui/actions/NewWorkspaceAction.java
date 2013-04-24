package com.scramcode.djinn.ui.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.scramcode.djinn.ui.Application;
import com.scramcode.djinn.ui.i18n.Images;
import com.scramcode.djinn.ui.i18n.Messages;

public class NewWorkspaceAction extends AbstractAction {
	
	private static final long serialVersionUID = 1L;

	public NewWorkspaceAction(boolean enabled) {
        super(Messages.getString("NewWorkspaceAction.label"), Images.getIcon("NewWorkspaceAction.icon") );        
        setEnabled(enabled);
	}

	public void actionPerformed(ActionEvent event) {		
		Application.getInstance().getWorkspaceEditorDialogController().setVisible(true);		
	}

}
