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
package net.jnovation.djinn.db.data;

import java.awt.Color;

import javax.swing.ImageIcon;

public abstract class DBObject {
           
    public abstract int getKey();
    
    public abstract String getMappedTable();    
    
    public abstract String getLabel();
    
    public abstract ImageIcon getImage();
    
    public abstract Color getColor();        
    
    @Override
    public String toString() {
        return getLabel();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj.getClass() == getClass()) {
            return ((DBObject)obj).getKey() == getKey(); 
        }
        return false;                
    }
    
    @Override
    public int hashCode() {
        return (getClass().getName() + String.valueOf(getKey())).hashCode();
    }

    public abstract boolean isContainedBy(DBObject destinationObject);
    
}
