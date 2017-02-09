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
package com.fbksoft.djinn.bytecode;

import java.util.HashSet;
import java.util.Set;

import org.objectweb.asm.Type;

public class TypeSet {
    
    private HashSet<String> typeSet;
    
    public TypeSet() {        
        typeSet = new HashSet<String>();
    }
        
    public void addName(String name) {
        if (name == null)
            return;                
        typeSet.add(name);
    }

    public void addNames(String[] names) {
        for (int i = 0; names != null && i < names.length; i++) {
            addName(names[i]);
        }
    }

    public void addDesc(String desc) {
        addType(Type.getType(desc));
    }

    public void addMethodDesc(String desc) {
        addType(Type.getReturnType(desc));
        Type[] types = Type.getArgumentTypes(desc);
        for (int i = 0; i < types.length; i++) {
            addType(types[i]);
        }
    }

    public void addType(Type t) {
        switch (t.getSort()) {
            case Type.ARRAY:
                addType(t.getElementType());
                break;
            case Type.OBJECT:
                addName(t.getClassName().replace('.', '/'));
                break;
        }
    }

    public Set<String> getSet() {
        return this.typeSet;
    }
 
}
