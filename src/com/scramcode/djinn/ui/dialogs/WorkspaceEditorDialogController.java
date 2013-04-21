package com.scramcode.djinn.ui.dialogs;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import com.scramcode.djinn.bytecode.importer.EclipseProjectImporter;
import com.scramcode.djinn.bytecode.importer.ImportListener;
import com.scramcode.djinn.bytecode.importer.Importer;
import com.scramcode.djinn.bytecode.importer.ImporterFactory;
import com.scramcode.djinn.db.data.JavaItem;
import com.scramcode.djinn.db.data.Location;
import com.scramcode.djinn.db.data.Project;
import com.scramcode.djinn.db.mgmt.ConnectionManager;
import com.scramcode.djinn.ui.Application;
import com.scramcode.djinn.ui.i18n.Images;
import com.scramcode.djinn.ui.i18n.Messages;
import com.scramcode.djinn.util.DjinnException;
import com.scramcode.djinn.util.SwingWorker;

public class WorkspaceEditorDialogController {

	private WorkspaceEditorDialog workspaceEditorDialog;
	private WorkspaceEditorListModel workspaceEditorListModel;

	@SuppressWarnings({ "serial", "unchecked" })
	public WorkspaceEditorDialogController(Application application) {
		workspaceEditorDialog = new WorkspaceEditorDialog();
		workspaceEditorDialog.setModal(true);
		workspaceEditorDialog.setLocationRelativeTo(application.getApplicationFrame());

		workspaceEditorDialog.getAddEclipseProjectButton().setAction(new AbstractAction("Add Eclipse Projects", Images.getIcon("ImportEclipseProjectAction.icon")) {
			public void actionPerformed(ActionEvent ae) {
				JFileChooser fileChooser = new JFileChooser(".");
				fileChooser.setMultiSelectionEnabled(true);
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int showOpenDialog = fileChooser.showOpenDialog(null);
				if (showOpenDialog == JFileChooser.APPROVE_OPTION) {
					File[] list = fileChooser.getSelectedFiles();
					for (File file : list) {
						workspaceEditorListModel.addJavaItem(new Project(file.getName(), file));
					}
				}
			}
		});
		
		workspaceEditorDialog.getAddClassDirectoryButton().setAction(new AbstractAction("Add Class Directories", Images.getIcon("ImportDirectoryAction.icon")) {
			public void actionPerformed(ActionEvent ae) {
				JFileChooser fileChooser = new JFileChooser(".");
				fileChooser.setMultiSelectionEnabled(true);
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int showOpenDialog = fileChooser.showOpenDialog(null);
				if (showOpenDialog == JFileChooser.APPROVE_OPTION) {				
					File[] list = fileChooser.getSelectedFiles();
					for (File file : list) {
						workspaceEditorListModel.addJavaItem(new Location(file.getAbsolutePath(), Location.DIR_LOCATION_TYPE));
					}
				}
			}

		});
		
		workspaceEditorDialog.getAddJarFileButton().setAction(new AbstractAction("Add Jar Files", Images.getIcon("Jar.icon")) {
			public void actionPerformed(ActionEvent ae) {
				JFileChooser fileChooser = new JFileChooser(".");
				fileChooser.setMultiSelectionEnabled(true);
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				fileChooser.setFileFilter(new FileFilter() {					
					@Override
					public String getDescription() {
						return "Jar files (*.jar)";
					}
					
					@Override
					public boolean accept(File file) {
						return file.isDirectory() || file.getName().endsWith(".jar") ;
					}
				});
				int showOpenDialog = fileChooser.showOpenDialog(null);
				if (showOpenDialog == JFileChooser.APPROVE_OPTION) {
					File[] list = fileChooser.getSelectedFiles();
					for (File file : list) {
						workspaceEditorListModel.addJavaItem(new Location(file.getAbsolutePath(), Location.JAR_LOCATION_TYPE));
					}					
				}					
			}
		});
		
		workspaceEditorDialog.getClearButton().setAction(new AbstractAction("Clear selected", Images.getIcon("Delete.icon")) {		
			public void actionPerformed(ActionEvent e) {
				List<JavaItem> selectedValuesList = workspaceEditorDialog.getList().getSelectedValuesList();
				for (JavaItem javaItem : selectedValuesList) {
					workspaceEditorListModel.remove(javaItem);
				}
			}
		});
		
		
		workspaceEditorDialog.getOkButton().setAction(new AbstractAction("OK") {			
			public void actionPerformed(ActionEvent ae) {				
				createWorkspace(workspaceEditorListModel.getList());
			}
		});
		
		workspaceEditorListModel = new WorkspaceEditorListModel();
		workspaceEditorDialog.getList().setModel(workspaceEditorListModel);
		workspaceEditorDialog.getList().setCellRenderer(new WorkspaceEditorListCellRenderer());
				
	}

	public void setVisible(boolean visible) {
		workspaceEditorDialog.setVisible(visible);
	}
	

	public void createWorkspace(final ArrayList<JavaItem> list) {
        new SwingWorker(true) {
        	@Override
            public Object construct() {
        		try {
					ConnectionManager.getInstance().resetDatabase();
				} catch (SQLException e) {					
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
        		
        		ArrayList<String> importErrors = new ArrayList<String>();
        		final AtomicInteger i = new AtomicInteger(0);        		
                for (JavaItem javaItem : list) {				
                	Importer importer = ImporterFactory.createImporterFor(javaItem, new ImportListener() {
						public void updateImportProgress(int percent) {
							updateProgress(percent/list.size() + i.get()*(100/list.size()));
						}
						public void updateImportCurrentStatus(String status) {
							updateMessage(status);		
						}                    	
	                });
                		                
	                try {                        
	                    importer.performImport();
	                }
	                catch (DjinnException ex) {
	                	ex.printStackTrace();
	                	importErrors.add(ex.getMessage());
	                }     
	                i.incrementAndGet();
                }
                
                System.err.println("import errors:" + importErrors);
                return null;
            }
            
            @Override
            public void finished() {
            	workspaceEditorListModel.getList().clear();
            	workspaceEditorDialog.setVisible(false);
                Application.getInstance().getWorkspaceTreeController().refresh();
            }
            
        }.start();               
	}	

}
