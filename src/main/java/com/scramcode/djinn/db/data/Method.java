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

import javax.swing.ImageIcon;

import com.scramcode.djinn.db.logic.JavaItemVistor;
import com.scramcode.djinn.ui.i18n.Images;

/** @author Hibernate CodeGenerator */
public class Method extends AbstractJavaItem {

    private int access;
    private String name;
    private Clazz clazz;

    private static final ImageIcon ICON = Images.getIcon("Method.icon");
    
    public Method(String name, int access, Clazz clazz) {        
        this.access = access;
        this.clazz = clazz;
        this.name = name;
        clazz.getMethods().add(this);
    }
    
    @Override
    public JavaItem getParent() {    
    	return clazz;
    }
    
    public int getAccess() {
        return this.access;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String getLabel() {
        return name;
    }
    
    @Override
    public ImageIcon getIcon() {
        return null;
    }
    
    @Override
    public ImageIcon getSmallIcon() {
    	return ICON;
    }
    
    @Override
    public Color getColor() {
        return Color.red;
    }

    public Clazz getClazz() {
		return clazz;
	}
    
    @Override
    public void accept(JavaItemVistor javaItemVistor) {
    	javaItemVistor.visitMethod(this);
    }
    
    @Override
    public boolean isContainedBy(JavaItem destinationObject) {
    	return false;
    }
    
}
