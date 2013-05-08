/*
 * Created on Mar 3, 2006
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
package com.scramcode.djinn.db.logic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.scramcode.djinn.db.data.Clazz;
import com.scramcode.djinn.db.data.JavaItem;
import com.scramcode.djinn.db.data.Location;
import com.scramcode.djinn.db.data.Package;
import com.scramcode.djinn.model.GraphGranularityComboBoxModel.GranularityLevel;


/**
 * Queries for computing references aggregations.
 * 
 * @author Fabien Benoit <fabien.benoit@gmail.com>
 */
public final class ReferenceTools {             

	private ReferenceTools() {		
	}
	
    public static List<JavaItem> getReferences(
            JavaItem sourceObject,
            JavaItem destinationObject,
            GranularityLevel granularityLevel) {

        Set<? extends JavaItem> references = getAllReferences(sourceObject, granularityLevel);

        // Filter references not contained by destination object
        List<JavaItem> result = new ArrayList<JavaItem>();
        for(JavaItem reference : references) {
            if (reference.isContainedBy(destinationObject)) {
                result.add(reference);
            }
        }
        
        return result;
    }
    
    public static boolean detectReference(JavaItem sourceObject, final JavaItem destinationObject) {    	
        JavaItemVistor visitor = new JavaItemVistor() {
        	public void visitClazz(Clazz clazz) {
        		List<Clazz> references = clazz.getReferences();
        		for (Clazz referencedClazz :  references) {
					if (referencedClazz.isContainedBy(destinationObject)) {
						throw new AbortVisitException();
					}
				}
        	}
        };        
        try {
        	sourceObject.accept(visitor);
        	return false;
        }
        catch(AbortVisitException e) {
        	return true;
        }        
    }

	private static Set<? extends JavaItem> getAllReferences(JavaItem sourceObject, GranularityLevel granularityLevel) {
		// Get all references of the source object, at the requested granularity
        Set<? extends JavaItem> references = null;        
        switch(granularityLevel) {
            case CLASS: {
                references = getAllReferencesGroupByClass(sourceObject);
                break;
            }
            case JAR: {
                references = getAllReferencesGroupByLocation(sourceObject);
                break;
            }    
            case PACKAGE: {
                references = getAllReferencesGroupByPackage(sourceObject);
            }
        }
		return references;
	}
	
    public static Set<Clazz> getAllReferencesGroupByClass(JavaItem javaItem) {
    	final Set<Clazz> clazzReferencesSet = new HashSet<Clazz>();
        JavaItemVistor visitor = new JavaItemVistor() {
        	public void visitClazz(Clazz clazz) {
        		clazzReferencesSet.addAll(clazz.getReferences());
        	}
        };
        javaItem.accept(visitor);
        return clazzReferencesSet;
    }

    public static Set<Location> getAllReferencesGroupByLocation(JavaItem javaItem) {
    	final Set<Location> locationReferencesSet = new HashSet<Location>();
        JavaItemVistor visitor = new JavaItemVistor() {
        	public void visitClazz(Clazz clazz) {
        		List<Clazz> references = clazz.getReferences();
        		for (Clazz reference : references) {
					locationReferencesSet.add(reference.getPackage().getLocation());
				}
        	}
        };
        javaItem.accept(visitor);
        return locationReferencesSet;     
    }

    public static Set<Package> getAllReferencesGroupByPackage(JavaItem javaItem) {
    	final Set<Package> packageReferencesSet = new HashSet<Package>();
        JavaItemVistor visitor = new JavaItemVistor() {
        	public void visitClazz(Clazz clazz) {
        		List<Clazz> references = clazz.getReferences();
        		for (Clazz reference : references) {
					packageReferencesSet.add(reference.getPackage());
				}
        	}
        };
        javaItem.accept(visitor);
        return packageReferencesSet;     

    }

	public static Set<JavaItem> getAllReferencesFromSubSet(JavaItem sourceJavaItem, List<JavaItem> destinationJavaItemList) {
		HashSet<JavaItem> result = new HashSet<JavaItem>();
		for (JavaItem destinationJavaItem : destinationJavaItemList) {			
			if (detectReference(sourceJavaItem, destinationJavaItem)) {
				result.add(destinationJavaItem);
			}
		}
		return result;		
	}

}    
