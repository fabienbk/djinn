/*
 * Created on Feb 8, 2006
 * By Fabien Benoit - http://www.jnovation.net 
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
package net.jnovation.djinn.control.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import net.jnovation.djinn.bytecode.importer.EclipseProjectImporter;
import net.jnovation.djinn.bytecode.importer.ImportListener;
import net.jnovation.djinn.bytecode.importer.Importer;
import net.jnovation.djinn.control.Application;
import net.jnovation.djinn.i18n.Images;
import net.jnovation.djinn.i18n.Messages;
import net.jnovation.djinn.util.DjinnException;
import net.jnovation.djinn.util.FileChooserFactory;
import net.jnovation.djinn.util.SwingWorker;

public class ImportEclipseProjectAction extends AbstractAction {

    /** Comment for <code>serialVersionUID</code> */
    private static final long serialVersionUID = 1L;
    
    public ImportEclipseProjectAction(boolean enabled) {
        super(Messages.getString("ImportEclipseProjectAction.label"), Images.getIcon("ImportEclipseProjectAction.icon") );        
        setEnabled(enabled);
    }
    
    public void actionPerformed(ActionEvent e) {
        
        final JFileChooser fc = FileChooserFactory.getInstance().getDirectoryChooser();        
        int result = fc.showOpenDialog(Application.getInstance().getApplicationFrame());
        if (result == JFileChooser.APPROVE_OPTION) {
                        
            final SwingWorker worker = new SwingWorker(true) {
                @Override
                public Object construct() {                                                                              
                    Importer importer = new EclipseProjectImporter(fc.getSelectedFile());
                    importer.addImportListener(new ImportListener() {
						public void updateImportProgress(int percent) {
							updateProgress(percent);
						}
						public void updateImportCurrentStatus(String status) {
							updateMessage(status);		
						}                    	
                    });
                    
                    try {                        
                        importer.importProject();
                    }
                    catch (DjinnException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(Application.getInstance().getApplicationFrame(), 
                                ex.getMessage(), 
                                Messages.getString("error.import"), JOptionPane.ERROR_MESSAGE);
                    }                    
                    return null;
                }
                
                @Override
                public void finished() {
                    Application.getInstance().getWorkspaceTreeController().refresh();
                }            
            };
            
            worker.start(); 
        }
        
               
    }
}
