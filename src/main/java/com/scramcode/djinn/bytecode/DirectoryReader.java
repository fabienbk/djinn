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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;

public class DirectoryReader implements LocationReader {

    private File directory;
    private HashSet<String> visitedPackages = new HashSet<String>();
    
    public DirectoryReader(File directory) {
        this.directory = directory;
    }
    
    public void visitDirectory(LocationVisitor visitor, File visitedDirectory) {        
        
        File[] files = visitedDirectory.listFiles();        
        int numberOfJavaClasses = 0;
        
        if (files == null) {
        	return;
        }
        
        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile() && files[i].getName().endsWith(".class")) {
                numberOfJavaClasses++;
            }
        }                
                
        String packageName = visitedDirectory.getAbsolutePath().substring(
                directory.getAbsolutePath().length());
        
        if (packageName.length() == 0) {
            packageName = "<root>";
        }
        else {
            packageName = packageName.substring(1).replaceAll("\\\\", ".");
        }                        
        
        if (!visitedPackages.contains(packageName) && numberOfJavaClasses > 0) {            
             visitor.visitPackage(packageName);            
             visitedPackages.add(packageName);
        }
                
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {                
                visitDirectory(visitor, files[i]); // visit subdir
            }
            else {
                if (files[i].getName().endsWith(".class")) {
                    FileInputStream fis = null;
                    try {
                        fis = new FileInputStream(files[i]);
                    }
                    catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                                                            
                    //String className = files[i].getName().substring(0, files[i].getName().length()-6);
                    
                    visitor.visitClass(packageName, packageName, fis);
                    
                    try {
                        fis.close();
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }        
    }
    

    public void accept(LocationVisitor visitor) {
        visitDirectory(visitor, directory);
    }
    
}
