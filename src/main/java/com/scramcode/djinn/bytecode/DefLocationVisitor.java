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
package com.scramcode.djinn.bytecode;

import java.io.InputStream;
import java.util.HashMap;

import org.objectweb.asm.ClassReader;

import com.scramcode.djinn.db.data.DataHelper;
import com.scramcode.djinn.db.data.Location;
import com.scramcode.djinn.db.data.Package;

public class DefLocationVisitor implements LocationVisitor {

    private Location location;
    private HashMap<String, Package> packageMap;    
    
    public DefLocationVisitor(Location location) {
        this.location = location;
        this.packageMap = new HashMap<String, Package>();
    }

    public void visitPackage(String packageQualifiedName) {
        Package packageObject = new Package(packageQualifiedName, location);
        DataHelper.putPackage(packageObject);
        packageMap.put(packageQualifiedName, packageObject);
    }

    public void visitClass(String classPackage, String className, InputStream byteCodeInputStream) {
        try {
            Package packageObj = packageMap.get(classPackage);            
            ClassReader classReader = new ClassReader(byteCodeInputStream);                
            classReader.accept(new DefClassVisitor(packageObj, location), ClassReader.SKIP_DEBUG & ClassReader.SKIP_FRAMES);            
        }
        catch (Exception e) {
        	System.err.println("Can't read bytecode of " + className + " in package " + classPackage);
            e.printStackTrace();
        }                
    }

}
