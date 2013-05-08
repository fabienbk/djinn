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
package com.scramcode.djinn.db.data;


public final class DataHelper {
	
	private static Workspace workspace = new Workspace();
	        
	private DataHelper() {		
	}
	
    public static void putProject(Project project) {
    	workspace.addProject(project);
    }

	public static void putLocation(Location location) {                
		workspace.addLocation(location);        
    }
    
    public static void putClass(Clazz clazz) { 
        workspace.addClazz(clazz);
    }
    
    public static void putField(Field field) {                
        workspace.addField(field);
    }
    
    public static void putMethod(Method method) {                    	
    	workspace.addMethod(method);
    }   
    
    public static void putPackage(Package packageObject) {      
    	workspace.addPackage(packageObject);        
    }    

    public static void putClassReference(Clazz clazz, String desc) {    	
    	workspace.addClassReference(clazz, desc);                          
    }

	public static Workspace getWorkspace() {
		return workspace;		
	}

	public static void resetWorkspace() {
		workspace = new Workspace();
	}

}
