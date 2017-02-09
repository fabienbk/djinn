/*
 * Created on Feb 8, 2006
 * By Fabien Benoit - http://www.fbksoft.com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package com.fbksoft.djinn.ui.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import com.fbksoft.djinn.Launcher;
import com.fbksoft.djinn.ui.Application;
import com.fbksoft.djinn.ui.i18n.Messages;


public class AboutDialogAction extends AbstractAction {
    
	private static final long serialVersionUID = 1L;
    
    public AboutDialogAction() {    
        super(Messages.getString("AboutAction.label"));
        super.putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.ALT_MASK));
        super.putValue(MNEMONIC_KEY, KeyEvent.VK_X);
    }
    
    
	public void actionPerformed(ActionEvent e) {

		JOptionPane.showMessageDialog(Application.getInstance().getApplicationFrame(), 
				Messages.getString("AboutAction.text", Launcher.version), Messages.getString("AboutAction.label"), JOptionPane.INFORMATION_MESSAGE);
		
	}

}
