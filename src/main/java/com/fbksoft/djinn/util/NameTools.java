/*
 * Created on Feb 9, 2006
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
package com.fbksoft.djinn.util;

import java.util.ArrayList;
import java.util.List;

public class NameTools {

    /**
     * Returns all the parent packages of a package, given its qualified name.
     * @param packageQualifiedName the package qualified name to analyze.
     * @return a List of packages names, except the package parameter itself.
     */
    public static List<String> getParentPackageNames(String packageQualifiedName) {       
        if (!packageQualifiedName.matches("([\\w]+)(\\.[\\w]+)*")) {
            throw new IllegalArgumentException("Not a package name : " + packageQualifiedName);
        }
        ArrayList<String> packageList = new ArrayList<String>();        
        String[] tokens = packageQualifiedName.split("\\.");
        StringBuffer currentPackage = new StringBuffer();
        for (int i = 0; i < tokens.length - 1; i++) {            
            currentPackage.append(tokens[i]);
            packageList.add(currentPackage.toString());
            currentPackage.append('.');            
        }        
        return packageList;
    }
    
    public static String getUnqualifiedClassName(String qualifiedClassName) {
        int lastIndexOfSlash = qualifiedClassName.lastIndexOf('/');
        String unqualifiedName;
        if (lastIndexOfSlash != -1) {
            unqualifiedName = qualifiedClassName.substring(lastIndexOfSlash+1);
        }
        else {
            unqualifiedName = qualifiedClassName;
        }
        return unqualifiedName;
    }

}
