/*
 * Created on Feb 8, 2006
 * By Fabien Benoit - http://www.scramcode.com
 * 
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
package net.jnovation.djinn.bytecode;

import java.sql.Connection;
import java.util.Iterator;
import java.util.Set;

import net.jnovation.djinn.db.data.Class;
import net.jnovation.djinn.db.data.DataHelper;
import net.jnovation.djinn.db.data.Field;
import net.jnovation.djinn.db.data.Method;
import net.jnovation.djinn.db.mgmt.ConnectionManager;
import net.jnovation.djinn.util.NameTools;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;

/**
 * Visitor that build a class data object.
 * @author Fabien Benoit <fabien.benoit@gmail.com>
 */
public final class DefClassVisitor implements ClassVisitor {
    
    private Connection connection = ConnectionManager.getInstance().getConnection();
    
    private int packageKey;
    private int locationKey;
    private Class clazz;    
    private TypeSet typeSet;
    
    public DefClassVisitor(int packageKey, int locationKey) {
        this.locationKey=locationKey;
        this.packageKey=packageKey;
        this.typeSet = new TypeSet();
    }

    public void visitInnerClass(String name, String outerName, String innerName, int access) {
        // Blah.
    }

    public void visitAttribute(Attribute attr) {
        // Blah.
    }    

    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        this.clazz = new Class(NameTools.getUnqualifiedClassName(name), name, access, packageKey, locationKey);
        
        // finally, save class in database                        
        DataHelper.putClass(connection, clazz);
    }

    public void visitSource(String source, String debug) {     
        // Nothing interesting here
    }

    public void visitOuterClass(String owner, String name, String desc) {
        // Nothing interesting here        
    }
    
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        return null;
    }

    public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
        Field field = new Field(name, access, clazz.getKey());        
        DataHelper.putField(connection, field);        
        
        typeSet.addDesc(desc); // add class reference
        
        return null;
    }

    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        Method method = new Method(name, access, clazz.getKey());
        DataHelper.putMethod(connection, method);
        
        typeSet.addMethodDesc(desc); // add class reference
                
        return new DefMethodVisitor(typeSet);
    }
    
    /** Dump references to the database */
    public void visitEnd() {        
        Set<String> clazzReferences = typeSet.getSet();
        for (Iterator<String> iter = clazzReferences.iterator(); iter.hasNext();) {
            DataHelper.putClassReference(connection, clazz.getKey(), iter.next());
        }        
    }
    
}