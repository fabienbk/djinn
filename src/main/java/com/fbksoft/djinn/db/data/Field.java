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
package com.fbksoft.djinn.db.data;

import java.awt.Color;

import javax.swing.ImageIcon;

import com.fbksoft.djinn.db.logic.JavaItemVistor;
import com.fbksoft.djinn.ui.i18n.Images;

public class Field extends AbstractJavaItem {

    private Clazz clazz;
    private String name;
    private int access;

    /** full constructor */
    public Field(String name, int access, Clazz clazz) {
        this.name = name;
        this.access = access;
        this.clazz = clazz;
        clazz.getFields().add(this);
    }

    @Override
    public JavaItem getParent() {    
    	return clazz;
    }
    
    public int getAccess() {
        return this.access;
    }

    public void setAccess(int access) {
        this.access = access;
    }
    
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public Clazz getClazz() {
		return clazz;
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
    public Color getColor() {
        return Color.RED;
    }
    
    @Override
    public void accept(JavaItemVistor javaItemVistor) {
    	javaItemVistor.visitField(this);
    }
    
    @Override
    public boolean isContainedBy(JavaItem destinationObject) {    
    	return false;
    }
    
    @Override
    public ImageIcon getSmallIcon() {
    	return Images.getIcon("Field.icon");
    }
    
}
