/*
 * Created on Feb 8, 2006
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
package com.scramcode.djinn.bytecode.importer;

import java.util.ArrayList;

import com.scramcode.djinn.db.data.DataHelper;
import com.scramcode.djinn.db.data.Project;
import com.scramcode.djinn.db.mgmt.ConnectionManager;
import com.scramcode.djinn.util.DjinnException;


/**
 * 
 * @author Fabien BENOIT <fabien.benoit@gmail.com>
 */
public abstract class Importer {
	
	private ArrayList<ImportListener> listeners = new ArrayList<ImportListener>();	
	
	public void addImportListener(ImportListener l) {
		listeners.add(l);
	}

	public void fireProgressUpdate(int percent) {
		for (ImportListener l : listeners) {
			l.updateImportProgress(percent);
		}
	}
	
	public void fireStatusUpdate(String status) {
		for (ImportListener l : listeners) {
			l.updateImportCurrentStatus(status);
		}
	}		

	public Project createAndSaveProject(String name) {
	    Project p = new Project(name);
	    DataHelper.putProject(ConnectionManager.getInstance().getConnection(), p);
	    return p;
	}
	
	public abstract void importProject() throws DjinnException;
	
}