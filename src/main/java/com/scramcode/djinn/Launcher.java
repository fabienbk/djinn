/*
 * Created on Feb 5, 2006
 * By Fabien Benoit - http://www.scramcode.com 
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
package com.scramcode.djinn;

import java.io.File;
import java.util.Properties;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import com.scramcode.djinn.bytecode.importer.ImportListener;
import com.scramcode.djinn.bytecode.importer.RecursiveDirectoryImporter;
import com.scramcode.djinn.ui.Application;
import com.scramcode.djinn.ui.i18n.Messages;
import com.scramcode.djinn.util.DjinnException;
import com.scramcode.djinn.util.FileChooserFactory;
import com.scramcode.djinn.util.AbstractSwingWorker;


public final class Launcher {
	
	public static String version = "";
    
	private Launcher() {		
	}
	
    public static void main(String[] args) throws Exception {       
            	    	
    	// Install the look and feel
    	try {
    	    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
    	        if ("Nimbus".equals(info.getName())) {
    	            UIManager.setLookAndFeel(info.getClassName());
    	            break;
    	        }
    	    }
    	} catch (Exception e) {
    		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    	}
        
        // Load the version
        Properties versionProperties = new Properties();
        versionProperties.load(Launcher.class.getResourceAsStream("version.properties"));
        version = versionProperties.getProperty("version");
        
        System.out.println("Loading djinn version " + version + " ...");
        
        // Verify the call syntax
    	if (args.length > 1) {    		
    		JOptionPane.showMessageDialog(null, Messages.getString("error.argsyntax"), 
    				Messages.getString("error"), JOptionPane.ERROR_MESSAGE);    		
    		System.exit(2);
    	}
    	if (args.length == 1) {
    		final File directoryToImport = new File(args[0]);
    		if (!directoryToImport.isDirectory()) {
	    		JOptionPane.showMessageDialog(null, Messages.getString("error.argsyntax"), 
	    				Messages.getString("error"), JOptionPane.ERROR_MESSAGE);
	    		System.exit(2);
    		}
    		
    		AbstractSwingWorker importWorker = new AbstractSwingWorker(true) {
				@Override
				public Object construct() {
					
					RecursiveDirectoryImporter importer = new RecursiveDirectoryImporter(directoryToImport);
					importer.addImportListener(new ImportListener() {
						public void updateImportProgress(int percent) {
							updateProgress(percent);
						}
						public void updateImportCurrentStatus(String status) {
							updateMessage(status);		
						}                    	
                    });
					
					try {
						importer.performImport();
					} catch (DjinnException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return null;
				}    			
				
    		};
    		importWorker.start();
    		
    	}    	
    	        
        FileChooserFactory.getInstance();
//        ConnectionManager.getInstance();
        
        Application.getInstance().start();
        
    }
    
}
