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

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import com.scramcode.djinn.db.logic.JavaItemVistor;
import com.scramcode.djinn.ui.i18n.Images;


public class Clazz extends AbstractJavaItem {
    
    public static final ImageIcon ICON = Images.getIcon("Class.graph.icon");

    private String name;
    private int access;
    
    private Package packageObject;
    
    private String canonicalName;
    
    private List<Clazz> references = new ArrayList<Clazz>();

	private List<Method> methods = new ArrayList<Method>();

	private List<Field> fields = new ArrayList<Field>();
        
    public Clazz(String name, String canonicalName, int access, Package packageObject) {
        this.name = name;        
        this.access = access;
        this.packageObject = packageObject;
        this.canonicalName = canonicalName;        
        
        packageObject.getClasses().add(this);
    }
        
    public java.lang.String getName() {
        return this.name;
    }

    public void setName(java.lang.String name) {
        this.name = name;
    }
    
    public String getCanonicalName() {
        return this.canonicalName;
    }
    
    public void setCanonicalName(String canonicalName) {
        this.canonicalName = canonicalName;
    }
    
	public Package getPackage() {
		return packageObject;
	}

    public int getAccess() {
        return this.access;
    }

    public void setAccess(int access) {
        this.access = access;
    }

    public String getMappedTable() {
        return "CLASSES";
    }

    @Override
    public String getLabel() {
        return name;
    }

    @Override
    public ImageIcon getImage() {
        return ICON;
    }
    
    @Override
    public Color getColor() {
        return Color.green;
    }

	public void addReference(Clazz referencedClazz) {
		references.add(referencedClazz);
	}

	public void clearReferences() {
		references.clear();
	}

	public List<Method >getMethods() {		
		return methods;
	}

	public List<Field> getFields() {
		return fields;
	}
	
	@Override
	public void accept(JavaItemVistor javaItemVistor) {
		javaItemVistor.visitClazz(this);
		
		if (javaItemVistor.isMemberVisitEnabled()) {
			for (Method method : methods) {
				method.accept(javaItemVistor);
			}
			
			for (Field field : fields) {
				field.accept(javaItemVistor);
			}		
		}
	}
	
	public List<Clazz> getReferences() {
		return references;
	}

	public boolean isContainedBy(JavaItem destinationObject) {		
		return packageObject.getKey() == destinationObject.getKey() || packageObject.isContainedBy(destinationObject);		
	}

}
