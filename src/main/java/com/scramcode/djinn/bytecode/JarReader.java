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

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;


public class JarReader implements LocationReader {

    private JarFile file =  null;
            
    public JarReader(JarFile file) {
        this.file = file;
    }
    
    public void accept(LocationVisitor visitor) {
        
        if (visitor == null) {
            throw new IllegalArgumentException("Null visitor");
        }
        
        HashSet<String> visitedPackages = new HashSet<String>();
        
        Enumeration<JarEntry> jarEntries = file.entries();
        while (jarEntries.hasMoreElements()) {
            JarEntry jarEntry = jarEntries.nextElement();
            
            String name = jarEntry.getName();
            if (name.endsWith(".class")) {                
                String nameWithoutExtension = name.substring(0, name.lastIndexOf('.'));
                nameWithoutExtension = nameWithoutExtension.replaceAll("\\/", ".");
                
                String packageName = null;
                if (nameWithoutExtension.lastIndexOf(".") != -1) {
                    packageName = nameWithoutExtension.substring(0, nameWithoutExtension.lastIndexOf("."));
                }
                else {
                    packageName = "<root>";
                }
                String className = nameWithoutExtension.substring(nameWithoutExtension.lastIndexOf(".")+1);
                
                if (!visitedPackages.contains(packageName)) {
                    visitor.visitPackage(packageName);                    
                    visitedPackages.add(packageName);
                }
                
                InputStream is;
                try {
                    is = file.getInputStream(jarEntry);
                    visitor.visitClass(packageName, className, is);
                }
                catch (IOException e) {
                    e.printStackTrace();
                }

            }
            
        }
        
    }
      
}